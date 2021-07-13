Spring 也提供了 `@Scheduled` 执行定时任务



#### 一、核心要素

`Scheduler`、`Trigger`、`Job`、`JobDetail`

trigger和job、jobDetail为元数据

Scheduler为实际进行调度的控制器

```powershell
1、Trigger
Trigger用于定义调度任务的时间规则，
分四种类型Trigger：SimpleTrigger、CronTrigger、DataIntervalTrigger和NthIncludedTrigger。

trigger状态：WAITING，ACQUIRED，EXECUTING，COMPLETE，BLOCKED，ERROR，PAUSED，PAUSED_BLOCKED，DELETED。

2、Job&Jodetail
Quartz将任务分为Job、JobDetail两部分

Job用来定义任务的执行逻辑
JobDetail用来描述Job的定义（例如Job接口的实现类以及其他相关的静态信息）。

对Quartz而言，主要有两种类型的Job：StateLessJob、StateFulJob

3、Scheduler
实际执行调度逻辑的控制器

Quartz提供了DirectSchedulerFactory和StdSchedulerFactory等工厂类，用于支持Scheduler相关对象的产生。
```

#### 二、核心元素关系

![在这里插入图片描述](..\resource\核心元素关系.png)

#### 三、数据存储

Quartz中有两种存储方式：RAMJobStore，JobStoreSupport，

`RAMJobStore`是将trigger和job存储在内存中，

`JobStoreSupport`是基于jdbc将trigger和job存储到数据库中



#### 四、Quartz集群容易存在的问题

##### 1、重复执行和资源竞争的问题

解决方式：**排它锁的方式**

（1）节点的主线程获取任务的执行权后，数据库对该行ROW LOCK，

（2）另外一个线程用同一SQL查询，此线程等待，直到锁的线程完成操作，commit后，数据库才会释放行锁



##### 2、节点争抢Job问题

Quartz使用了一个随机的负载均衡算法，Job以随机的方式由不同的实例执行。

不存在一个方法来指派(钉住) 一个 Job 到集群中特定的节点。



#### 五、关键组件

##### 1、QuartzSchedulerThread

用来进行任务调度的线程

主线程QuartzSchedulerThread 就是不断查询需要触发的trigger，获取trigger，执行trigger关联的任务，释放trigger。



##### 2、JobStore

2种store：内存RAMJobStore，数据库JobStoreSupport

2种实现：JobStoreTX（依赖于容器来进行事务的管理）和JobStoreCMT（自己管理事务）



若要使用集群要使用JobStoreSupport的方式

```shell
## 集群配置
org.quartz.jobStore.isClustered=true
org.quartz.jobStore.clusterCheckinInterval=20000
```



##### 3、ThreadPool

一般是使用SimpleThreadPool，

SimpleThreadPool创建了一定数量的WorkerThread实例来使得Job能够在线程中进行处理。



WorkerThread是定义在SimpleThreadPool类中的内部类，它实质上就是一个线程。



在SimpleThreadPool中有三个list：

workers-存放池中所有的线程引用，availWorkers-存放所有空闲的线程，busyWorkers-存放所有工作中的线程；

```shell
org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount=3
org.quartz.threadPool.threadPriority=5
```



#### 六、quartz运行中的一些问题：

1、报错：ClusterManager: Scanning for instance "SH10361624446416458"'s failed in-progress jobs

quartz的常规扫描，用于当终止程序时，好知道哪些任务正在执行，每个job都会生成一个id

下一次job运行会在`NEXT_FIRE_TIME`时候执行，超过该时间在等待时间后立即执行



#### 七、QuartzSchedulerThread线程

1、先获取线程池中的可用线程数量`availWorkers`（若没有可用的会阻塞，直到有可用的）；

2、获取30m内要执行的trigger `acquireNextTriggers`：

```
	获取trigger的锁，通过select …for update方式实现；
​	获取30m内（可配置）要执行的triggers（需要保证集群节点的时间一致），若@ConcurrentExectionDisallowed且列表存在该条trigger则跳过，否则更新trigger状态为ACQUIRED(刚开始为WAITING)；
​	插入firedTrigger表，状态为ACQUIRED;（注意：在RAMJobStore中，有个timeTriggers，排序方式是按触发时间nextFireTime排的；
​	JobStoreSupport从数据库取出triggers时是按照nextFireTime排序）;
```

3、等待直到获取的trigger中最先执行的trigger在2ms内；

4、triggersFired：

```
1.更新firedTrigger的status=EXECUTING;
2。更新trigger下一次触发的时间；
3.更新trigger的状态：无状态的trigger->WAITING，有状态的trigger->BLOCKED，若nextFireTime==null ->COMPLETE；
4.commit connection,释放锁；
```

5、针对每个要执行的trigger，创建JobRunShell，并放入线程池执行：

```
1、execute:执行job
2、获取TRIGGER_ACCESS锁
3、若是有状态的job：更新trigger状态：BLOCKED->WAITING,PAUSED_BLOCKED->BLOCKED
4、若@PersistJobDataAfterExecution，则updateJobData
5、删除firedTrigger
6、commit connection，释放锁
```

代码：

