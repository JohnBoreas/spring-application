package com.message.rabbitmq.service;

/**
 * @author boreas
 * @create 2019-12-18 16:29
 */
public interface DataProcess<T> {

    boolean doInProcess(T t);
}
