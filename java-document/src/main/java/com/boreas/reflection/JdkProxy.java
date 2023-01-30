package com.boreas.reflection;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author boreas
 * @create 2023-01-30 11:58
 */
public class JdkProxy {

    public static void main(String[] args) {
        JdkProxy proxy = new JdkProxy();
        proxy.execute();
    }

    public void execute() {
        TargetService service = new TargetServiceImpl();
        MyAspect aspect = new MyAspect();
        Method[] methods = aspect.getClass().getMethods();
        Method aspectMethod = null;
        for (Method m : methods) {
            if (m.getName().equals("round")) {
                aspectMethod = m;
            }
        }
        Method finalAspectMethod = aspectMethod;
        Object proxyInstance = Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        method.invoke(service, args);// 原方法
                        finalAspectMethod.invoke(aspect);// 切面方法
                        return null;
                    }
                });
        TargetService service2 = (TargetService) proxyInstance;
        service2.targetMethod();


        //CGLIB实现
        Enhancer enhancer = new Enhancer(); //创建增强器•
        enhancer.setSuperclass(service.getClass()); //设置父类•
        enhancer.setCallback(new MethodInterceptor() { //设置回调•
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                method.invoke(service, null);// 原方法
                finalAspectMethod.invoke(aspect);
                return null;
            }
        });
        Object cg = enhancer.create();
        TargetService service3 = (TargetService) cg;
        service3.targetMethod();
    }
}
