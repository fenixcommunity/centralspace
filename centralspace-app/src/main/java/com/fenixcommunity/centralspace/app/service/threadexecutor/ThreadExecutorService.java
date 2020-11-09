package com.fenixcommunity.centralspace.app.service.threadexecutor;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ThreadExecutorService {

    // please use ConcurrentHashMap or ConcurrentNavigableMap for ordering
    public void execute(final Runnable command, final int threadsNo) {
        if (threadsNo < 0 || threadsNo > 8) {
            throw new ServiceFailedException("0 < threads no < 8");
        }
        final ExecutorService executorService = Executors.newFixedThreadPool(threadsNo);
        executorService.execute(command);
        executorService.shutdown();
    }
}
