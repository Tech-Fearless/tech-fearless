package snoopy.task.factory;

import snoopy.base.loader.SnoopyLoader;
import snoopy.exception.SnoopyException;
import snoopy.task.service.AsyncTaskService;
import snoopy.task.service.impl.AsyncTaskServiceImpl;

public class AsyncTaskFactory {

    private static AsyncTaskService asyncTaskService;

    static {
        SnoopyLoader.initSnoopy();
    }

    public AsyncTaskService createAsyncTaskService() throws SnoopyException {
        if (asyncTaskService == null) {
            asyncTaskService = new AsyncTaskServiceImpl();
        }
        return asyncTaskService;
    }

}
