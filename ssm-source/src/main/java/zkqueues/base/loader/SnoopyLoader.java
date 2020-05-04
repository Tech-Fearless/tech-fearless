package zkqueues.base.loader;

import zkqueues.base.client.ZookeeperClient;

public class SnoopyLoader {

    private static boolean initSnoopy = false;

    public static void initSnoopy(){

        if (initSnoopy){
            return;
        }

        initSnoopy = true;

        ZookeeperClient.initSnoopyClient();
    }

    public static boolean isInitSnoopy() {
        return initSnoopy;
    }
}
