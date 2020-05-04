package zkqueues.lock.factory;

import zkqueues.base.loader.SnoopyLoader;
import zkqueues.exception.SnoopyException;
import zkqueues.lock.service.AsyncLockService;
import zkqueues.lock.service.LimitTimeLockService;
import zkqueues.lock.service.impl.AsyncLockServiceImpl;
import zkqueues.lock.service.impl.LimitTimeLockServiceImpl;

public class LockFactory {

    private static AsyncLockService asyncLockService;
    private static LimitTimeLockService limitTimeLockService;

    static {
        SnoopyLoader.initSnoopy();
    }

    public AsyncLockService createAsyncLockService() throws SnoopyException {
        if (asyncLockService == null) {
            asyncLockService = new AsyncLockServiceImpl();
        }
        return asyncLockService;
    }

    public LimitTimeLockService createLimitTimeLockService() throws SnoopyException{
        if (limitTimeLockService == null) {
            limitTimeLockService = new LimitTimeLockServiceImpl();
        }
        return limitTimeLockService;
    }

}
