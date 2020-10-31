package com.fenixcommunity.centralspace.app.service.thread;


import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

    public void delayInSeconds(final Runnable runnable, final long delay) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public void repeatInSeconds(final Runnable runnable, final long delay) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, delay, TimeUnit.SECONDS);
    }
}