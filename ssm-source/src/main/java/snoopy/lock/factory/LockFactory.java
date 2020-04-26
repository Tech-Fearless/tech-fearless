package snoopy.lock.factory;

import snoopy.base.loader.SnoopyLoader;
import snoopy.exception.SnoopyException;
import snoopy.lock.service.AsyncLockService;
import snoopy.lock.service.LimitTimeLockService;
import snoopy.lock.service.impl.AsyncLockServiceImpl;
import snoopy.lock.service.impl.LimitTimeLockServiceImpl;

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
