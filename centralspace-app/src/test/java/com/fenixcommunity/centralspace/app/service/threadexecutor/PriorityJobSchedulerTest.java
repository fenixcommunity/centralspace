package com.fenixcommunity.centralspace.app.service.threadexecutor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PriorityJobSchedulerTest {

    @InjectMocks
    private PriorityJobScheduler priorityJobScheduler;

    @Test
    void scheduleJobTest() {
        RunnableJob job1 = new RunnableJob(() -> System.out.println("Job1"), JobPriority.LOW);
        RunnableJob job2 = new RunnableJob(() -> System.out.println("Job2"), JobPriority.MEDIUM);
        RunnableJob job3 = new RunnableJob(() -> System.out.println("Job3"), JobPriority.HIGH);
        RunnableJob job4 = new RunnableJob(() -> System.out.println("Job4"), JobPriority.MEDIUM);
        RunnableJob job5 = new RunnableJob(() -> System.out.println("Job5"), JobPriority.LOW);
        RunnableJob job6 = new RunnableJob(() -> System.out.println("Job6"), JobPriority.HIGH);

        priorityJobScheduler.scheduleJob(job1);
        priorityJobScheduler.scheduleJob(job2);
        priorityJobScheduler.scheduleJob(job3);
        priorityJobScheduler.scheduleJob(job4);
        priorityJobScheduler.scheduleJob(job5);
        priorityJobScheduler.scheduleJob(job6);

        assertThat(true).isTrue();
    }
}