package zkqueues.task.queue;

import org.apache.zookeeper.Watcher;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.base.config.BaseConfig;
import zkqueues.log.SnoopyLogger;

import java.util.Timer;
import java.util.TimerTask;

public class WatcherTimer implements Runnable{

    private Watcher watcher;
    private Timer timer = new Timer();
    private int count = 0;
    private int totalCount = 0;
    private String taskQueueNodeUrl;

    public WatcherTimer(Watcher watcher, String url) {
        this.watcher = watcher;
        this.taskQueueNodeUrl = url;
    }

    public void run(){
        scheduleTask();
    }

    private void scheduleTask(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                try {
                    ZookeeperClient.getClient().getChildren(taskQueueNodeUrl, watcher);
                }catch (Exception e){
                    SnoopyLogger.error(this.getClass(), "scheduleTask", "scheduleTask error, class:" + watcher.getClass().getSimpleName(),e);
                }
                if (count == totalCount) {
                    timer.cancel();
                }
            }
        },0, BaseConfig.TIME_MS);
    }
}
