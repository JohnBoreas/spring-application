CyclicBarrier 循环屏障,让多个线程在这个屏障前等待，直到所有线程都达到这个屏障时，再一起继续执行后面的动作



```java
// 第一个参数：相互等待的线程数量（方数）
// 第二个参数：当最后一个线程到达屏障点的时候所有做的事情
CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new TourGuideTask());

// 屏障，线程在此等待，直到所有线程到达屏障
barrier.await();
```