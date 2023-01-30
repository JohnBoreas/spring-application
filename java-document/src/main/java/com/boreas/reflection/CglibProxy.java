package com.boreas.reflection;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author boreas
 * @create 2023-01-30 14:40
 */
// 生成动态代理类
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target) {
        this.target = target;
        // Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CglibProxy start");
        Object result = methodProxy.invoke(object, args);
        System.out.println("CglibProxy end");
        return result;
    }
}
