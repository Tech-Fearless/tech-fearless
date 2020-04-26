package snoopy.util;

import snoopy.base.client.ZookeeperClient;
import snoopy.task.model.TaskModel;
import snoopy.task.queue.TaskQueue;

public class TaskUtil {

    public static String getTaskNodeUrl(TaskModel taskModel){
        StringBuffer sb = new StringBuffer();
        String className = taskModel.getClassName();
        StringBuffer result = sb.append(ZookeeperClient.taskQueueEnvNodeUrl)
                .append(ZookeeperClient.SEPARATOR)
                .append(className)
                .append(ZookeeperClient.SEPARATOR)
                .append(TaskQueue.TASK_PREFIX);
        return result.toString();
    }

    public static String getTaskQueueNodeUrl(TaskModel taskModel){
        StringBuffer sb = new StringBuffer();
        String className = taskModel.getClassName();
        StringBuffer result = sb.append(ZookeeperClient.taskQueueEnvNodeUrl)
                .append(ZookeeperClient.SEPARATOR)
                .append(className);
        return result.toString();
    }

    public static String getTaskQueueNodeUrl(String nodeName) {
        StringBuffer sb = new StringBuffer();
        StringBuffer result = sb.append(ZookeeperClient.taskQueueEnvNodeUrl)
                .append(ZookeeperClient.SEPARATOR)
                .append(nodeName);
        return result.toString();
    }

}