```java
while (!halted.get()) {
            try {
                // 锁住当前对象，判断是否暂停
                synchronized (sigLock) {
                    while (paused && !halted.get()) {
                        try {
                            // wait until togglePause(false) is called...
                            sigLock.wait(1000L);
                        } catch (InterruptedException ignore) {
                        }
                    }
                    if (halted.get()) {
                        break;
                    }
                }
                // 失败需要休眠一定时间
                
                // wait a bit, if reading from job store is consistently
                // failing (e.g. DB is down or restarting)..
                if (acquiresFailed > 1) {
                    try {
                        long delay = computeDelayForRepeatedErrors(qsRsrcs.getJobStore(), acquiresFailed);
                        Thread.sleep(delay);
                    } catch (Exception ignore) {
                    }
                }
                // 获取可用线程数availWorkers
                int availThreadCount = qsRsrcs.getThreadPool().blockForAvailableThreads();
                if(availThreadCount > 0) { // will always be true, due to semantics of blockForAvailableThreads...
                    List<OperableTrigger> triggers = null;
                    // 获取30m内要执行的trigge
                    long now = System.currentTimeMillis();
                    clearSignaledSchedulingChange();
                    try {
                        triggers = qsRsrcs.getJobStore().acquireNextTriggers(
                                now + idleWaitTime, Math.min(availThreadCount, qsRsrcs.getMaxBatchSize()), qsRsrcs.getBatchTimeWindow());
                        lastAcquireFailed = false;
                        if (log.isDebugEnabled()) 
                            log.debug("batch acquisition of " + (triggers == null ? 0 : triggers.size()) + " triggers");
                    } catch (JobPersistenceException jpe) {
                        if(!lastAcquireFailed) {
                            qs.notifySchedulerListenersError(
                                "An error occurred while scanning for the next triggers to fire.",
                                jpe);
                        }
                        lastAcquireFailed = true;
                        continue;
                    } catch (RuntimeException e) {
                        if(!lastAcquireFailed) {
                            getLog().error("quartzSchedulerThreadLoop: RuntimeException "
                                    +e.getMessage(), e);
                        }
                        lastAcquireFailed = true;
                        continue;
                    }

                    if (triggers != null && !triggers.isEmpty()) {

                        now = System.currentTimeMillis();
                        long triggerTime = triggers.get(0).getNextFireTime().getTime();
                        long timeUntilTrigger = triggerTime - now;
                        while(timeUntilTrigger > 2) {
                            synchronized (sigLock) {
                                if (halted.get()) {
                                    break;
                                }
                                if (!isCandidateNewTimeEarlierWithinReason(triggerTime, false)) {
                                    try {
                                        // we could have blocked a long while
                                        // on 'synchronize', so we must recompute
                                        now = System.currentTimeMillis();
                                        timeUntilTrigger = triggerTime - now;
                                        if(timeUntilTrigger >= 1)
                                            sigLock.wait(timeUntilTrigger);
                                    } catch (InterruptedException ignore) {
                                    }
                                }
                            }
                            if(releaseIfScheduleChangedSignificantly(triggers, triggerTime)) {
                                break;
                            }
                            now = System.currentTimeMillis();
                            timeUntilTrigger = triggerTime - now;
                        }

                        // this happens if releaseIfScheduleChangedSignificantly decided to release triggers
                        if(triggers.isEmpty())
                            continue;

                        // set triggers to 'executing'
                        List<TriggerFiredResult> bndles = new ArrayList<TriggerFiredResult>();

                        boolean goAhead = true;
                        synchronized(sigLock) {
                            goAhead = !halted.get();
                        }
                        if(goAhead) {
                            try {
                                List<TriggerFiredResult> res = qsRsrcs.getJobStore().triggersFired(triggers);
                                if(res != null)
                                    bndles = res;
                            } catch (SchedulerException se) {
                                qs.notifySchedulerListenersError(
                                        "An error occurred while firing triggers '"
                                                + triggers + "'", se);
                                //QTZ-179 : a problem occurred interacting with the triggers from the db
                                //we release them and loop again
                                for (int i = 0; i < triggers.size(); i++) {
                                    qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                                }
                                continue;
                            }

                        }

                        for (int i = 0; i < bndles.size(); i++) {
                            TriggerFiredResult result =  bndles.get(i);
                            TriggerFiredBundle bndle =  result.getTriggerFiredBundle();
                            Exception exception = result.getException();

                            if (exception instanceof RuntimeException) {
                                getLog().error("RuntimeException while firing trigger " + triggers.get(i), exception);
                                qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                                continue;
                            }

                            // it's possible to get 'null' if the triggers was paused,
                            // blocked, or other similar occurrences that prevent it being
                            // fired at this time...  or if the scheduler was shutdown (halted)
                            if (bndle == null) {
                                qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                                continue;
                            }

                            JobRunShell shell = null;
                            try {
                                shell = qsRsrcs.getJobRunShellFactory().createJobRunShell(bndle);
                                shell.initialize(qs);
                            } catch (SchedulerException se) {
                                qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
                                continue;
                            }

                            if (qsRsrcs.getThreadPool().runInThread(shell) == false) {
                                // this case should never happen, as it is indicative of the
                                // scheduler being shutdown or a bug in the thread pool or
                                // a thread pool being used concurrently - which the docs
                                // say not to do...
                                getLog().error("ThreadPool.runInThread() return false!");
                                qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
                            }

                        }

                        continue; // while (!halted)
                    }
                } else { // if(availThreadCount > 0)
                    // should never happen, if threadPool.blockForAvailableThreads() follows contract
                    continue; // while (!halted)
                }

                long now = System.currentTimeMillis();
                long waitTime = now + getRandomizedIdleWaitTime();
                long timeUntilContinue = waitTime - now;
                synchronized(sigLock) {
                    try {
                      if(!halted.get()) {
                        // QTZ-336 A job might have been completed in the mean time and we might have
                        // missed the scheduled changed signal by not waiting for the notify() yet
                        // Check that before waiting for too long in case this very job needs to be
                        // scheduled very soon
                        if (!isScheduleChanged()) {
                          sigLock.wait(timeUntilContinue);
                        }
                      }
                    } catch (InterruptedException ignore) {
                    }
                }

            } catch(RuntimeException re) {
                getLog().error("Runtime error occurred in main trigger firing loop.", re);
            }
        } // while (!halted)
```

