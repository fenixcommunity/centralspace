package com.fenixcommunity.centralspace.utilities.benchmark;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j2;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

@Log4j2
public class BenchmarkTester {

    @Benchmark
    @BenchmarkMode(Mode.All)
    //value -> 1 fork, 0 no fork / warmups -> 2 times ignore result and then get result
    @Fork(value = 1, warmups = 2,  jvmArgs = {"-Xms1G", "-Xmx1G"})
    public void forkTest() {
        log.info("forkTest");
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void warmupTest() {
        log.info("warmupTest");
    }

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void executionPlanTest(ExecutionPlan executionPlan) {
        for (int i = executionPlan.iterations; i > 0; i--) {
            executionPlan.hasher.putString(executionPlan.password, Charset.defaultCharset());
        }
        executionPlan.hasher.hash();
    }

        http://tutorials.jenkov.com/java-performance/jmh.html
    https://mkyong.com/java/java-jmh-benchmark-tutorial/
    https://javaleader.pl/2019/12/12/java-microbenchmark-harness-jmh/
}
