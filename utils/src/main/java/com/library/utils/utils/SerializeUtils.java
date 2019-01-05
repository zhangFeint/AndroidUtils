package com.library.utils.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 功能：
 *     序列化对象
 * @author：zhangerpeng
 * @create：2018\11\5 0005 15:06
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class SerializeUtils {
    private static long startTime = 0l;
    private static long endTime = 0l;

    /**
     * 序列化对象
     *
     * @param object   对象必须实现 implements Serializable 接口
     * @return
     * @throws IOException
     */
    public static String serialize(Object object) throws Exception {
        startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        endTime = System.currentTimeMillis();
//        System.out.println("序列化耗时为" + (endTime - startTime));
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return  对象必须实现 implements Serializable 接口
     * @throws Exception

     */
    public static Object deSerialization(String str) throws Exception {
        startTime = System.currentTimeMillis();
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.close();
        byteArrayInputStream.close();
        endTime = System.currentTimeMillis();
//        System.out.println("反序列化耗时为" + (endTime - startTime));
        return objectInputStream.readObject();
    }
}
