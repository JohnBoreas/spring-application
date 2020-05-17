package com.boreas.thread;

/**
 * @author boreas
 * @create 2020-05-17 22:01
 */
public class LockTest {
    /**
     * 锁消除
     * StringBuffer线程安全，synchronized方法add，stringBuffer只会在add中使用，不被其他线程使用（局部变量，栈私有），sb不可共享资源。JVM自动消除stringBuffer对象内部锁
     * @param str1
     * @param str2
     */
    public void lockEliminate(String str1, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str1).append(str2);
    }
    /**
     * 锁粗化
     * JVM检测到一连串加锁操作都是对同一对象，此JVM会将锁的范围粗化到这一连串操作外部，使得只加一次锁
     * @return
     */
    public String lockCoarsening(String str) {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while (i < 100) {
            stringBuffer.append(str);
            i ++;
        }
        return stringBuffer.toString();
    }
}
