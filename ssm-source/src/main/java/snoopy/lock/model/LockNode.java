package snoopy.lock.model;

import org.apache.commons.lang.StringUtils;
import snoopy.base.client.ZookeeperClient;

public class LockNode {

    public static String getCreateLockUrl(RouteNode routeNode, String lockName) {
        return String.format(ZookeeperClient.lockNameNodeUrlFormat, routeNode.getNodeName(), lockName);
    }

    public static String getLockUrlBySeqNodeName(RouteNode routeNode, String seqNodeName) {
        String routeNodeUrl = String.format(ZookeeperClient.routeNodeUrl, routeNode.getNodeName());
        return routeNodeUrl + ZookeeperClient.SEPARATOR + seqNodeName;
    }

    public static String getLockNodeName(RouteNode routeNode, String lockName, String lockUrl){
        return lockUrl.substring(getRouteNodeUrl(routeNode).length() + 1);
    }

    public static String getRouteNodeUrl(RouteNode routeNode) {
        return String.format(ZookeeperClient.routeNodeUrl, routeNode.getNodeName());
    }

    public static int getNodeSequenceNumber(String lockUrl, String createLockUrl) {
        if (StringUtils.isEmpty(lockUrl)) {
            return Integer.MIN_VALUE;
        }
        if (StringUtils.isEmpty(createLockUrl)){
            createLockUrl = "";
        }
        String sequence = lockUrl.substring(createLockUrl.length());
        try {
            return Integer.parseInt(sequence);
        }catch (Exception e){
            return Integer.MIN_VALUE;
        }
    }

}
