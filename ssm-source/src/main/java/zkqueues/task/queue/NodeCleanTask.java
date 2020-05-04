package zkqueues.task.queue;

import org.springframework.util.CollectionUtils;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.base.config.BaseConfig;
import zkqueues.base.config.SnoopyConfig;
import zkqueues.log.SnoopyLogger;
import zkqueues.util.RouteNodeUtil;

import java.util.List;

public class NodeCleanTask implements Runnable{

    @Override
    public void run() {
        try {
            List<String> childrenParentNode = ZookeeperClient.getClient().getChildren(ZookeeperClient.taskQueueParentNodeUrl, null);
            for (String eachEnvNode : childrenParentNode) {
                if (eachEnvNode.contains(SnoopyConfig.ENV_NAME) || eachEnvNode.equals(SnoopyConfig.DEBUG_PREFIX + RouteNodeUtil.getHash(SnoopyConfig.DEBUG_CODE))) {
                    continue;
                }
                byte[] data = ZookeeperClient.getClient().getData(ZookeeperClient.taskQueueParentNodeUrl + "/" + eachEnvNode, null , null);
                long oldTime = 0;
                if (data != null && data.length > 0) {
                    oldTime = Long.valueOf(new String(data));
                }
                long currentTime = System.currentTimeMillis();
                long subTime = currentTime - oldTime;
                if (subTime > BaseConfig.DEBUG_NODE_TIMEOUT * 1000) {
                    List<String> children = ZookeeperClient.getClient().getChildren(ZookeeperClient.taskQueueParentNodeUrl + "/" + eachEnvNode, null);
                    recursiveDeleteChildNode(ZookeeperClient.taskQueueParentNodeUrl + "/" + eachEnvNode, children);
                    ZookeeperClient.getClient().delete(ZookeeperClient.taskQueueParentNodeUrl + "/" + eachEnvNode, -1);
                    SnoopyLogger.info("begin to delete unused node:" + eachEnvNode);
                }
            }
        }catch (Exception e) {
            SnoopyLogger.error(this.getClass(), "NodeCleanTask", "NodeCleanTask run error.", e);
        }
    }

    private void recursiveDeleteChildNode(String parentNode, List<String> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (String eachNode:list) {
            String wholeNode = parentNode + "/" +eachNode;
            List<String> children = ZookeeperClient.getClient().getChildren(wholeNode, null);
            if (CollectionUtils.isEmpty(children)) {
                recursiveDeleteChildNode(wholeNode, children);
            }else {
                SnoopyLogger.info("begin to delete unused node: " + eachNode);
                ZookeeperClient.getClient().delete(wholeNode, -1);
            }
        }
    }
}
