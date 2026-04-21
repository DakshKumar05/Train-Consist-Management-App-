package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC18: Train Consist Management - Multithreading
 * Demonstrates concurrent bogie processing using Java Threads,
 * synchronized methods, and Runnable tasks for parallel maintenance checks.
 */
public class UseCase18TrainConsistMgmt {

    public static class Bogie {
        private final String id;
        private final String type;
        private volatile String status; // volatile for thread visibility

        public Bogie(String id, String type) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Bogie ID cannot be null or empty.");
            }
            this.id = id;
            this.type = type;
            this.status = "PENDING";
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        @Override
        public String toString() {
            return "Bogie{id='" + id + "', type='" + type + "', status='" + status + "'}";
        }
    }

    // Shared, thread-safe log
    private final List<String> processLog = new ArrayList<>();
    private final Object lock = new Object();

    /**
     * Thread-safe method to log a maintenance message.
     */
    public synchronized void log(String message) {
        processLog.add(message);
        System.out.println("[LOG] " + message);
    }

    public List<String> getProcessLog() {
        synchronized (lock) {
            return new ArrayList<>(processLog);
        }
    }

    /**
     * Runnable task for simulating a maintenance check on a bogie.
     */
    public class MaintenanceTask implements Runnable {
        private final Bogie bogie;

        public MaintenanceTask(Bogie bogie) {
            this.bogie = bogie;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            log(threadName + " started check on " + bogie.getId());
            bogie.setStatus("IN_PROGRESS");

            try {
                // Simulate time-consuming maintenance check
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                bogie.setStatus("INTERRUPTED");
                log(threadName + " interrupted for " + bogie.getId());
                return;
            }

            bogie.setStatus("DONE");
            log(threadName + " completed check on " + bogie.getId());
        }
    }

    /**
     * Processes all bogies in parallel using individual threads.
     * @param bogies list of bogies to process
     */
    public void processInParallel(List<Bogie> bogies) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (Bogie bogie : bogies) {
            Thread t = new Thread(new MaintenanceTask(bogie), "Worker-" + bogie.getId());
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join(); // Wait for all threads to finish
        }

        System.out.println("All maintenance checks completed.");
    }

    /**
     * Thread-safe counter demonstrating synchronized access.
     */
    public static class BogieCounter {
        private int count = 0;

        public synchronized void increment() { count++; }
        public synchronized int getCount() { return count; }
    }

    public static void main(String[] args) throws InterruptedException {
        UseCase18TrainConsistMgmt mgmt = new UseCase18TrainConsistMgmt();

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper"));
        bogies.add(new Bogie("B002", "AC Chair Car"));
        bogies.add(new Bogie("B003", "General"));
        bogies.add(new Bogie("B004", "First Class AC"));

        System.out.println("Starting parallel maintenance checks...");
        mgmt.processInParallel(bogies);

        System.out.println("\nFinal Bogie Status:");
        bogies.forEach(b -> System.out.println("  " + b));

        System.out.println("\nProcess Log (" + mgmt.getProcessLog().size() + " entries):");
        mgmt.getProcessLog().forEach(e -> System.out.println("  " + e));

        // Demonstrate synchronized counter
        BogieCounter counter = new BogieCounter();
        List<Thread> counterThreads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(counter::increment);
            counterThreads.add(t);
            t.start();
        }
        for (Thread t : counterThreads) t.join();
        System.out.println("\nSynchronized Counter Result (expected 100): " + counter.getCount());
    }
}
