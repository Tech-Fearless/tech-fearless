package zkqueues.task.queue;

import zkqueues.log.SnoopyLogger;
import zkqueues.task.model.TaskModel;
import zkqueues.task.processor.AsyncTaskProcessor;
import zkqueues.util.NodeCacheUtil;
import zkqueues.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeCallBackTask implements Runnable{

    private AsyncTaskProcessor invokeInstance;
    private TaskModel taskModel;

    public InvokeCallBackTask(AsyncTaskProcessor invokeInstance, TaskModel taskModel) {
        this.invokeInstance = invokeInstance;
        this.taskModel = taskModel;
    }

    @Override
    public void run() {
        invokeCallBack();
    }

    private void invokeCallBack(){
        Method invokeMethod;
        try {
            invokeMethod = NodeCacheUtil.getInvokeMethodMap().get(taskModel.getClassName());
            ReflectUtil.invokeMethod(invokeMethod, invokeInstance, taskModel);
        }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            SnoopyLogger.error(this.getClass(), "invokeCallBack",
                    "[Task][" + taskModel.getQueueKey() + "].[className:]" + taskModel.getClassName() + " invokeMethod error." , e);
        }catch (Exception e){
            SnoopyLogger.error(this.getClass(), "invokeCallBack",
                    "[Task][" + taskModel.getQueueKey() + "].[className:]" + taskModel.getClassName() + " invokeMethod error." , e);
        }
    }
}
