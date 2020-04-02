package com.fenixcommunity.centralspace.benchmark.runner;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ExecutionPlan {
    Hasher hasher;
    final String password = "4v3rys3kur3p455w0rd";

    @Param({"100", "1000"})
    public int iterations;

    @Setup(Level.Invocation)
    public void setUp() {
        hasher = Hashing.murmur3_128().newHasher();
    }
}