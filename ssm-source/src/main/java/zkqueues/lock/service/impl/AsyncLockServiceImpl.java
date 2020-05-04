package zkqueues.lock.service.impl;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.base.config.BaseConfig;
import zkqueues.exception.SnoopyError;
import zkqueues.exception.SnoopyException;
import zkqueues.exception.ZKExceptionDispose;
import zkqueues.lock.model.AsyncLockModel;
import zkqueues.lock.model.LockNode;
import zkqueues.lock.model.LockResult;
import zkqueues.lock.model.RouteNode;
import zkqueues.lock.service.AsyncLockService;
import zkqueues.lock.validator.LockModelValidator;
import zkqueues.log.SnoopyLogger;
import zkqueues.util.RouteNodeUtil;

import java.util.List;

public class AsyncLockServiceImpl implements AsyncLockService {
    @Override
    public LockResult getLock(AsyncLockModel asyncLockModel) throws SnoopyException {
        LockResult lockResult = new LockResult();
        try {
            LockModelValidator.validateAsyncLock(asyncLockModel);
            lockResult.setAccessTime(System.currentTimeMillis());
            lockResult.setLocked(false);
            doLockBusiness(asyncLockModel, lockResult);

            lockResult.setReturnTime(System.currentTimeMillis());

        }catch (SnoopyException e){
            if (e.getSnoopyError() != SnoopyError.CLIENT_CONNECT_LOSS_ERROR
            && e.getSnoopyError() != SnoopyError.CLIENT_SESSION_EXPIRED_ERROR) {
                throw  e;
            }else {
                dealConnectionLossException(asyncLockModel, lockResult);
                if (!lockResult.getLocked()) {
                    SnoopyLogger.error(this.getClass(),"dealConnectionLossException", "dealConnectionLossException error", e);
                    throw e;
                }
            }
        }

        return lockResult;
    }

    @Override
    public void unlock(LockResult lockResult) throws SnoopyException {
        if (lockResult == null
                || StringUtils.isEmpty(lockResult.getLockName())
                || StringUtils.isEmpty(lockResult.getLockNode())) {
            throw new SnoopyException(SnoopyError.LOCK_PARAM_NAME_ERROR);
        }
        RouteNode routeNode = RouteNodeUtil.getRouteNode(lockResult.getLockName());
        String lockUrl = LockNode.getLockUrlBySeqNodeName(routeNode, lockResult.getLockNode());
        try {
            ZookeeperClient.getClient().delete(lockUrl, -1);
        }catch (InterruptedException e){
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e) {
            ZKExceptionDispose.disposeKeeperException(e);
        }


    }

    private void doLockBusiness(AsyncLockModel asyncLockModel, LockResult lockResult) throws SnoopyException{

        RouteNode routeNode = RouteNodeUtil.getRouteNode(asyncLockModel.getLockName());
        if (null == routeNode) {
            throw new SnoopyException(SnoopyError.CLIENT_ROUTENODE_ERROR);
        }

        String createLockUrl = LockNode.getCreateLockUrl(routeNode, asyncLockModel.getLockName());
        String lockUrl = getLockUrl(asyncLockModel, routeNode, createLockUrl);

        lockResult.setLockName(asyncLockModel.getLockName());
        lockResult.setLockNode(LockNode.getLockNodeName(routeNode,asyncLockModel.getLockName(),lockUrl));

        while (true) {
            List<String> childrenList = getChildrenNodes(routeNode);
            int position = 0;
            if (!CollectionUtils.isEmpty(childrenList)){
                int nodeSequence = LockNode.getNodeSequenceNumber(lockUrl, createLockUrl);
                for (String childNode : childrenList) {
                    if (StringUtils.isEmpty(childNode) || !childNode.contains(asyncLockModel.getLockName())) {
                        continue;
                    }
                    String childNodeUrl = LockNode.getLockUrlBySeqNodeName(routeNode, childNode);
                    int childSequence = LockNode.getNodeSequenceNumber(childNodeUrl, createLockUrl);
                    if (childSequence < nodeSequence) {
                        position++;
                    }
                }
                position++;
            }else {
                position =Integer.MAX_VALUE;
            }

            if (position <= asyncLockModel.getKeyCount()) {
                lockResult.setLocked(true);
                return;
            }

            unlock(lockResult);
            return;
        }

    }

    private List<String> getChildrenNodes(RouteNode routeNode) throws SnoopyException{
        List<String> childrenList = null;
        try {
            childrenList = ZookeeperClient.getClient().getChildren(LockNode.getRouteNodeUrl(routeNode), true);
        }catch (InterruptedException e){
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e){
            ZKExceptionDispose.disposeKeeperException(e);
        }
        return childrenList;
    }

    private String getLockUrl(AsyncLockModel asyncLockModel,
                              RouteNode routeNode,
                              String createLockUrl) throws SnoopyException {
        String lockUrl = null;
        try {
            lockUrl = ZookeeperClient.getClient().create(createLockUrl, String.valueOf(System.currentTimeMillis()).getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        }catch (InterruptedException e) {
            ZKExceptionDispose.disposeInterruptedException(e);
        }catch (KeeperException e){
            ZKExceptionDispose.disposeKeeperException(e);
        }
        return lockUrl;
    }


    private void dealConnectionLossException(AsyncLockModel lockModel, LockResult lockResult) throws SnoopyException {
        for (int i = 0; i < BaseConfig.RETRY_COUNT; i++) {
            try {
                ZookeeperClient.close();
                ZookeeperClient.initSnoopyClient();
                doLockBusiness(lockModel, lockResult);
                if (lockResult.getLocked()) {
                    lockResult.setReturnTime(System.currentTimeMillis());
                    break;
                }

            }catch (SnoopyException e){
                if (e.getSnoopyError() != SnoopyError.CLIENT_CONNECT_LOSS_ERROR
                && e.getSnoopyError() != SnoopyError.CLIENT_SESSION_EXPIRED_ERROR) {
                    throw e;
                }
            }
        }
    }
}
