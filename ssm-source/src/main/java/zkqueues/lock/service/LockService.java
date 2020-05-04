package zkqueues.lock.service;

import zkqueues.exception.SnoopyException;
import zkqueues.lock.model.LockResult;
import zkqueues.lock.template.BaseLockTemplate;

public interface LockService {

    void setTemplate(int lockCode, BaseLockTemplate template) throws SnoopyException;

    LockResult tryLock() throws SnoopyException;

    void unLock(LockResult lockResult) throws SnoopyException;

}
