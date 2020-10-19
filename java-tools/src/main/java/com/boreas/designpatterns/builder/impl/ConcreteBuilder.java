package com.boreas.designpatterns.builder.impl;

import com.boreas.designpatterns.builder.Builder;
import com.boreas.designpatterns.builder.Product;

public class ConcreteBuilder implements Builder {

    private Product product;

    ConcreteBuilder(){
        product = new Product();
    }

    public void buildName() {
        product.name = new String("name");
    }

    public void buildSize() {
        product.size = 1;
    }

    public void buildTextField() {
        product.textField = "test";
    }

    public Product createProduct() {
        return product;
    }
}
