package com.boreas.jvm;

/**
 * 测试对象可在finalize方法中可进行一次自救的过程
 * @author boreas
 * @create 2021-07-14 16:31
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVA_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still  alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVA_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVA_HOOK = new FinalizeEscapeGC();

        //对象第一次成功拯救自己
        SAVA_HOOK = null;
        System.gc();
        //因为finalize方法的优先级比较低,所以暂停0.5秒等待finalize方法执行
        Thread.sleep(500L);
        if (SAVA_HOOK != null) {
            SAVA_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        //下面这段代码与上一段相同,但是这一次自救失败了
        SAVA_HOOK = null;
        System.gc();
        Thread.sleep(500L);
        if (SAVA_HOOK != null) {
            SAVA_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

    }
}
