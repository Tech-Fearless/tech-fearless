package zkqueues.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {

    public static List<String> getAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        int len = annotations.length;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < len; i++){
            Annotation annotation = annotations[i];
            String annotationName = annotation.annotationType().getSimpleName();
            list.add(annotationName);
        }
        return list;
    }

    public static Object getNewInstance(Class<?> clazz)
            throws InstantiationException, IllegalAccessException{
        return clazz.newInstance();
    }

    public static Method getMethod(Class<?> clazz,String name, Class<?>... parameterTypes)
        throws NoSuchMethodException, SecurityException{
        return clazz.getDeclaredMethod(name, parameterTypes);
    }

    public static Object invokeMethod(Method method, Object obj, Object... args)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    public static Method getMethodFromSelfOrParent(Class<?> clazz, String methodName, Class<?> classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        }catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            }catch (NoSuchMethodException ex){
                if (clazz.getSuperclass() == null || clazz.getSuperclass() == Object.class){
                    return method;
                }else {
                    method = getMethodFromSelfOrParent(clazz.getSuperclass(), methodName, classes);
                }
            }
        }
        return method;
    }
}
