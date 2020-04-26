package snoopy.lock.factory;

import snoopy.lock.model.AsyncLockModel;

public class AsyncLockModelBuilder {

    public static AsyncLockModel newLockModel(){
        AsyncLockModel asyncLockModel = new AsyncLockModel();
        return asyncLockModel;
    }

}
