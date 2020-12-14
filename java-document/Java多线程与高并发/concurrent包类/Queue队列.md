###### DelayQueue

一个使用优先级队列实现的无界阻塞队列，用于放置实现了Delayed接口的对象。

支持延时获取的元素的阻塞队列

存放到DelayDeque的元素必须继承Delayed接口。

适用场景：实现自己的缓存系统，订单到期，限时支付等等。

| 方法     | 抛出异常 | 返回值 | 一直阻塞 | 超时退出    |
| -------- | -------- | ------ | -------- | ----------- |
| 插入方法 | add      | offer  | put      | Offer(time) |
| 移除方法 | remove   | poll   | take     | Poll(time)  |
| 检查方法 | element  | peek   | N/A      | N/A         |

```java
public class DelayedElement<T> implements Delayed {
    private final long delay; // 延迟时间
    private final long expire;  // 到期时间
    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
```

方法：

```java
// put() = offer()
public void put(E e) {
    offer(e);
}
```