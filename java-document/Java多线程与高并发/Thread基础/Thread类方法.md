1、Start（）：启动一个线程，线程处于就绪状态，并没有执行，一旦分配cpu，开始执行run（）方法

2、interrupt（）

3、join（）

4、setDaemon（）

5、setPriority（）

6、sleep（）

```java
public static native void sleep(long millis) throws InterruptedException;
```

7、yield（）

```java
public static native void yield();
```

8、currentThread（）

```java
// 返回当前正在执行的线程对象Returns a reference to the currently executing thread object
public static native Thread currentThread();
```

