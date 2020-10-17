package com.boreas.designpatterns.factory;

import com.boreas.designpatterns.factory.service.Factory;
/**
 * 工厂方法模式
 * @author xuhua.jiang
 * @date 2020-10-12
 */
public class FactoryClient {

    // 把简单工厂的内部逻辑判断转移到了客户端代码来进行,增加一个产品类，需要增加对应的工厂类，开发量增加
    public static void main(String[] args) throws Exception {

        // 使用反射机制实例化工厂对象，因为字符串是可以通过变量改变的
        Factory a = (Factory) Class.forName("com.boreas.designpatterns.factory.service.impl.FactoryA").newInstance();
        // 通过工厂对象创建相应的实例对象
        a.createMachine().create();
        a.createMachine().machining();
    }
}
