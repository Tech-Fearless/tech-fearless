package zkqueues.lock.service;

import zkqueues.exception.SnoopyException;
import zkqueues.lock.model.LimitTimeLockModel;
import zkqueues.lock.model.LockResult;

public interface LimitTimeLockService {

    LockResult getLock(LimitTimeLockModel limitTimeLockModel) throws SnoopyException;

    void unlock(LockResult lockResult) throws SnoopyException;

}
