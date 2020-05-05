package com.boreas.connection.exception;

/**
 * @author boreas
 * @create 2020-04-22 0:08
 */
public class RedisException extends RuntimeException {

    public RedisException(String errMsg){
        super(errMsg);
    }

    public RedisException(String errMsg, Throwable throwable) {
        super(errMsg, throwable);
    }

}
