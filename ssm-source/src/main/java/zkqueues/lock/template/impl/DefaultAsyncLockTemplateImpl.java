package zkqueues.lock.template.impl;

import zkqueues.lock.template.AsyncLockTemplate;

public class DefaultAsyncLockTemplateImpl implements AsyncLockTemplate {
    @Override
    public int setKeyCount() {
        return 1;
    }

    @Override
    public String setLockName() {
        return "DefaultAsyncLock";
    }
}
