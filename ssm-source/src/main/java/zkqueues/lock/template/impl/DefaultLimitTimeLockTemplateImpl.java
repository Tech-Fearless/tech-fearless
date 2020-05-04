package zkqueues.lock.template.impl;

import zkqueues.lock.template.LimitTimeLockTemplate;

public class DefaultLimitTimeLockTemplateImpl implements LimitTimeLockTemplate {
    @Override
    public int setLockSeconds() {
        return 30;
    }

    @Override
    public String setLockName() {
        return "DefaultLimitTimeLock";
    }
}
