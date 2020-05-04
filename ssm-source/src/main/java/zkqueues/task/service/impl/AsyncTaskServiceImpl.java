package zkqueues.task.service.impl;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.base.config.BaseConfig;
import zkqueues.exception.SnoopyError;
import zkqueues.exception.SnoopyException;
import zkqueues.log.SnoopyLogger;
import zkqueues.task.model.TaskModel;
import zkqueues.task.service.AsyncTaskService;
import zkqueues.task.validator.ValidateUtil;
import zkqueues.util.ArrayUtil;
import zkqueues.util.TaskUtil;

public class AsyncTaskServiceImpl implements AsyncTaskService {
    @Override
    public void addAsyncTask(TaskModel model) throws Exception {
        if (!ValidateUtil.inputValidator(model)) {
            throw new SnoopyException(SnoopyError.TASK_PARAM_LOSS_ERROR);
        }

        byte[] modelByte = ArrayUtil.objectToByteArray(model);
        String taskNode = TaskUtil.getTaskNodeUrl(model);
        try {
            ZookeeperClient.getClient().create(taskNode, modelByte, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            if (!"eventNode".equals(model.getQueueKey())) {
                SnoopyLogger.info("addAsyncTask success className:[" + model.getClassName() + "], queueKey:[" + model.getQueueKey() + "]");
            }
        }catch (Exception e){
            if (e instanceof KeeperException.ConnectionLossException
            || e instanceof KeeperException.SessionExpiredException) {
                dealConnectionLossException(taskNode, modelByte);
            }else {
                throw e;
            }
        }
    }


    private void dealConnectionLossException(String taskNode, byte[] modelByte) throws Exception{
        for (int i = 0; i < BaseConfig.RETRY_COUNT ; i++) {
            try {
                ZookeeperClient.close();
                ZookeeperClient.initSnoopyClient();
                ZookeeperClient.getClient().create(taskNode, modelByte, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
                break;
            }catch (Exception ex){
                if (!(ex instanceof KeeperException.ConnectionLossException
                        || ex instanceof KeeperException.SessionExpiredException)) {
                    throw ex;
                }
            }
        }
    }
}
