package snoopy.base.client;

import org.apache.zookeeper.ZooKeeper;
import snoopy.base.loader.SnoopyLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ZookeeperClient {

    public static final String SEPARATOR = "/";
    public static final String INPUT_VAR = "%s";

    public static String systemCode;
    public static String vuserId;
    public static String nameSpace;

    public static String lockNameNodeUrlFormat;
    public static String routeNodeUrl;

    public static String taskQueueParentNodeUrl;
    public static String taskQueueEnvNodeUrl;
    public static Map<String, Object> cacheConfig = new HashMap<>();

    private static ZooKeeper client = null;
    private static CountDownLatch countDownLatch = null;
    private static ExecutorService taskQueueExecutorService = null;

    public static synchronized boolean initSnoopyClient(){
        if (client != null){
            return true;
        }
        if (!initSnoopyLoader()){
            return false;
        }


    }

    private static boolean initSnoopyLoader(){
        boolean isSnoopyLoader = false;
        try {
            if (!SnoopyLoader.isInitSnoopy()){
                throw new Exception();
            }
        }catch (Exception e){

        }
    }

}
