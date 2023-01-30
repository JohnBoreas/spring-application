package com.boreas.reflection;

public class MyAspect {

    //为了方便少做一些字符串解析，我直接给我们的注解添加目标对象name和方法name
    @Around( targetName = "targetService", methodName = "targetMethod")
    public void round() throws Throwable {
        System.out.println("环绕通知来了");
        System.out.println("环绕通知走了");
    }
}
