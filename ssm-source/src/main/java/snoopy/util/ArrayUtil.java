package snoopy.util;

import snoopy.log.SnoopyLogger;

import java.io.*;

public class ArrayUtil {

    public static byte[] objectToByteArray(Object object){
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            SnoopyLogger.error(ArrayUtil.class, "objectToByteArray", "objectToByteArray field", e);
        }finally {
            if (objectOutputStream != null){
                try {
                    objectOutputStream.close();
                }catch (IOException e){
                    SnoopyLogger.error(ArrayUtil.class,"objectToByteArray", "objectOutputStream close", e);
                }
            }
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                }catch (IOException e){
                    SnoopyLogger.error(ArrayUtil.class,"byteArrayOutputStream", "byteArrayOutputStream close", e);
                }
            }
        }
        return bytes;
    }

    public static  <T> T byteArrayToObject(byte[] bytes, Class<?> clazz){
        T obj = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object result = objectInputStream.readObject();
            if (clazz.isInstance(result)) {
                obj = (T) result;
            }
        }catch (Exception e){
            SnoopyLogger.error(ArrayUtil.class, "byteArrayToObject", "byteArrayToObject field", e);
        }finally {
            if (byteArrayInputStream != null){
                try {
                    byteArrayInputStream.close();
                }catch (IOException e){
                    SnoopyLogger.error(ArrayUtil.class, "byteArrayToObject", "byteArrayInputStream close failed", e);
                }
            }
            if (objectInputStream != null){
                try {
                    objectInputStream.close();
                }catch (IOException e){
                    SnoopyLogger.error(ArrayUtil.class, "objectInputStream", "objectInputStream close failed", e);
                }
            }
        }
        return obj;
    }

}
