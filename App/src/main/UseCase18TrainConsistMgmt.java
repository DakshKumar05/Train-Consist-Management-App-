package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC18: Multithreading (Bogie Processing)
 * 
 * Scenario:
 * A modern rail yard uses automated systems to inspect multiple 
 * bogies in parallel to save time. 
 * This use case demonstrates multithreading using the Thread class.
 */
public class UseCase18TrainConsistMgmt {

    public static class MaintenanceTask implements Runnable {
        private String bogieId;
        private boolean isDone = false;

        public MaintenanceTask(String bogieId) {
            this.bogieId = bogieId;
        }

        @Override
        public void run() {
            try {
                System.out.println("[Crew] Started maintenance on: " + bogieId);
                // Simulate work taking time
                Thread.sleep(500); 
                isDone = true;
                System.out.println("[Crew] Finished maintenance on: " + bogieId);
            } catch (InterruptedException e) {
                System.err.println("Maintenance interrupted for: " + bogieId);
            }
        }

        public boolean isDone() {
            return isDone;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] bogieIds = {"B101", "B102", "B103", "B104"};
        List<Thread> threads = new ArrayList<>();
        List<MaintenanceTask> tasks = new ArrayList<>();

        System.out.println("--- Starting Parallel Yard Maintenance ---");

        for (String id : bogieIds) {
            MaintenanceTask task = new MaintenanceTask(id);
            tasks.add(task);
            Thread t = new Thread(task);
            threads.add(t);
            t.start(); // Start parallel processing
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("--- All Yard Tasks Completed ---");
        
        boolean allDone = tasks.stream().allMatch(MaintenanceTask::isDone);
        System.out.println("Final Check: All Maintenance Done? " + allDone);
    }
}
