package zkqueues.lock.factory;

import zkqueues.lock.model.AsyncLockModel;

public class AsyncLockModelBuilder {

    public static AsyncLockModel newLockModel(){
        AsyncLockModel asyncLockModel = new AsyncLockModel();
        return asyncLockModel;
    }

}
