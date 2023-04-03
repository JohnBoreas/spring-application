

**用到了哪些设计模式？**

```
工厂模式：BeanFactory就是简单工厂模式的体现，用来创建对象的实例；
单例模式：Bean默认为单例模式。
代理模式：Spring的AOP功能用到了JDK的动态代理和CGLIB字节码生成技术；
模板方法：用来解决代码重复的问题。比如. RestTemplate, JmsTemplate, JpaTemplate。
观察者模式：定义对象键一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都会得到通知被制动更新，如Spring中listener的实现–ApplicationListener。
```

Spring中常见设计模式的

​    注意：Spring框架在使用到设计模式的地方一般都会用英文作为标记

​    1、简单工厂  ----》BeanFactory.getBean()通过传入的参数，动态的决定获取哪一个对象

​    2、工厂方法--->所有实现了FactoryBean接口的具体实现,非常有代表的：SqlSessionFactoryBean

​    3、单例 ---》getSingleton方法，首先去三级缓存取，没取到，将三级缓存锁住，然后去二级缓存取

​    4、适配器模式---》经典，AOP通知适配器

​    5、装饰器模式---》代表：BeanWrapper 后面使用的时候用到了bean class类型（这就是对象额外的功能）

​    6、原型模式 ---实现了Cloneable接口，对象原型对象进行克隆

​    7、代理模式---代理AOP

​    8、观察者模式--- registerListeners()
​            代表作：监听 事件源（ApplicationContext），事件(ApplicationEvent)，观察者(ApplicationListener)

​    9 、策略模式（strategy） ，进行方法处理的时候选择哪一种<lookup-method></lookup-method>//注入方式
​            		<replaced-method></replaced-method>//覆盖

​    10、模板方法---》jdbctemplate，redistemplate 我们不需关系模板里面的具体实现，spring框架已经封装好了
​        if(bean instanceof JdbcTemplate){return new JdbcTemplate()}