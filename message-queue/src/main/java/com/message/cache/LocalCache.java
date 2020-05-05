package com.message.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author boreas
 * @create 2020-02-26 下午 11:30
 */
public class LocalCache {
    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);
    private static final int DEFAULT_MAX_NUMBER = 100; //默认最大缓存对象数

    private final Map<String, Value> cache; //真正存储数据的Map，使用ConcurrentHashMap
    private final int maxNumber; //最大对象数
    //并发控制器，很重要，防止高并发下本地缓存对象个数超过maxNumber
    private final AtomicInteger cur = new AtomicInteger(0);

    /**
     * 使用默认最大对象数100
     */
    public LocalCache() {
        this(DEFAULT_MAX_NUMBER);
    }

    public LocalCache(int maxNumber) {
        this.maxNumber = maxNumber;
        this.cache = new ConcurrentHashMap<>(maxNumber);
    }

    /**
     * 添加
     * 判断是否超过最大限制 如果超过触发一次全量过期
     * 如果全量过期后还不够返回false
     *  由于1 2 不是原子的所以需要使用单独的AtomicInteger来控制
     *
     * @param key    对应的key
     * @param value  值
     * @param expire 过期时间 单位毫秒
     */
    public boolean put(String key, Object value, long expire) {
        if (key == null || key.length() == 0 || value == null || expire < 0) {
            logger.error("本地缓存put参数异常");
            return false;
        }
        if (!incr()) { //如果CAS增加失败直接返回添加失败
            return false;
        }
        if (isOver()) { //判断是否需要过期
            expireAll(); //触发一次全量过期
            if (isOver()) { //二次检查
                logger.error("本地缓存put时全量过期后还没有空间");
                decr();
                return false;
            }
        }
        putValue(key, value, expire);
        return true;
    }

    /**
     * 获取时判断过期时间
     * 在这里实现懒过期
     */
    public Object get(String key) {
        Value v = cache.get(key);
        if (v == null) {
            return null;
        }
        if (isExpired(v)) {
            logger.info("本地缓存key={}已经过期", key);
            removeValue(key);
            return null;
        }
        return v.value;
    }

    /**
     * 判断是否过期，实现很简单
     */
    private boolean isExpired(Value v) {
        long current = System.currentTimeMillis();
        return current - v.updateTime > v.expire;
    }

    /**
     * 扫描所有的对象对需要过期的过期
     */
    private void expireAll() {
        logger.info("开始过期本地缓存");
        for (Map.Entry<String, Value> entry : cache.entrySet()) {
            if (isExpired(entry.getValue())) {
                removeValue(entry.getKey());
            }
        }
    }

    /**
     * 为了保证cur和Map的size时刻保持一致这里我查询了put的注释及ConcurrentHashMap底层关于put的实现。
     * 发现如果put方法返回的不是null说明存在覆盖操作，如果是覆盖那么Map的size其实没有变，因为我们添加之前把cur的值增加
     * 上去了所以要在这里减下来。
     */
    private void putValue(String key, Object value, long expire) {
        Value v = new Value(System.currentTimeMillis(), expire, value);
        if (cache.put(key, v) != null) {//存在覆盖 使得cur和map的size统一
            decr();
        }
    }

    /**
     * 这里也是为了保证cur和Map的size时刻保持一致只有在remove方法返回的不是null时才证明真正有对象被删除了，才需要把
     * cur减下来。这里出现remove返回为null是因为可能存在并发删除，两个线程删除同一个对象只能有一个删除成功(返回不是
     * null)，另一个(返回null)如果也减小了cur的值，会造成cur和Map的size不一致。
     */
    private void removeValue(String key) {
        if (cache.remove(key) != null) { //真正删除成功了  使得cur和map的size统一
            decr();
        }
    }

     /**
     * 这里很重要，原来我使用的是cache.size() >= maxNumber;
     * 但是如果使用map本身的size方法会存在获取size和putValue方法不是原子的，
     * 可能多个线程同时都判断那时候还没执行putValue方法，线程都认为还没有满，大家都执行了putValue方法造成数据太多
     */
    private boolean isOver() {
        return cur.get() > maxNumber;
    }

    private boolean incr() {
        int c = cur.get();
        return cur.compareAndSet(c, ++c);
    }

    /**
     * 因为CAS不一定是一定成功的
     * 所以这里通过循环保证成功
     */
    private void decr() {
        for (; ; ) {
            int c = cur.get();
            if (c == 0) {
                logger.error("LocalCache decr cur is 0");
                return;
            }
            if (cur.compareAndSet(c, --c)) {
                return;
            }
        }
    }
    private static class Value {
        private long updateTime; //更新时间
        private long expire; //有效期
        private Object value; //真正的对象

        private Value(long updateTime, long expire, Object value) {
            this.updateTime = updateTime;
            this.expire = expire;
            this.value = value;
        }
    }
}
