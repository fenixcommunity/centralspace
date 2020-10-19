package com.fenixcommunity.centralspace.metrics.benchmark.experimental;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
public class ExperimentalBenchmark {

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ExperimentalBenchmark.class.getSimpleName())
                .forks(1).build();
        new Runner(options).run();
    }

    @State(Scope.Thread)
    public static class MyState {
        private final Set<Item> itemSet = new HashSet<>();
        private final List<Item> itemList = new ArrayList<>();

        private long iterations = 1000;

        private final Item item = new Item(100L, "Max");

        @Setup(Level.Trial)
        public void setUp() {

            for (long i = 0; i < iterations; i++) {
                itemSet.add(new Item(i, "Ryszard"));
                itemList.add(new Item(i, "John"));
            }

            itemList.add(item);
            itemSet.add(item);
        }
    }

    @Benchmark
    @SuppressWarnings("unused")
    public boolean testArrayList(MyState state) {
        return state.itemList.contains(state.item);
    }

    @Benchmark
    @SuppressWarnings("unused")
    public boolean testHashSet(MyState state) {
        return state.itemSet.contains(state.item);
    }
}
