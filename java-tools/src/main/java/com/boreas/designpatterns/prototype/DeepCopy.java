package com.boreas.designpatterns.prototype;

import java.io.*;

/**
 * 深拷贝
 */
public class DeepCopy {
    /**
     * 序列化方式深拷贝
     * @param t
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object serializationDeepCopy(Object t) throws IOException, ClassNotFoundException {
        //通过序列化方法实现深拷贝
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(t);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return ois.readObject();
    }
}
