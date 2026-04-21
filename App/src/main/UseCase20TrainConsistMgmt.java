package main;

import java.util.*;
import java.util.stream.*;

/**
 * UC20: Train Consist Management - Java Streams (Advanced)
 * Demonstrates advanced Stream operations: groupingBy, partitioningBy,
 * flatMap, collect, statistics, and Optional chaining on bogie data.
 */
public class UseCase20TrainConsistMgmt {

    public static class Bogie {
        private final String id;
        private final String type;
        private final String classCategory; // "PREMIUM", "STANDARD", "ECONOMY"
        private final int capacity;
        private final double fareMultiplier;
        private final String status; // "ACTIVE", "MAINTENANCE", "RETIRED"

        public Bogie(String id, String type, String classCategory,
                     int capacity, double fareMultiplier, String status) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Bogie ID cannot be null or empty.");
            }
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity cannot be negative.");
            }
            this.id = id;
            this.type = type;
            this.classCategory = classCategory;
            this.capacity = capacity;
            this.fareMultiplier = fareMultiplier;
            this.status = status;
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public String getClassCategory() { return classCategory; }
        public int getCapacity() { return capacity; }
        public double getFareMultiplier() { return fareMultiplier; }
        public String getStatus() { return status; }

        @Override
        public String toString() {
            return String.format("[%s] %s (%s) cap=%d fare=%.1fx status=%s",
                    id, type, classCategory, capacity, fareMultiplier, status);
        }
    }

    private final List<Bogie> bogies;

    public UseCase20TrainConsistMgmt(List<Bogie> bogies) {
        this.bogies = new ArrayList<>(bogies);
    }

    /**
     * Groups bogies by class category using Collectors.groupingBy().
     */
    public Map<String, List<Bogie>> groupByCategory() {
        return bogies.stream()
                .collect(Collectors.groupingBy(Bogie::getClassCategory));
    }

    /**
     * Groups bogies by status and counts them using downstream collector.
     */
    public Map<String, Long> countByStatus() {
        return bogies.stream()
                .collect(Collectors.groupingBy(Bogie::getStatus, Collectors.counting()));
    }

    /**
     * Partitions bogies into ACTIVE vs non-ACTIVE.
     */
    public Map<Boolean, List<Bogie>> partitionByActive() {
        return bogies.stream()
                .collect(Collectors.partitioningBy(b -> "ACTIVE".equals(b.getStatus())));
    }

    /**
     * Returns total capacity of ACTIVE bogies only.
     */
    public int totalActiveCapacity() {
        return bogies.stream()
                .filter(b -> "ACTIVE".equals(b.getStatus()))
                .mapToInt(Bogie::getCapacity)
                .sum();
    }

    /**
     * Returns IntSummaryStatistics for bogie capacities.
     */
    public IntSummaryStatistics capacityStatistics() {
        return bogies.stream()
                .mapToInt(Bogie::getCapacity)
                .summaryStatistics();
    }

    /**
     * Finds the bogie with the highest capacity using max().
     */
    public Optional<Bogie> highestCapacityBogie() {
        return bogies.stream()
                .max(Comparator.comparingInt(Bogie::getCapacity));
    }

    /**
     * Returns a sorted, comma-separated list of all bogie types.
     */
    public String getSortedTypesJoined() {
        return bogies.stream()
                .map(Bogie::getType)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    /**
     * Groups bogies by class category and sums capacity per group.
     */
    public Map<String, Integer> totalCapacityByCategory() {
        return bogies.stream()
                .collect(Collectors.groupingBy(
                        Bogie::getClassCategory,
                        Collectors.summingInt(Bogie::getCapacity)
                ));
    }

    /**
     * Returns distinct class categories present in the consist.
     */
    public List<String> distinctCategories() {
        return bogies.stream()
                .map(Bogie::getClassCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Returns the average fare multiplier for ACTIVE bogies.
     */
    public OptionalDouble averageFareMultiplierActive() {
        return bogies.stream()
                .filter(b -> "ACTIVE".equals(b.getStatus()))
                .mapToDouble(Bogie::getFareMultiplier)
                .average();
    }

    public List<Bogie> getBogies() { return new ArrayList<>(bogies); }

    public static void main(String[] args) {
        List<Bogie> bogies = Arrays.asList(
            new Bogie("B001", "First Class AC",   "PREMIUM",  24,  3.5, "ACTIVE"),
            new Bogie("B002", "AC Chair Car",      "PREMIUM",  60,  2.5, "ACTIVE"),
            new Bogie("B003", "Sleeper",           "STANDARD", 72,  1.5, "ACTIVE"),
            new Bogie("B004", "AC 3-Tier",         "STANDARD", 64,  2.0, "MAINTENANCE"),
            new Bogie("B005", "General",           "ECONOMY",  90,  1.0, "ACTIVE"),
            new Bogie("B006", "Unreserved",        "ECONOMY",  100, 1.0, "RETIRED"),
            new Bogie("B007", "Pantry Car",        "STANDARD",  0,  1.0, "ACTIVE")
        );

        UseCase20TrainConsistMgmt mgmt = new UseCase20TrainConsistMgmt(bogies);

        System.out.println("=== Group by Category ===");
        mgmt.groupByCategory().forEach((cat, list) ->
            System.out.println("  " + cat + ": " + list.size() + " bogies"));

        System.out.println("\n=== Count by Status ===");
        mgmt.countByStatus().forEach((status, count) ->
            System.out.println("  " + status + ": " + count));

        System.out.println("\n=== Active vs Non-Active ===");
        Map<Boolean, List<Bogie>> partitioned = mgmt.partitionByActive();
        System.out.println("  Active: " + partitioned.get(true).size());
        System.out.println("  Non-Active: " + partitioned.get(false).size());

        System.out.println("\n=== Total Active Capacity ===");
        System.out.println("  " + mgmt.totalActiveCapacity() + " seats");

        System.out.println("\n=== Capacity Statistics ===");
        IntSummaryStatistics stats = mgmt.capacityStatistics();
        System.out.printf("  Min=%d, Max=%d, Avg=%.1f, Sum=%d%n",
            stats.getMin(), stats.getMax(), stats.getAverage(), (long) stats.getSum());

        System.out.println("\n=== Highest Capacity Bogie ===");
        mgmt.highestCapacityBogie().ifPresent(b -> System.out.println("  " + b));

        System.out.println("\n=== Sorted Types ===");
        System.out.println("  " + mgmt.getSortedTypesJoined());

        System.out.println("\n=== Total Capacity by Category ===");
        mgmt.totalCapacityByCategory().forEach((cat, cap) ->
            System.out.println("  " + cat + ": " + cap + " seats"));

        System.out.println("\n=== Distinct Categories ===");
        System.out.println("  " + mgmt.distinctCategories());

        System.out.println("\n=== Avg Fare Multiplier (Active) ===");
        mgmt.averageFareMultiplierActive().ifPresent(avg ->
            System.out.printf("  %.2f%n", avg));
    }
}
