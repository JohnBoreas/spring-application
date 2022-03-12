解决死锁问题的方法是：一种是用synchronized，一种是用Lock显式锁实现。



信号量可以控制资源能被多少线程访问，这里我们指定只能被一个线程访问，就做到了类似锁住。而信号量可以指定去获取的超时时间，我们可以根据这个超时时间，去做一个额外处理。



```java
Semaphore semaphore = new Semaphore(1);
semaphore.tryAcquire(``1``, TimeUnit.SECONDS);
    
ReentrantLock lock1 = new ReentrantLock();
lock1.tryLock(1,TimeUnit.SECONDS)
```

