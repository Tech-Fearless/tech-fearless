package snoopy.lock.service;

import snoopy.exception.SnoopyException;
import snoopy.lock.model.LockResult;
import snoopy.lock.template.BaseLockTemplate;

public interface LockService {

    void setTemplate(int lockCode, BaseLockTemplate template) throws SnoopyException;

    LockResult tryLock() throws SnoopyException;

    void unLock(LockResult lockResult) throws SnoopyException;

}
