package snoopy.task.service;

import snoopy.task.model.TaskModel;

public interface AsyncTaskService {

    void addAsyncTask(TaskModel taskModel) throws  Exception;

}
