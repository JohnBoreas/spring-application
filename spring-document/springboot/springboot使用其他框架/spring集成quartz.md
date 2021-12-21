#### 1、配置文件

可以用`quartz.properties`，可以用`application.yml`

如果使用集群，需要配置数据源



#### 2、数据源无法识别

org.quartz.jobStore.dataSource=

数据源在配置文件中定义的，会找不到config里面配置的数据源

解决：

（1）重新定义`SchedulerFactoryBean`，config配置数据源，需要自定义bean去获取

```java
SchedulerFactoryBean factory = new SchedulerFactoryBean();
factory.setAutoStartup(true);
factory.setStartupDelay(5);//延时5秒启动
factory.setQuartzProperties(quartzProperties());
// 必须配置数据源, 配置文件无法拿到config里的数据源，需要配置bean来获取
factory.setDataSource(dataSource);
```

（2）`SchedulerFactoryBean`可能也会不生效

可以配置：（注意数据库是否区分大小写）

```properties
org.quartz.dataSource.spiderDataSource.driver=com.mysql.cj.jdbc.Driver
org.quartz.dataSource.spiderDataSource.URL=
org.quartz.dataSource.spiderDataSource.user=root
org.quartz.dataSource.spiderDataSource.password=123456
```



#### 3、在job里@Autowired不生效

无法在job里注入bean

```shell
## 问题
使用spring 结合quartz进行定时任务开发时，如果直接在job内的execute方法内使用service 或者mapper对象，执行时，出现空指针异常。
```

job对象在spring容器加载时候，能够注入bean，但是调度时，job对象会重新创建，此时就是导致已经注入的对象丢失，因此报空指针异常。

解决：

（1）创建工具类`SpringContextJobUtil`，实现`ApplicationContextAware`接口,此工具类会在spring容器启动后，自动加载，使用其提供的`getBean`方法获取想要的bean即可

（2）重写JobFactory类（不建议）





#### 4、上一个任务执行未完成，又调度下一个任务

Quartz定时任务默认都是并发执行的，不会等待上一次任务执行完毕，只要间隔时间到就会执行

解决办法：

1.在Spring中这时需要设置concurrent的值为false, 禁止并发执行。

```
 <property name="concurrent" value="true" /
```

2.当不使用spring的时候就需要在Job的实现类上加@DisallowConcurrentExecution的注释





#### 5、一个任务JobDetail绑定多个Trigger

每个任务JobDetail可以绑定多个Trigger，但一个Trigger只能绑定一个任务

注意：

公共变量是共享的，相当于同一个job，不同的trigger下是共享job类里面的公共变量

