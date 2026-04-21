package main;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UC20: Advanced Streams & Analytics
 * 
 * Scenario:
 * Railway headquarters needs a complete analytical overview of the 
 * regional train fleet. This use case uses advanced stream functions like 
 * groupingBy, partitioningBy, and IntSummaryStatistics.
 */
public class UseCase20TrainConsistMgmt {

    public static class Bogie {
        private String type;
        private int capacity;
        private boolean isUnderMaintenance;

        public Bogie(String type, int capacity, boolean isUnderMaintenance) {
            this.type = type;
            this.capacity = capacity;
            this.isUnderMaintenance = isUnderMaintenance;
        }

        public String getType() { return type; }
        public int getCapacity() { return capacity; }
        public boolean isUnderMaintenance() { return isUnderMaintenance; }
    }

    public static void main(String[] args) {
        List<Bogie> fleet = Arrays.asList(
            new Bogie("Sleeper", 72, false),
            new Bogie("Sleeper", 72, true),
            new Bogie("AC Chair", 60, false),
            new Bogie("AC Chair", 60, false),
            new Bogie("First Class", 24, false),
            new Bogie("Goods", 0, true)
        );

        System.out.println("=== Railway Fleet Analytics ===\n");

        // 1. Grouping by Type
        Map<String, Long> countByType = fleet.stream()
                .collect(Collectors.groupingBy(Bogie::getType, Collectors.counting()));
        System.out.println("Bogie Counts by Type: " + countByType);

        // 2. Partitioning by Maintenance Status
        Map<Boolean, List<Bogie>> partitioned = fleet.stream()
                .collect(Collectors.partitioningBy(Bogie::isUnderMaintenance));
        System.out.println("Active Bogies: " + partitioned.get(false).size());
        System.out.println("Under Maintenance: " + partitioned.get(true).size());

        // 3. Summary Statistics for Capacity
        IntSummaryStatistics stats = fleet.stream()
                .mapToInt(Bogie::getCapacity)
                .summaryStatistics();

        System.out.println("\nCapacity Statistics:");
        System.out.println(" - Total Seats: " + stats.getSum());
        System.out.println(" - Max Capacity: " + stats.getMax());
        System.out.println(" - Min Capacity: " + stats.getMin());
        System.out.println(" - Average Capacity: " + String.format("%.1f", stats.getAverage()));
    }
}
