package snoopy.lock.model;

public class LimitTimeLockModel extends BaseLockModel{

    private Integer lockSeconds;

    public Integer getLockSeconds() {
        return lockSeconds;
    }

    public void setLockSeconds(Integer lockSeconds) {
        this.lockSeconds = lockSeconds;
    }
}
