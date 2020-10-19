package com.boreas.designpatterns.builder;

/**
 * 抽象生成器
 */
public interface Builder {

    void buildName();
    void buildSize();
    void buildTextField();
    Product createProduct();
}
