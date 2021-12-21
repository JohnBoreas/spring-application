1、设置注解

```java
/*** 自定义操作日志记录注解*/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /*** 模块*/
    public String title() default "";
    /*** 功能*/
    public BusinessType businessType() default BusinessType.OTHER;
    /*** 操作人类*/
    public OperatorType operatorType() default OperatorType.MANAGE;
    /*** 是否保存请求的参数*/
    public boolean isSaveRequestData() default true;
}
```

2、配置切面

创建切面类，配置织入点

```java
// 配置织入点
@Pointcut("@annotation(注解路径.annotation.Log)")
    public void logPointCut() {
}
/**
  * 处理完请求后执行
  * @param joinPoint 切点
 */
@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
     handleLog(joinPoint, null, jsonResult);// 切点处理
}
/*** 拦截异常操作*/
@AfterThrowing(value = "logPointCut()", throwing = "e")
public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
    handleLog(joinPoint, e, null);
}
```

3、使用

在方法前加入注解

```java
@Log(title = "操作日志", businessType = BusinessType.EXPORT)
public Result export(SysOperLog operLog){}
```

