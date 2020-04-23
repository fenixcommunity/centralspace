package com.fenixcommunity.centralspace.utilities.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fenixcommunity.centralspace.utilities.globalexception.FutureAsyncException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AsyncFutureHelper {

    public static <T> T get(CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
        throw new FutureAsyncException("unable to get async resource");
    }
}
