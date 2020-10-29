package com.boreas.designpatterns.proxy;

import com.boreas.designpatterns.proxy.impl.RealSubject;

import java.lang.reflect.Proxy;

public class Client {

    public static void main(String[] args) {
        // 静态代理
        // 优点：可以做到在符合开闭原则的情况下对目标对象进行功能扩展。
        // 缺点：代理对象与目标对象要实现相同的接口，我们得为每一个服务都得创建代理类，工作量太大，不易管理。同时接口一旦发生改变，代理类也得相应修改。
        Subject realSubject = new RealSubject();
        Subject subject = new SubjectStaticProxy(realSubject);
        subject.request();

        System.out.println("==========================================================================");
        // 动态代理
        Subject realSubject2 = new RealSubject();
        DynamicProxyHandler dynamicPurchasing = new DynamicProxyHandler(realSubject2);
        /**
         * Proxy.newProxyInstance : 生成一个代理对象
         * 参数1:ClassLoader loader 代理对象的类加载器 一般使用被代理对象的类加载器
         * 参数2:Class<?>[] interfaces 代理对象的要实现的接口 一般使用的被代理对象实现的接口
         * 参数3:InvocationHandler h (接口)执行处理类
         */
        Subject purchasing = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, dynamicPurchasing);
        purchasing.request();

        System.out.println("==========================================================================");

        Subject realSubject3 = new RealSubject();
        CglibProxy cglibProxy = new CglibProxy();
        RealSubject cglibRealSubject = (RealSubject) cglibProxy.getInstance(realSubject3);
        cglibRealSubject.request();
    }
}
