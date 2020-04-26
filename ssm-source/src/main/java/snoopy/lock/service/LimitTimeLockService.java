package snoopy.lock.service;

import snoopy.exception.SnoopyException;
import snoopy.lock.model.LimitTimeLockModel;
import snoopy.lock.model.LockResult;

public interface LimitTimeLockService {

    LockResult getLock(LimitTimeLockModel limitTimeLockModel) throws SnoopyException;

    void unlock(LockResult lockResult) throws SnoopyException;

}
