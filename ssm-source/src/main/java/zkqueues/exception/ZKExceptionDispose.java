package zkqueues.exception;

import org.apache.zookeeper.KeeperException;
import zkqueues.base.client.ZookeeperClient;
import zkqueues.log.SnoopyLogger;

public class ZKExceptionDispose {

    public static void disposeKeeperException(KeeperException e) throws SnoopyException {
        switch (e.code()) {
            case SESSIONEXPIRED:
                printKeeperException(e);
                dealConnectionLossException();
                throw new SnoopyException(SnoopyError.CLIENT_SESSION_EXPIRED_ERROR,e);
            case CONNECTIONLOSS:
                printKeeperException(e);
                dealConnectionLossException();
                throw new SnoopyException(SnoopyError.CLIENT_CONNECT_LOSS_ERROR, e);
            case SESSIONMOVED:
                printKeeperException(e);
                throw new SnoopyException(SnoopyError.CLIENT_NOAUTH_ERROR, e);
            case NOAUTH:
                printKeeperException(e);
                throw new SnoopyException(SnoopyError.CLIENT_NOAUTH_ERROR, e);
            case OK:
                default:
                    printKeeperException(e);
                    throw new SnoopyException(SnoopyError.KEEPER_EXCEPTION_ERROR, e);
        }
    }

    public static void disposeInterruptedException(InterruptedException e) throws SnoopyException{
        printKeeperException(e);
        throw new SnoopyException(SnoopyError.INTERRUPTED_EXCEPTION_ERROR, e);
    }

    private static void printKeeperException(Exception e) {
        SnoopyLogger.error(ZKExceptionDispose.class, "disposeKeeperException","zookeeper exception", e);
    }

    private static void dealConnectionLossException(){
        ZookeeperClient.close();
        ZookeeperClient.initSnoopyClient();
    }

}
