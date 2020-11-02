package com.fenixcommunity.centralspace.app.service.thread;


import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ScheduledService {

    public void delayInSeconds(final Runnable runnable, final long delay) {
        Executors.newSingleThreadScheduledExecutor().schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public void repeatInSeconds(final Runnable runnable, final long delay) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, delay, TimeUnit.SECONDS);
    }
}