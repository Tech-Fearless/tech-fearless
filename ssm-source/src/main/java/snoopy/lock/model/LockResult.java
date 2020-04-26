package snoopy.lock.model;

public class LockResult {

    private Boolean isLocked;

    private String lockName;

    private String lockNode;

    private Long accessTime;

    private Long returnTime;

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockNode() {
        return lockNode;
    }

    public void setLockNode(String lockNode) {
        this.lockNode = lockNode;
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }
}
