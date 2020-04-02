package com.fenixcommunity.centralspace.benchmark.runner;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkWithExecutionPlan {
//info https://javaleader.pl/2019/12/12/java-microbenchmark-harness-jmh/

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkWithExecutionPlan.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    @Fork(value = 2, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void executionPlanTest(ExecutionPlan executionPlan) {
        for (int i = executionPlan.iterations; i > 0; i--) {
            executionPlan.hasher.putString(executionPlan.password, Charset.defaultCharset());
        }
        executionPlan.hasher.hash();
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Measurement(iterations = 5, time = 2)
    public void measurementTest() {
        long count = 1;
        count++;
        boolean statement = count == 545454;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    //value -> 1 fork, 0 no fork / warmups -> 2 times ignore result and then get result
    @Fork(value = 0, warmups = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
    public void forkTest() {
        long count = 1;
        count++;
        boolean statement = count == 545454;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 500, timeUnit = TimeUnit.MICROSECONDS)
    public void warmupTest() {
        long count = 1;
        if (count > 5) {
            count = count + 2;
        } else {
            count++;
        }
    }
}
