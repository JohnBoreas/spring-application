[控制反转](https://so.csdn.net/so/search?q=控制反转&spm=1001.2101.3001.7020)（Inversion of Control，英文缩写为IoC）把创建对象的权利交给框架,是框架的重要特征，并非面向对象编程的专用术语。它包括依赖注入（Dependency Injection，简称DI）和依赖查找（Dependency Lookup）



spring ioc指的是控制反转，IOC容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。交由Spring容器统一进行管理，从而实现松耦合

是一种思想

1. 谁控制谁
2. 反转了什么



Spring的IOC实现步骤解析：

1、加载并且保存Spring配置文件路径信息然后保存到configLocation中

2、 刷新Spring上下文环境

3、创建并且载入DefaultListableBeanFactory（即BeanFactory）

4、根据DefaultListableBeanFactory创建XMLBeanDefinitionReader，用于后面读取xml配置文件信息

5、创建BeanDefinitionDelegate代理类，用于解析xml配置信息

6、解析xml中配置的<import>、<bean>、<beans>、<alias>等不同的标签信息，以便于可以使用不同的解析器进行解析

7、通过XMLBeanDefinitionReader结合location路径信息读取Resources资源信息

8、使用BeanDefinitionDelegate代理类解析Bean元素并且依次进行实例化操作，实例化完毕之后将Bean信息注册（put）到BeanDefinitionMap中以便于可以下次继续使用





```
依赖注入：
        Dependency Injection
    IOC 的作用：
        降低程序键的耦合（依赖关系）
    依赖关系的管理：
       以后都交给 spring 来维护
       在当前类需要用到其他类的对象，由Spring来为我们提供，我们只需要在配置文件中说明
       
     依赖关系的维护：
            就称为依赖注入
     依赖注入：
            能注入的数据，有三类
                基本类型和 string
                其他 bean 类型（在配置文件中或者注解配置过的 bean—）
                复杂类型、集合类型
            
            注入的方式：有三种
                第一种：使用构造函数提供
                第二种：使用 set方法提供
                第三种：使用注解提供
```


![img](https://pic3.zhimg.com/80/v2-0f67c95641cca4e9072336f9ceb8dafa_720w.jpg)

