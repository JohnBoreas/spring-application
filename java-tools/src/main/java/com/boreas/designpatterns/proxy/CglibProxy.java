package com.boreas.designpatterns.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB代理总结： CGLIB创建的动态代理对象比JDK创建的动态代理对象的性能更高，但是CGLIB创建代理对象时所花费的时间却比JDK多得多。
 * 所以对于单例的对象，因为无需频繁创建对象，用CGLIB合适，反之使用JDK方式要更为合适一些。
 * 同时由于CGLib由于是采用动态创建子类的方法，对于final修饰的方法无法进行代理。
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target) {
        // 首先将被代理类TargetObject设置成父类，然后设置拦截器TargetInterceptor
        this.target = target;
        // Enhancer类是CGLib中的一个字节码增强器，它可以方便的对你想要处理的类进行扩展
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        // 执行enhancer.create()动态生成一个代理类，并从Object强制转型成父类型TargetObject
        return enhancer.create();
    }

    /**
     * 拦截器
     * @param object 由CGLib动态生成的代理类实例
     * @param method 上文中实体类所调用的被代理的方法引用
     * @param args 参数值列表
     * @param methodProxy 生成的代理类对方法的代理引用
     * @return 从代理实例的方法调用返回的值
     * @throws Throwable
     */
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CglibProxy start");
        Object result = methodProxy.invoke(object, args);
        System.out.println("CglibProxy end");
        return result;
    }
}
