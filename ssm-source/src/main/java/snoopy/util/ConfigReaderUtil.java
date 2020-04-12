package snoopy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReaderUtil {
    private static Map<String, Properties> loadProperties = new HashMap<>();
    private static Map<String, Object> paraMap = new HashMap<>();

    private static final Object lockObject = new Object();

    public static String getConfigByFile(String fileName, String key){
        boolean isDataExist = paraMap.get(key) == null ? false : true;
        if (isDataExist){
            return (String) paraMap.get(key);
        }else {
            return "";
        }
    }

    private static String getParam(String fileName, String key){
        return "";
    }

    private static Properties addProperties(String propertiesName){
        Properties properties = loadProperties.get(propertiesName);
        if (null == properties){
            synchronized (lockObject){
                //properties = trimProperties()
            }
        }
        return null;
    }

    private static Properties trimProperties(Properties properties){
        Properties props = new Properties();
        for (Map.Entry<Object, Object> entry:properties.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null){
                value = ((String) value).replaceAll("\\s*","");
                props.put(key, value);
            }
        }
        return props;
    }
}
