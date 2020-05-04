package zkqueues.base.client;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;
import zkqueues.base.config.BaseConfig;
import zkqueues.base.config.SnoopyConfig;
import zkqueues.base.loader.SnoopyLoader;
import zkqueues.exception.SnoopyError;
import zkqueues.exception.SnoopyException;
import zkqueues.lock.model.LockNode;
import zkqueues.lock.model.RouteNode;
import zkqueues.log.SnoopyLogger;
import zkqueues.task.model.TaskModel;
import zkqueues.task.processor.AsyncTaskProcessor;
import zkqueues.task.queue.InitConsumeServiceTask;
import zkqueues.task.queue.NodeCleanTask;
import zkqueues.task.queue.TriggerTimer;
import zkqueues.util.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        if (!initZookeeperClient()){
            return false;
        }

        if (!initSnoopySystemNode()) {
            return false;
        }

        if (!initLockServiceNode()) {
            return false;
        }

        initTaskQueueServiceNode();

        printConfigInfo();

        return false;
    }

    public static ZooKeeper getClient(){
        if (client != null){
            return client;
        }
        initSnoopyClient();
        return client;
    }

    private static boolean initSnoopyLoader(){
        boolean isSnoopyLoader = false;
        try {
            if (!SnoopyLoader.isInitSnoopy()){
                throw new SnoopyException(SnoopyError.CLIENT_INIT_ERROR);
            }
            isSnoopyLoader = true;
        }catch (Exception e){
            SnoopyLogger.error(ZookeeperClient.class, "initSnoopyLoader", "SnoopyLoader init error.", e);
        }
        return isSnoopyLoader;
    }

    private static boolean initZookeeperClient(){
        boolean isZKClientInit = false;
        try {
            if (StringUtils.isEmpty(SnoopyConfig.ENV_NAME)) {
                throw new SnoopyException(SnoopyError.CLIENT_PARAM_ENVNAME_ERROR);
            }
            initSnoopyClientParams();
            initCacheConfig();

            SnoopyLogger.info("ZookeeperClient starting...");

            countDownLatch = new CountDownLatch(1);
            String zkAddresses = (String) ZookeeperClient.cacheConfig.get(BaseConfig.ZK_ADDRESS_KEY);
            client = new ZooKeeper(zkAddresses, BaseConfig.SESSION_TIMEOUT, new ZookeeperWatcher(countDownLatch));
            client.addAuthInfo("digest", getAuthInfo(nameSpace).getBytes());
            countDownLatch.await(BaseConfig.SESSION_TIMEOUT, TimeUnit.MILLISECONDS);
            countDownLatch = null;
            isZKClientInit = true;
            SnoopyLogger.info("ZookeeperClient SessionId:" + client.getSessionId());
            SnoopyLogger.info("ZookeeperClient connected.");
            SnoopyLogger.info("ZookeeperClient address:" + zkAddresses);
        }catch (Exception e){
            SnoopyLogger.error(ZookeeperClient.class, "initZookeeperClient", "ZookeeperClient Init error.", e);
        }
        return isZKClientInit;
    }

    public static void initSnoopyClientParams(){
        systemCode = SEPARATOR + "systemCode";
        vuserId = SEPARATOR + "vuserId";
        nameSpace = systemCode + vuserId;
        lockNameNodeUrlFormat = systemCode + SEPARATOR + INPUT_VAR + SEPARATOR + INPUT_VAR;
        routeNodeUrl = systemCode + SEPARATOR + INPUT_VAR;
        taskQueueParentNodeUrl = systemCode + SEPARATOR + "TaskParentNode";
        if (StringUtils.isNotEmpty(SnoopyConfig.DEBUG_CODE)
                && SnoopyConfig.ENV_NAME.contains("dev")){
            String newDebugName = SnoopyConfig.DEBUG_PREFIX
                    + RouteNodeUtil.getHash(SnoopyConfig.DEBUG_CODE);
            taskQueueEnvNodeUrl = taskQueueParentNodeUrl + ZookeeperClient.SEPARATOR + newDebugName;
        }else {
            taskQueueEnvNodeUrl = taskQueueParentNodeUrl + ZookeeperClient.SEPARATOR + SnoopyConfig.ENV_NAME;
        }
    }

    private static void initCacheConfig(){
        String zkAddress = BaseConfig.getZKAddresses();
        ZookeeperClient.cacheConfig.put(BaseConfig.ZK_ADDRESS_KEY, zkAddress);
        ZookeeperClient.cacheConfig.put("cacheTime", System.currentTimeMillis());
    }

    private static String getAuthInfo(String passWord) {
        byte[] digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1").digest(passWord.getBytes());
        }catch (NoSuchAlgorithmException e) {
            SnoopyLogger.error(ZookeeperClient.class, "getAuthInfo", "getAuthInfo error.", e);
        }
        return EncodeUtil.base64Encode(digest);
    }

    private static boolean initSnoopySystemNode(){
        boolean initSnoopySystemNode = false;
        try {
            Stat namespaceStat = client.exists(systemCode, false);
            if (namespaceStat != null) {
                return true;
            }
            client.create(systemCode, new byte[0], ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            SnoopyLogger.info("System node inited");

            Stat childNodeStat = client.exists(nameSpace, false);
            if (childNodeStat != null){
                return true;
            }

            client.create(nameSpace, new byte[0], ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            initSnoopySystemNode = true;
            SnoopyLogger.info("Namespace node initd.");
        }catch (KeeperException e) {
            SnoopyLogger.error(ZookeeperClient.class, "initSnoopySystemNode", "Namespace node inited error.", e);
        }catch (InterruptedException e){
            SnoopyLogger.error(ZookeeperClient.class, "initSnoopySystemNode", "Namespace node Interrupted exception.", e);
        }
        return initSnoopySystemNode;
    }

    private static boolean initLockServiceNode(){
        boolean isinitRouteNode = false;
        try {
            for (int i = 0; i < BaseConfig.ROUTENODE_COUNT;i++) {
                RouteNodeUtil.addRouteNode(SnoopyConfig.ENV_NAME + ":" + "routenode" + i);
            }
            if (isRouteNodeCreated()){
                return true;
            }

            createRouteNode();

            isinitRouteNode = true;

            SnoopyLogger.info("lock service route node inited.");

        }catch (KeeperException e){
            SnoopyLogger.error(ZookeeperClient.class, "initLockServiceNode", "initLockServiceNode init error.", e);
        }catch (InterruptedException e){
            SnoopyLogger.error(ZookeeperClient.class, "initLockServiceNode", "initLockServiceNode Interrupted exception.", e);
        }
        return isinitRouteNode;
    }

    private static boolean isRouteNodeCreated() throws KeeperException, InterruptedException {
        for (RouteNode routeNode:RouteNodeUtil.routeNodes) {
            Stat childNodeStat = client.exists(LockNode.getRouteNodeUrl(routeNode), false);
            if (childNodeStat == null) {
                return false;
            }
            break;
        }
        return true;
    }

    private static void createRouteNode() throws KeeperException,InterruptedException{
        for (RouteNode routeNode:RouteNodeUtil.routeNodes){
            client.create(LockNode.getRouteNodeUrl(routeNode), new byte[0], ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }
    }

    public static void initTaskQueueServiceNode(){

        if (StringUtils.isEmpty(SnoopyConfig.SCAN_PACKAGE_URL)) {
            SnoopyLogger.info("UserSystem can not access snoopy async task service.");
            return;
        }

        try {
            List<String> callBackClasses = PackageUtil.getClasses(SnoopyConfig.SCAN_PACKAGE_URL);
            if (callBackClasses == null || callBackClasses.size() == 0) {
                throw new Exception("Snoopy can not find classes.");
            }

            List<String> processors = getAllProccessorImpl(callBackClasses);
            if (CollectionUtils.isEmpty(processors)) {
                throw  new Exception("Snoopy can not find callback serviceImpl.");
            }

            List<String> proccessorNames = getShortNames(processors);
            if (CollectionUtils.isEmpty(proccessorNames)) {
                throw new Exception("Snoopy callback serviceimpl class name error.");
            }

            Map<String ,String> taskQueueMap = judgeQueueTaskNode(proccessorNames);

            initTaskQueueExecutorService(proccessorNames.size() + 2);

            for (String processorName : proccessorNames) {
                String processor = NodeCacheUtil.getProcessNameMap().get(processorName);
                taskQueueExecutorService.submit(new InitConsumeServiceTask(processor,taskQueueMap.get(processorName)));
            }

            if (SnoopyConfig.ENV_NAME.contains("dev") && !SnoopyConfig.ENV_NAME.contains("ci")) {
                taskQueueExecutorService.submit(new NodeCleanTask());
            }

            taskQueueExecutorService.submit(new TriggerTimer(proccessorNames));
        }catch (Exception e){
            SnoopyLogger.error(ZookeeperClient.class,"initTaskQueueServiceNode", e.getMessage(), e);
        }

    }

    private static List<String> getAllProccessorImpl(List<String> callBackClasses) throws ClassNotFoundException {
        List<String> allProccessorImpl = new ArrayList<>();
        for (String className : callBackClasses) {
            Class<?> clazz = ZookeeperClient.class.getClassLoader().loadClass(className);
            boolean assignableFrom = AsyncTaskProcessor.class.isAssignableFrom(clazz);
            boolean isAbs = Modifier.isAbstract(clazz.getModifiers());
            if (assignableFrom && !isAbs) {
                SnoopyLogger.info("Snoopy get callback serviceImpl class [" + className + "].");
                allProccessorImpl.add(className);
            }
        }
        return allProccessorImpl;
    }

    private static List<String> getShortNames(List<String> proccessors) throws Exception {
        List<String> result = new ArrayList<>();
        for (String name : proccessors) {
            String shortName = name.substring(name.lastIndexOf(".") + 1, name.length());
            result.add(shortName);
            NodeCacheUtil.getProcessNameMap().put(shortName, name);
            NodeCacheUtil.getProcessNameMap().put(name, shortName);
            Class<?> clazz = ReflectUtil.getNewInstance(ZookeeperClient.class.getClassLoader().loadClass(name)).getClass();
            Method method = ReflectUtil.getMethodFromSelfOrParent(clazz, "doProcess", TaskModel.class);
            NodeCacheUtil.getInvokeMethodMap().put(shortName, method);
        }
        return result;
    }

    private static Map<String, String> judgeQueueTaskNode(List<String> proccessorNames) throws KeeperException, InterruptedException, SnoopyException{
        Map<String, String> taskQueueMap = new HashMap<>();
        Stat isTaskParentNodeCreated = client.exists(taskQueueParentNodeUrl, null);
        if (isTaskParentNodeCreated == null) {
            SnoopyLogger.info("Snoopy task service init task queue parent node.");
            client.create(taskQueueParentNodeUrl, new byte[]{}, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }
        Stat isTaskQueueEnvNodeCreated = client.exists(taskQueueEnvNodeUrl, null);

        if (isTaskQueueEnvNodeCreated == null && StringUtils.isEmpty(SnoopyConfig.DEBUG_CODE)) {
            SnoopyLogger.info("Snoopy task service init task queue env node.");
            client.create(taskQueueEnvNodeUrl, new byte[]{}, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }

        if (StringUtils.isNotEmpty(SnoopyConfig.DEBUG_CODE) && isTaskQueueEnvNodeCreated == null) {
            List<String> children = ZookeeperClient.getClient().getChildren(taskQueueParentNodeUrl, null);
            if (children != null && children.size() > 20) {
                throw new SnoopyException(SnoopyError.TASK_DEV_TOOMANYNODE_ERROR);
            }
            SnoopyLogger.info("Snoopy task service init task queue env node.");
            long currentTime = System.currentTimeMillis();
            client.create(taskQueueEnvNodeUrl, String.valueOf(currentTime).getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }

        if (StringUtils.isNotEmpty(SnoopyConfig.DEBUG_CODE) && isTaskQueueEnvNodeCreated != null) {
            client.setData(taskQueueEnvNodeUrl, String.valueOf(System.currentTimeMillis()).getBytes(),-1);
        }

        for (String processorName:proccessorNames) {
            String taskQueueNodeUrl = TaskUtil.getTaskQueueNodeUrl(processorName);
            taskQueueMap.put(processorName, taskQueueNodeUrl);
            Stat isTaskQueueNodeCreated = client.exists(taskQueueNodeUrl ,null);
            if (isTaskQueueNodeCreated == null) {
                client.create(taskQueueNodeUrl, new byte[]{}, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
                SnoopyLogger.info("Snoopy task service init task queue node [" + processorName + "].");
            }
        }

        List<String> taksQueueNodes = client.getChildren(taskQueueEnvNodeUrl,null);
        for (String taskQueueNode : taksQueueNodes) {
            if (!proccessorNames.contains(taskQueueNode)) {
                String taskQueueNodeUrl = TaskUtil.getTaskQueueNodeUrl(taskQueueNode);
                client.delete(taskQueueNodeUrl, -1);
                SnoopyLogger.info("Snoopy task service clean task queue node [" + taskQueueNode + "].");
            }
        }

        return taskQueueMap;
    }

    private static void initTaskQueueExecutorService(int threeCount) {
        taskQueueExecutorService = Executors.newFixedThreadPool(threeCount);
    }

    public static void printConfigInfo(){
        StringBuffer message = new StringBuffer();
        message.append("\n [RMS Snoopy ConfigInfo] \n");
        message.append("***************************************");
        message.append("System:RMS Snoopy \n");
        message.append("Version: " + ZookeeperClientVersion.VERSION);
        message.append("State: State Success \n");
        message.append("***************************************");
        message.append(" [RMS Snoopy ConfigInfo] ");
        SnoopyLogger.info(message.toString());
    }

    public static void close(){
        if (client != null){
            SnoopyLogger.error(ZookeeperClient.class, "close", "ZookeeperClient old session expired.", new Exception());
            try {
                client.close();
                client = null;
            }catch (InterruptedException e){
                SnoopyLogger.error(ZookeeperClient.class, "close", "ZookeeperClient session expired InterruptedException:", e);
            }
        }
    }
}
