package snoopy.util;

import org.apache.commons.lang.StringUtils;
import snoopy.lock.model.RouteNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.CRC32;

public class RouteNodeUtil {

    public static List<RouteNode> routeNodes = new ArrayList<>();

    public static void addRouteNode(String nodeName){
        if (StringUtils.isEmpty(nodeName)){
            return;
        }

        long nodeHash = getHash(nodeName);
        RouteNode node = new RouteNode(nodeName, nodeHash);
        routeNodes.add(node);

        Collections.sort(routeNodes, new Comparator<RouteNode>() {
            @Override
            public int compare(RouteNode node1, RouteNode node2) {
                if (node1.getNodeHash() < node2.getNodeHash()){
                    return -1;
                }
                return 1;
            }
        });
    }

    public static long getHash(String nodeName){
        CRC32 crc32 = new CRC32();
        crc32.update(nodeName.getBytes());
        return crc32.getValue();
    }

    public static void deleteRouteNode(String nodeName){
        if (StringUtils.isEmpty(nodeName)) {
            return;
        }

        int serverNum = routeNodes.size();
        for (int i = 0; i < serverNum ; i++) {
            RouteNode node = routeNodes.get(i);
            if (node.getNodeName().equals(nodeName)) {
                routeNodes.remove(node);
                return;
            }
        }
    }

    public static RouteNode getRouteNode(String key){
        long hash = getHash(key);
        for (RouteNode node : routeNodes) {
            if (node.getNodeHash() > hash){
                return node;
            }
        }
        return routeNodes.get(0);
    }

    public static int getNodeSize(){
        return routeNodes.size();
    }


}

