package snoopy.lock.service;

import snoopy.exception.SnoopyException;
import snoopy.lock.model.AsyncLockModel;
import snoopy.lock.model.LockResult;

public interface AsyncLockService {

    LockResult getLock(AsyncLockModel asyncLockModel) throws SnoopyException;

    void unlock(LockResult lockResult) throws SnoopyException;

}
