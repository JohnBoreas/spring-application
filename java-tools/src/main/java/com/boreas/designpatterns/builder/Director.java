package com.boreas.designpatterns.builder;

import com.boreas.designpatterns.builder.impl.ConcreteBuilder;

/**
 * 指挥者
 */
public class Director {
    /**
     * （1）产品(Product)：具体生产器要构造的复杂对象；
     *
     * （2）抽象生成器(Bulider)：抽象生成器是一个接口，该接口除了为创建一个Product对象的各个组件定义了若干个方法之外，还要定义返回Product对象的方法（定义构造步骤）；
     *
     * （3）具体生产器(ConcreteBuilder)：实现Builder接口的类，具体生成器将实现Builder接口所定义的方法（生产各个组件）；
     *
     * （4）指挥者(Director)：指挥者是一个类，该类需要含有Builder接口声明的变量。指挥者的职责是负责向用户提供具体生成器，即指挥者将请求具体生成器类来构造用户所需要的Product对象，如果所请求的具体生成器成功地构造出Product对象，指挥者就可以让该具体生产器返回所构造的Product对象。
     */
    private Builder builder;

    public void setComputerBuilder(ConcreteBuilder concreteBuilder) {
        this.builder = concreteBuilder;
    }

    public Product getComputer() {
        return builder.createProduct();
    }

    public void concreteBuilder() {
        builder.buildName();
        builder.buildSize();
        builder.buildTextField();
    }
}
