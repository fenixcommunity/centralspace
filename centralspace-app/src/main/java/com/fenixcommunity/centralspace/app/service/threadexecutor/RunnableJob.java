package com.fenixcommunity.centralspace.app.service.threadexecutor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RunnableJob implements Runnable {
    private final Runnable jobCommand;
    @Getter
    private final JobPriority jobPriority;

    @Override
    public void run() {
        jobCommand.run();
    }
}
