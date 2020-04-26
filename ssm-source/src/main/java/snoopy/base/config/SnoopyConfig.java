package snoopy.base.config;

import org.apache.commons.lang.StringUtils;
import snoopy.util.ConfigReaderUtil;

public class SnoopyConfig {
    public static final String SNOOPY_CONFIG = "snoopy-config.properties";
    public static final String ENV_NAME = getEnvName();
    public static final String SCAN_PACKAGE_URL = getTaskScanPackageUrl();
    public static final String DEBUG_CODE = getTaskDebugCode();
    public static final String DEBUG_PREFIX = "DEBUG";

    public static String getConfigByKey(String key){
        return ConfigReaderUtil.getConfigByFile(SNOOPY_CONFIG, key);
    }

    public static String getConfigByKeyWithNoCache(String key){
        return ConfigReaderUtil.getConfigWithNoCache(SNOOPY_CONFIG, key);
    }

    private static String getEnvName(){
        String envName = SnoopyConfig.getConfigByKey("soopy.deploy.environment.name");
        if (StringUtils.isEmpty(envName)) {
            return null;
        }

        if (envName.length() > 10){
            return null;
        }

        if (envName.contains("dev") || envName.contains("stg") || envName.contains("prd")) {
            return envName;
        }

        return null;
    }

    public static String getTaskScanPackageUrl(){
        String scanPackageUrl = SnoopyConfig.getConfigByKey("snoopy.task.scan.package.url");
        if (StringUtils.isEmpty(scanPackageUrl)){
            return null;
        }
        return scanPackageUrl;
    }

    private static String getTaskDebugCode(){
        return SnoopyConfig.getConfigByKey("snoopy.task.debug.code");
    }

    public static String getZkAddress(){
        return SnoopyConfig.getConfigByKeyWithNoCache("snoopy.zk.address");
    }

}
