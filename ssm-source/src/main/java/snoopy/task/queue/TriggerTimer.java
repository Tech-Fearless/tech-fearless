package snoopy.task.queue;

import org.apache.zookeeper.KeeperException;
import snoopy.base.client.ZookeeperClient;
import snoopy.base.config.BaseConfig;
import snoopy.log.SnoopyLogger;
import snoopy.task.factory.AsyncTaskFactory;
import snoopy.task.model.TaskModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TriggerTimer implements Runnable{

    private AsyncTaskFactory taskFactory = new AsyncTaskFactory();
    private Timer triggerTimer = new Timer();
    private List<String> processors = null;

    public TriggerTimer(List<String> processors) {
        this.processors = processors;
    }

    @Override
    public void run() {
        scheduleTrigger();
    }

    private void scheduleTrigger(){
        triggerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkConfig();
                triggerWatcherEvent();
            }
        },0,BaseConfig.TIME_MS);
    }

    private void checkConfig(){
        try{
            long cacheTime = (long) ZookeeperClient.cacheConfig.get("cacheTime");
            long currentTime = System.currentTimeMillis();
            long time = currentTime - cacheTime;
            if (time < BaseConfig.DEFAULT_ZKADDR_TIMEOUT) {
                return;
            }
            String currentZKAddresses = BaseConfig.getZKAddresses();
            String cacheZKAddresses = (String) ZookeeperClient.cacheConfig.get(BaseConfig.ZK_ADDRESS_KEY);
            ZookeeperClient.cacheConfig.put("cacheTime", System.currentTimeMillis());
            if (!cacheZKAddresses.equals(currentZKAddresses)) {
                SnoopyLogger.info("TriggerTimer.zkAddresses has been changed. begin to start...");
                ZookeeperClient.cacheConfig.put(BaseConfig.ZK_ADDRESS_KEY, currentZKAddresses);
                ZookeeperClient.close();
                ZookeeperClient.initSnoopyClient();
            }
        }catch (Exception e){
            SnoopyLogger.error(this.getClass(), "cacheConfig", "checkConfig error", e);
        }
    }

    private void triggerWatcherEvent(){
        try {

            for (String eachProccessor : processors) {
                TaskModel eventModel = new TaskModel();
                eventModel.setClassName(eachProccessor);
                eventModel.setQueueKey("eventNode");
                taskFactory.createAsyncTaskService().addAsyncTask(eventModel);
            }
        }catch (Exception e){
            if (e instanceof KeeperException.ConnectionLossException ||
            e instanceof KeeperException.SessionExpiredException) {
                ZookeeperClient.close();
                ZookeeperClient.initSnoopyClient();
            }
            SnoopyLogger.error(this.getClass(), "triggerWatcherEvent", "triggerWatcherEvent errro", e);
        }
    }
}
