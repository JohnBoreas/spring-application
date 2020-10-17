package com.boreas.designpatterns.factory.service;

/**
 * 抽象工厂
 */
public interface Factory {
    /**
     * 生产机器
     * @return
     */
    Machine createMachine();
}
