package snoopy.lock.model;

public class RouteNode {

    private String nodeName;
    private long nodeHash;

    public RouteNode(String nodeName, long nodeHash){
        super();
        this.nodeName = nodeName;
        this.nodeHash = nodeHash;
    }

    public String getNodeName(){
        return nodeName;
    }

    public void setNodeName(String nodeName){
        this.nodeName = nodeName;
    }

    public long getNodeHash(){
        return nodeHash;
    }

    public void setNodeHash(long nodeHash){
        this.nodeHash = nodeHash;
    }

}
