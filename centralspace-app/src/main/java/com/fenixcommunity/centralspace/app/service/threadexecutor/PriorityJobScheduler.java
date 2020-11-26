package com.fenixcommunity.centralspace.app.service.threadexecutor;

import static java.util.Comparator.comparing;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PriorityJobScheduler {
    private static final int POOL_SIZE = 1;
    private static final int QUEUE_SIZE = 10;

    private final ExecutorService priorityJobPoolExecutor;
    private final ExecutorService priorityJobScheduler = Executors.newSingleThreadExecutor();
    private final PriorityBlockingQueue<RunnableJob> priorityQueue;

//  public PriorityJobScheduler(final Integer poolSize, Integer queueSize) {
    public PriorityJobScheduler() {
        priorityJobPoolExecutor = Executors.newFixedThreadPool(POOL_SIZE);
        priorityQueue = new PriorityBlockingQueue<>(QUEUE_SIZE, comparing(RunnableJob::getJobPriority));
        priorityJobScheduler.execute(() -> {
            while (true) {
                try {
                    priorityJobPoolExecutor.execute(priorityQueue.take());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    public void scheduleJob(RunnableJob runnableJob) {
        priorityQueue.add(runnableJob);
    }

}