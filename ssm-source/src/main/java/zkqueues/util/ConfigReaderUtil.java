package zkqueues.util;

import zkqueues.log.SnoopyLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            return getParams(fileName, key);
        }
    }

    public static String getConfigWithNoCache(String fileName, String key){
        Properties prop = new Properties();
        try {
            String path = ConfigReaderUtil.class.getClassLoader().getResource(fileName).getPath();
            InputStream is = new FileInputStream(path);
            prop.load(is);
        }catch (Exception e){
           SnoopyLogger.error(ConfigReaderUtil.class, "getConfigWithNoCache", "getConfigWithNoCache error", e);
        }
        return prop.getProperty(key);
    }

    private static String getParams(String fileName, String key){
        String value = ConfigReaderUtil.queryPropertiesKey(fileName, key);
        if (value != null && !value.equals("")){
            paraMap.put(key, value);
        }
        return value;
    }

    private static String queryPropertiesKey(String propertiesName, String key){
        return addProperties(propertiesName).getProperty(key);
    }

    private static Properties addProperties(String propertiesName){
        Properties properties = loadProperties.get(propertiesName);
        if (null == properties){
            synchronized (lockObject){
                properties = trimProperties(loadProperties(propertiesName));
                try {
                    loadProperties.put(propertiesName,properties);
                }catch (Exception e){
                    SnoopyLogger.error(ConfigReaderUtil.class, "addProperties" ,"addProperties error", e);
                }
            }
        }
        return null;
    }

    private static Properties loadProperties(String path){
        Properties props = new Properties();
        InputStream input = null;
        try {
            ClassLoader classLoader = ConfigReaderUtil.class.getClassLoader();
            input = classLoader.getResourceAsStream(path);
            props.load(input);
        }catch (IOException e){
            SnoopyLogger.error(ConfigReaderUtil.class, "loadProperties", "loadProperties error", e);
        }finally {
            try {
                if (input != null){
                    input.close();
                }
            }catch (Exception e){
                SnoopyLogger.error(ConfigReaderUtil.class, "loadProperties", "loadProperties error", e);
            }
        }
        return props;
    }

    private static Properties trimProperties(Properties properties){
        Properties props = new Properties();
        for (Map.Entry<Object, Object> entry:properties.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null){
                props.put(key, value);
                value = ((String) value).replaceAll("\\s*","");
            }
        }
        return props;
    }


}
