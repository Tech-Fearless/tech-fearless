package snoopy.base.config;

import org.apache.commons.lang.StringUtils;
import snoopy.log.SnoopyLogger;
import snoopy.util.ConfigReaderUtil;

public class BaseConfig {

    private static final int DEFAULT_SESSION_TIMEOUT = 2000;
    private static final int DEFAULT_ROUTENODE_COUNT = 20;
    private static final int DEFAULT_TASKQUEUE_CONSUME_COUNT = 2;
    private static final int DEFAULT_ZKADDRESS_TIMEOUT = 60000;

    public static final int SESSION_TIMEOUT = getSessionTimeout();
    public static final int ROUTENODE_COUNT = getRouteNodeCount();
    public static final int TASKQUEUE_CONSUME_COUNT = getTaskQueueConsumeCount();

    public static final long DEBUG_NODE_TIMEOUT = getDebugNodeTimeOut();
    public static final int DEFAULT_ZKADDR_TIMEOUT = getZKConfigTimeout();

    public static final int RETRY_COUNT = getZKRetryCount();
    public static final long TIME_MS = getZKTimerMs();

    public static final String ZK_ADDRESS = getZkAddress();
    public static final String ZK_ADDRESS_KEY = "zkAddresses";

    public static final String SNOOPY_DEFAULT_CONFIG = "base-config.properties";

    public static String getConfigBykey(String key){
        return ConfigReaderUtil.getConfigByFile(SNOOPY_DEFAULT_CONFIG, key);
    }

    private static int getSessionTimeout(){
        String sessionTimeoutStr = BaseConfig.getConfigBykey("snoopy.zk.session.timeout.ms");
        int sessionTimeout = DEFAULT_SESSION_TIMEOUT;

        try {
            sessionTimeout = Integer.valueOf(sessionTimeoutStr);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class , "getSessionTimeout", "getSessionTimeout init error.", e);
        }
        return sessionTimeout;
    }

    private static int getZKConfigTimeout(){
        String zkAddrTimeoutStr = BaseConfig.getConfigBykey("snoopy.zk.config.timeout.ms");
        int zkAddrTimeout = DEFAULT_ZKADDR_TIMEOUT;
        try {
            zkAddrTimeout = Integer.valueOf(zkAddrTimeoutStr);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class , "getZKConfigTimeout", "getZKConfigTimeout init error.", e);
        }
        return zkAddrTimeout;
    }

    private static long getDebugNodeTimeOut(){
        String debugNodeTimeStr = BaseConfig.getConfigBykey("snoopy.zk.task.debug.node.cache.timeout.sec");
        long debugTimeOut = 0;
        try {
            debugTimeOut = Long.valueOf(debugNodeTimeStr);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class ,"getDebugNodeTimeOut", "getDebugNodeTimeOut init error.", e);
        }
        return debugTimeOut;
    }

    private static String getZkAddress(){
        String envName = SnoopyConfig.ENV_NAME;
        if (StringUtils.isEmpty(envName)){
            return null;
        }
        if (envName.contains("dev")) {
            return BaseConfig.getConfigBykey("snoopy.zk.address.dev");
        }
        if (envName.contains("stg")) {
            return BaseConfig.getConfigBykey("snoopy.zk.address.stg");
        }
        if (envName.contains("prd")) {
            return BaseConfig.getConfigBykey("snoopy.zk.address.prd");
        }
        return null;
    }

    public static String getZKAddresses(){
        String snoopyConfigZKAddress = SnoopyConfig.getZkAddress();
        if (StringUtils.isNotEmpty(snoopyConfigZKAddress)) {
            return snoopyConfigZKAddress;
        }else {
            return BaseConfig.ZK_ADDRESS;
        }
    }

    private static int getRouteNodeCount(){
        String routeNodeCountStr = BaseConfig.getConfigBykey("snoopy.zk.routenode.count");
        int routeNodeCount = DEFAULT_ROUTENODE_COUNT;
        try {
            routeNodeCount = Integer.valueOf(routeNodeCount);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class ,"getRouteNodeCount", "getRouteNodeCount init error.", e);
        }
        return routeNodeCount;
    }

    private static int getTaskQueueConsumeCount(){
        int consumeCount = DEFAULT_TASKQUEUE_CONSUME_COUNT;
        try {
            String envName = BaseConfig.getConfigBykey("snoopy.deploy.environment.name");
            String devConsumeCountStr = BaseConfig.getConfigBykey("snoopy.zk.task.taskqueue.dev.consume.count");
            String stgConsumeCountStr = BaseConfig.getConfigBykey("snoopy.zk.task.taskqueue.stg.consume.count");
            String prdConsumeCountStr = BaseConfig.getConfigBykey("snoopy.zk.task.taskqueue.prd.consume.count");
            if (envName.contains("dev")) {
                consumeCount = Integer.valueOf(devConsumeCountStr);
            }
            if (envName.contains("stg")) {
                consumeCount = Integer.valueOf(stgConsumeCountStr);
            }
            if (envName.contains("prd")) {
                consumeCount = Integer.valueOf(prdConsumeCountStr);
            }

        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class ,"getTaskQueueConsumeCount","getTaskQueueConsumeCount init error.", e);
        }
        return consumeCount;
    }

    private static int getZKRetryCount(){
        String countStr = BaseConfig.getConfigBykey("snoopy.zk.retry.count");
        int count = 0;
        try {
            count = Integer.valueOf(countStr);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class ,"getZKRetryCount", "getZKRetryCount init error.", e);
        }
        return count;
    }

    private static long getZKTimerMs(){
        String countStr = BaseConfig.getConfigBykey("snoopy.zk.timer.ms");
        long count = 0;
        try {
            count = Long.valueOf(countStr);
        }catch (Exception e){
            SnoopyLogger.error(BaseConfig.class ,"getZKTimerMs","getZKTimerMs init error.", e);
        }
        return count;
    }
}
