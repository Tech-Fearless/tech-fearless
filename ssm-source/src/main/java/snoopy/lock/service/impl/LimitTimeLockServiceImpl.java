package snoopy.lock.service.impl;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.springframework.util.StringUtils;
import snoopy.base.client.ZookeeperClient;
import snoopy.exception.SnoopyError;
import snoopy.exception.SnoopyException;
import snoopy.exception.ZKExceptionDispose;
import snoopy.lock.model.LimitTimeLockModel;
import snoopy.lock.model.LockNode;
import snoopy.lock.model.LockResult;
import snoopy.lock.model.RouteNode;
import snoopy.lock.service.LimitTimeLockService;
import snoopy.lock.validator.LockModelValidator;
import snoopy.util.RouteNodeUtil;

import java.util.List;

public class LimitTimeLockServiceImpl implements LimitTimeLockService {
    @Override
    public LockResult getLock(LimitTimeLockModel limitTimeLockModel) throws SnoopyException {
        LockModelValidator.validateLimitTimeLock(limitTimeLockModel);
        LockResult result = new LockResult();
        result.setAccessTime(System.currentTimeMillis());
        result.setLocked(false);

        doLockBusiness(limitTimeLockModel, result);

        result.setReturnTime(System.currentTimeMillis());
        return result;
    }

    @Override
    public void unlock(LockResult lockResult) throws SnoopyException {

    }

    private void doLockBusiness(LimitTimeLockModel limitTimeLockModel, LockResult result) throws SnoopyException {
        RouteNode routeNode = RouteNodeUtil.getRouteNode(limitTimeLockModel.getLockName());
        if (routeNode == null) {
            throw new SnoopyException(SnoopyError.CLIENT_ROUTENODE_ERROR);
        }
        String createLockUrl = LockNode.getCreateLockUrl(routeNode, limitTimeLockModel.getLockName());
        String lockUrl = createEphemeralSeqNode(createLockUrl);

        result.setLockName(limitTimeLockModel.getLockName());
        result.setLockNode(LockNode.getLockNodeName(routeNode, limitTimeLockModel.getLockName(), lockUrl));

        while (true) {
            boolean isLocked = false;
            int currentNodeSequence = LockNode.getNodeSequenceNumber(lockUrl, createLockUrl);
            int ministSequence = currentNodeSequence;
            List<String> childrenList = getChildrenNodes(routeNode);

            for (String childNode : childrenList) {
                if (StringUtils.isEmpty(childNode)) {
                    continue;
                }

                String childNodeUrl = LockNode.getLockUrlBySeqNodeName(routeNode, childNode);
                int childSequence = LockNode.getNodeSequenceNumber(childNodeUrl, createLockUrl);
                if (childSequence < currentNodeSequence) {
                    ministSequence = childSequence;
                    releaseLock(limitTimeLockModel, childNodeUrl);
                }
            }

            if (ministSequence != currentNodeSequence) {
                unlock(result);
            }else {
                isLocked = true;
            }
            if (isLocked) {
                result.setLocked(isLocked);
                return;
            }
            return;
        }
    }

    private String createEphemeralSeqNode(String createLockUrl) throws SnoopyException {
        String currentTimeStr = String.valueOf(System.currentTimeMillis());
        String lockUrl = null;
        try {
            lockUrl = ZookeeperClient.getClient().create(createLockUrl
                    , currentTimeStr.getBytes()
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.EPHEMERAL_SEQUENTIAL);
        }catch (InterruptedException e) {
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e) {
            ZKExceptionDispose.disposeKeeperException(e);
        }
        return lockUrl;
    }

    private List<String> getChildrenNodes(RouteNode routeNode) throws SnoopyException {
        List<String> childrenList = null;
        try {
            childrenList = ZookeeperClient.getClient().getChildren(LockNode.getRouteNodeUrl(routeNode), true);
        }catch (InterruptedException e) {
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e) {
            ZKExceptionDispose.disposeKeeperException(e);
        }
        return childrenList;
    }

    private void releaseLock(LimitTimeLockModel limitTimeLockModel, String childNodeUrl) throws SnoopyException {
        Long nodeCreateTime = getNodeCreateTime(childNodeUrl);
        if ((System.currentTimeMillis() - nodeCreateTime) >= limitTimeLockModel.getLockSeconds() * 1000) {
            try {
                ZookeeperClient.getClient().delete(childNodeUrl, -1);
            }catch (InterruptedException e) {
                ZKExceptionDispose.disposeInterruptedException(e);
            }catch (KeeperException e){
                ZKExceptionDispose.disposeKeeperException(e);
            }
        }
    }

    private long getNodeCreateTime(String childNodeUrl) throws SnoopyException {
        byte[] data = null;
        try {
            data = ZookeeperClient.getClient().getData(childNodeUrl, false, null);
        }catch (InterruptedException e){
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e){
            ZKExceptionDispose.disposeKeeperException(e);
        }
        String dataStr = new String(data);
        Long time = Long.valueOf(dataStr);
        return time;

    }
}
