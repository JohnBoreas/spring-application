#### 一、简介

**@Component** （把普通pojo实例化到spring容器中，相当于配置文件中的 <bean id="" class=""/> ）

泛指各种组件

```java
@Component("conversionImpl")
//其实默认的spring中的Bean id 为 conversionImpl(首字母小写)
public class ConversionImpl implements Conversion {
    @Autowired
    private RedisClient redisClient;
}
```

#### 二、区别其他注解

**@Controller** 控制器（注入服务）： 用于标注控制层，相当于struts中的action层

**@Service 服务**（注入dao）： 用于标注服务层，主要用来进行业务的逻辑处理

**@Repository**（实现dao访问）：用于标注数据访问层，也可以说用于标注数据访问组件，即DAO组件

