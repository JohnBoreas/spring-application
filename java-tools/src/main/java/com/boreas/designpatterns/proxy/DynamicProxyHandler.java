package com.boreas.designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态处理器
 * 减少了对业务接口的依赖，降低了耦合度。
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object object;

    public DynamicProxyHandler(final Object object) {
        this.object = object;
    }

    /**
     * 调用代理类的任何方法，此方法都会执行
     * @param proxy 代理对象(慎用)
     * @param method 当前执行的方法
     * @param args 当前执行的方法运行时传递过来的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("DynamicProxyHandler start");
        Object result = method.invoke(object, args);
        System.out.println("DynamicProxyHandler end");
        return result;
    }
}
