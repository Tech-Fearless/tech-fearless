package zkqueues.task.factory;

import zkqueues.base.loader.SnoopyLoader;
import zkqueues.exception.SnoopyException;
import zkqueues.task.service.AsyncTaskService;
import zkqueues.task.service.impl.AsyncTaskServiceImpl;

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
