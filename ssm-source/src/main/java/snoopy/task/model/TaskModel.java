package snoopy.task.model;

import java.io.Serializable;
import java.util.Map;

public class TaskModel implements Serializable{

    private static final long serialVersionUID = -5380137860996698510L;

    private String queueKey;

    private String className;

    private Map<String, Object> params;

    public String getQueueKey() {
        return queueKey;
    }

    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString(){
        return "TaskModel [queueKey=" + queueKey + ", className=" + className + ", params=" + params + "]";
    }
}
