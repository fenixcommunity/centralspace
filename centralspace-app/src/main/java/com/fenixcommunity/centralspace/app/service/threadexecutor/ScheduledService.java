package com.fenixcommunity.centralspace.app.service.threadexecutor;


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

    public void delayInSeconds(final Runnable command, final long delay) {
        Executors.newSingleThreadScheduledExecutor().schedule(command, delay, TimeUnit.SECONDS);
    }

    public void repeatInSeconds(final Runnable command, final long delay) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(command, 0, delay, TimeUnit.SECONDS);
    }
}