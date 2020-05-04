package zkqueues.task.processor;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import zkqueues.task.model.TaskModel;

import java.util.concurrent.CountDownLatch;

public abstract class AsyncTaskProcessor implements Watcher {

    public static CountDownLatch latch;

    public AsyncTaskProcessor(){
        latch = new CountDownLatch(1);
    }

    public void await() throws InterruptedException{
        latch.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        latch.countDown();
        latch = new CountDownLatch(1);
    }

    public abstract void doProcess(TaskModel model);

}
