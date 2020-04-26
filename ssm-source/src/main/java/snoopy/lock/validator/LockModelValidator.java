package snoopy.lock.validator;

import org.apache.commons.lang.StringUtils;
import snoopy.exception.SnoopyError;
import snoopy.exception.SnoopyException;
import snoopy.lock.model.AsyncLockModel;
import snoopy.lock.model.LimitTimeLockModel;

public class LockModelValidator {

    public static void validateAsyncLock(AsyncLockModel asyncLockModel) throws SnoopyException {
        if (asyncLockModel == null) {
            throw new SnoopyException(SnoopyError.INIT_FACTORY_MODEL_ERR);
        }
        if (StringUtils.isEmpty(asyncLockModel.getLockName())) {
            throw new SnoopyException(SnoopyError.LOCK_PARAM_NAME_ERROR);
        }
        if (asyncLockModel.getLockName().length() > 32) {
            throw  new SnoopyException(SnoopyError.LOCK_PARAM_NAME_TOLONG_ERROR);
        }
        if (asyncLockModel.getKeyCount() <= 0){
            throw new SnoopyException(SnoopyError.LOCK_PARAM_KEYCOUNT_ERROR);
        }
    }

    public static void validateLimitTimeLock(LimitTimeLockModel limitTimeLockModel) throws SnoopyException {
        if (limitTimeLockModel == null) {
            throw new SnoopyException(SnoopyError.INIT_FACTORY_MODEL_ERR);
        }
        if (StringUtils.isEmpty(limitTimeLockModel.getLockName())) {
            throw new SnoopyException(SnoopyError.LOCK_PARAM_NAME_ERROR);
        }
        if (limitTimeLockModel.getLockName().length() > 32) {
            throw new SnoopyException(SnoopyError.LOCK_PARAM_NAME_TOLONG_ERROR);
        }
        if (limitTimeLockModel.getLockSeconds() <= 0) {
            throw new SnoopyException(SnoopyError.LOCK_PARAM_TIME_ERROR);
        }
    }

}
