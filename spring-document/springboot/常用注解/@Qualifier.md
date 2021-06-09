一、简介

**Qualifier的意思是合格者，通过这个标示，表明了哪个实现类才是我们所需要的**

**添加@Qualifier注解，需要注意的是@Qualifier的参数名称为我们之前定义@Service注解的名称之一。**

（1）使用@Autowire时，加上@Qualifier(“test”)可以指定注入哪个对象；

（2）可以作为筛选的限定符，我们在做自定义注解时可以在其定义上增加@Qualifier，用来筛选需要的对象。

```java
@Component
public class FooService {
    @Autowired
    @Qualifier("fooFormatter")
    private Formatter formatter;
    //todo 
}
```



