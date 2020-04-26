package snoopy.lock.model;

public class AsyncLockModel extends BaseLockModel{

    private Integer keyCount;

    public Integer getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(Integer keyCount) {
        this.keyCount = keyCount;
    }

    public AsyncLockModel withName(String name) {
        setLockName(name);
        return this;
    }

    public AsyncLockModel withCount(Integer count){
        setKeyCount(count);
        return this;
    }

    public AsyncLockModel build(){
        return this;
    }

}
