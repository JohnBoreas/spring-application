将标注了@Component和@Component的衍生注解如@Controller,@Service,@Repository，把当前的Bean加入到IOC容器中



一、@ComponentScan

**@ComponentScan做的事情就是告诉Spring从哪里找到bean**

```java
@SpringBootApplication
// 扫描com.java这个包并排除@Controller这个注解标注的类
@ComponentScan(value = "com.java",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class})})
public class Application {}
```

二、@SpringBootApplication

`@SpringBootApplication`里面有一个`@ComponentScan`,也就相当于@ComponentScan标注在入口类上。

默认情况下，扫描入口类同级及其子级包下的所有文件。



三、实现

org.springframework.context.annotation.ComponentScanAnnotationParser#parse

具体扫描的逻辑： 

org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan



四、@Import

 用于将指定的类实例注入之Spring IOC Container中

@Import可以添加在@SpringBootApplication(启动类)、@Configuration(配置类)、@Component(组件类)对应的类上。

```java
@SpringBootApplication
@Import(ImportBean.class) // 通过@Import注解把ImportBean添加到IOC容器里面去
public class MyBatisApplication {}

@Import({  // import了两个哈
     XXXDataConfiguration.XXXPartOneConfiguration.class,
     XXXDataConfiguration.XXXPartTwoConfiguration.class
}
```

