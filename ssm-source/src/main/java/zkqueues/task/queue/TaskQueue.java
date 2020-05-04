package zkqueues.task.queue;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.base.config.BaseConfig;
import zkqueues.log.SnoopyLogger;
import zkqueues.task.model.TaskModel;
import zkqueues.task.processor.AsyncTaskProcessor;
import zkqueues.util.ArrayUtil;
import zkqueues.util.ReflectUtil;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskQueue {

    private static ExecutorService taskExecutorService = Executors.newFixedThreadPool(200);

    public static final String TASK_PREFIX = "taks-";

    public void initConsumerService(String taskQueueClassName, String taskQueueNodeUrl) {

        AsyncTaskProcessor invokeInstance;
        try {
            invokeInstance = (AsyncTaskProcessor) ReflectUtil.
                    getNewInstance(TaskQueue.class.getClassLoader().loadClass(taskQueueClassName));
            ZookeeperClient.getClient().getChildren(taskQueueNodeUrl, invokeInstance);

        }catch (Exception e){
            SnoopyLogger.error(this.getClass(), "initConsumerService",
                    "[Task] [" + taskQueueClassName + "] Reflect AsyncTaskProcessor error.", e);
            return;
        }

        taskExecutorService.submit(new WatcherTimer(invokeInstance, taskQueueNodeUrl));
        TreeMap<Long,String> sortedChildren = null;
        while (true) {
            try {
                sortedChildren = getSortedChildren(invokeInstance, taskQueueNodeUrl);
                if (sortedChildren.size() == 0) {
                    invokeInstance.await();
                    continue;
                }
            }catch (KeeperException.NoNodeException e){
                continue;
            }catch (KeeperException | InterruptedException e) {
                SnoopyLogger.error(this.getClass(), "initConsumerService",
                        "[Task][" + taskQueueClassName + "] get SortedChildren error.", e);
            }

            int consumeCount = 0;
            for (String childNode : sortedChildren.values()) {
                String path = taskQueueNodeUrl + ZookeeperClient.SEPARATOR + childNode;
                try {
                    byte[] data = ZookeeperClient.getClient().getData(path, false, null);
                    ZookeeperClient.getClient().delete(path, -1);
                    TaskModel taskModel = ArrayUtil.byteArrayToObject(data, TaskModel.class);
                    if (taskModel.getQueueKey() != null && taskModel.getQueueKey().equals("eventNode")) {
                        continue;
                    }
                    consumeCount++;
                    SnoopyLogger.info("initConsumerService InvokeCallBackTask className:[" + taskModel.getClassName() + "], queueKey:[" + taskModel.getQueueKey() + "]");
                    taskExecutorService.submit(new InvokeCallBackTask(invokeInstance, taskModel));

                    if (consumeCount == BaseConfig.TASKQUEUE_CONSUME_COUNT) {
                        break;
                    }

                }catch (KeeperException.NoNodeException e){
                    continue;
                }catch (KeeperException | InterruptedException e) {
                    SnoopyLogger.error(this.getClass(), "initConsumerService" ,
                            "[Task] [" + taskQueueClassName + "][" + childNode + "] consume error.", e);
                }
            }
        }
    }

    private TreeMap<Long, String> getSortedChildren(Watcher watcher, String taskQueueNodeUrl) throws KeeperException, InterruptedException {
        TreeMap<Long, String> sortedChildren = new TreeMap<>();
        List<String> childNodeNames = null;
        try {
            childNodeNames = ZookeeperClient.getClient().getChildren(taskQueueNodeUrl, watcher);
        }catch (KeeperException.NoNodeException e){
            throw e;
        }

        for (String childNodeName : childNodeNames) {
            try {
                if (!childNodeName.regionMatches(0, TASK_PREFIX, 0, TASK_PREFIX.length())){
                    SnoopyLogger.info("[Task] [" + childNodeName + "] Found error with child node name.");
                    continue;
                }
                String suffix = childNodeName.substring(TASK_PREFIX.length());
                Long childId = new Long(suffix);
                sortedChildren.put(childId, childNodeName);
            }catch (NumberFormatException e){
                SnoopyLogger.error(this.getClass(), "getSortedChildren" ,
                        "[Task][" + childNodeName + "] Found error with child node name." ,e);
            }
        }
        return sortedChildren;
    }
}
