#### 一、

1、简介

**@Configuration**用于定义配置类，可替换xml配置文件，

被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被`AnnotationConfigApplicationContext`或`AnnotationConfigWebApplicationContext`类进行扫描，并用于构建bean定义，初始化Spring容器。



2、内部实现

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component// 会注册为bean, 其内部也可以依赖注入。
public @interface Configuration {
}
```



3、注解的配置类有如下要求：

```shell
1. @Configuration不可以是final类型；
2. @Configuration不可以是匿名类；
3. 嵌套的configuration必须是静态类。
```



4、小结

**@Configuation** 等价于 `<Beans></Beans>`

**@Bean** 等价于 `<Bean></Bean>`

**@ComponentScan** 等价于 `<context:component-scan base-package="com.demo"/>`



5、区分

**@Repository**、**@Service** 和 **@Controller**，分别和持久层、业务层和控制层（Web 层）相对应。

在目前的 Spring 版本中，这 3 个注释和 @Component 是等效的

**@Component** 对那些比较中立的类进行注释。

**@Configuration**的第二个作用是可以作为配置类，和@Component的作用一样



#### 二、@Configuation加载Spring方法

1、@Configuration配置spring并启动spring容器

@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)

```java
@Configuration
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化。。。");
    }
}
```



2、@Configuration启动容器+@Bean注册Bean，@Bean下管理bean的生命周期

@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象

```java
注： 
(1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 
(2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域； 
(3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描。

//@Bean注解注册bean,同时可以指定初始化和销毁方法
@Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
@Scope("prototype")
public TestBean testBean() {
    return new TestBean();
}
```

