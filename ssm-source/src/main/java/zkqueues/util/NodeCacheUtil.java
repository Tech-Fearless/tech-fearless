package zkqueues.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NodeCacheUtil {

    private static volatile Map<String, Method> invokeMethodMap = new HashMap<>();

    private static volatile Map<String, String> processNameMap = new HashMap<>();

    public static Map<String, String> getProcessNameMap(){
        return processNameMap;
    }

    public static Map<String, Method> getInvokeMethodMap(){
        return invokeMethodMap;
    }

}
