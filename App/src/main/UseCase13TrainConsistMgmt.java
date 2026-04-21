package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC13: Performance Comparison (Loop vs Stream)
 * 
 * Scenario:
 * A regional railway network processes thousands of bogie records daily.
 * This use case compares the performance of traditional loops, 
 * sequential streams, and parallel streams when aggregating data 
 * from a large collection (1,000,000 records).
 */
public class UseCase13TrainConsistMgmt {

    public static class Bogie {
        private int capacity;

        public Bogie(int capacity) {
            this.capacity = capacity;
        }

        public int getCapacity() { return capacity; }
    }

    /**
     * Sums capacities using a traditional for-each loop.
     */
    public long sumWithLoop(List<Bogie> bogies) {
        long sum = 0;
        for (Bogie b : bogies) {
            sum += b.getCapacity();
        }
        return sum;
    }

    /**
     * Sums capacities using a sequential stream.
     */
    public long sumWithStream(List<Bogie> bogies) {
        return bogies.stream()
                .mapToLong(Bogie::getCapacity)
                .sum();
    }

    /**
     * Sums capacities using a parallel stream.
     */
    public long sumWithParallelStream(List<Bogie> bogies) {
        return bogies.parallelStream()
                .mapToLong(Bogie::getCapacity)
                .sum();
    }

    public static void main(String[] args) {
        final int COUNT = 1_000_000;
        List<Bogie> largeConsist = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            largeConsist.add(new Bogie(72)); // Every bogie has 72 seats
        }

        UseCase13TrainConsistMgmt benchmark = new UseCase13TrainConsistMgmt();

        System.out.println("Processing " + COUNT + " bogie records...\n");

        // 1. Benchmark Loop
        long start = System.currentTimeMillis();
        long sumLoop = benchmark.sumWithLoop(largeConsist);
        long end = System.currentTimeMillis();
        System.out.println("Loop Sum: " + sumLoop + " | Time: " + (end - start) + "ms");

        // 2. Benchmark Sequential Stream
        start = System.currentTimeMillis();
        long sumStream = benchmark.sumWithStream(largeConsist);
        end = System.currentTimeMillis();
        System.out.println("Stream Sum: " + sumStream + " | Time: " + (end - start) + "ms");

        // 3. Benchmark Parallel Stream
        start = System.currentTimeMillis();
        long sumParallel = benchmark.sumWithParallelStream(largeConsist);
        end = System.currentTimeMillis();
        System.out.println("Parallel Stream Sum: " + sumParallel + " | Time: " + (end - start) + "ms");
    }
}
