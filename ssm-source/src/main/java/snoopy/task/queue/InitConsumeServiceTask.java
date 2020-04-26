package snoopy.task.queue;

import snoopy.log.SnoopyLogger;

public class InitConsumeServiceTask implements Runnable{

    private String taskQueueClassName;
    private String taskQueueNodeUrl;
    private TaskQueue taskQueue;

    public InitConsumeServiceTask(String taskQueueClassName, String taskQueueNodeUrl) {
        this.taskQueueClassName = taskQueueClassName;
        this.taskQueueNodeUrl = taskQueueNodeUrl;
        taskQueue = new TaskQueue();
    }


    @Override
    public void run() {
        initConsumeService();
    }

    private void initConsumeService(){
        SnoopyLogger.info("Snoopy task init service [" + taskQueueClassName + "].");
        taskQueue.initConsumerService(taskQueueClassName, taskQueueNodeUrl);
    }
}
