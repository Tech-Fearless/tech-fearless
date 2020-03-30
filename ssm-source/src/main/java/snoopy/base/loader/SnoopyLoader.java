package snoopy.base.loader;

import snoopy.base.client.ZookeeperClient;

public class SnoopyLoader {

    private static boolean initSnoopy = false;

    public static void initSnoopy(){
        initSnoopy = true;

        ZookeeperClient.initSnoopyClient();
    }

    public static boolean isInitSnoopy() {
        return initSnoopy;
    }
}
