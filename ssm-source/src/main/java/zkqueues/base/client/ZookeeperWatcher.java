package zkqueues.base.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import zkqueues.log.SnoopyLogger;

import java.util.concurrent.CountDownLatch;

public class ZookeeperWatcher implements Watcher {

    private CountDownLatch countDownLatch = null;

    ZookeeperWatcher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (countDownLatch != null) {
                countDownLatch.countDown();
                countDownLatch = null;
                SnoopyLogger.info("ZookeeperWatcher KeeperState SyncConnected WatchdEvent:" + watchedEvent.toString());
            }
        } else if (watchedEvent.getState() == Event.KeeperState.Expired) {
            SnoopyLogger.error(ZookeeperWatcher.class,
                    "process",
                    "Session expired.Now rebuilding. WhatchedEvent:" + watchedEvent.toString(),
                    new Exception());
            ZookeeperClient.close();
            ZookeeperClient.initSnoopyClient();
        }
    }


}
