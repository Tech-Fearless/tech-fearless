package snoopy.lock.template.impl;

import snoopy.lock.template.LimitTimeLockTemplate;

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
