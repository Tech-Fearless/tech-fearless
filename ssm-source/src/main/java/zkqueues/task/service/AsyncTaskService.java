package zkqueues.task.service;

import zkqueues.task.model.TaskModel;

public interface AsyncTaskService {

    void addAsyncTask(TaskModel taskModel) throws  Exception;

}
