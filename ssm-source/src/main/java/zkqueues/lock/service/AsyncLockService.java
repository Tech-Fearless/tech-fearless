package zkqueues.lock.service;

import zkqueues.exception.SnoopyException;
import zkqueues.lock.model.AsyncLockModel;
import zkqueues.lock.model.LockResult;

public interface AsyncLockService {

    LockResult getLock(AsyncLockModel asyncLockModel) throws SnoopyException;

    void unlock(LockResult lockResult) throws SnoopyException;

}
