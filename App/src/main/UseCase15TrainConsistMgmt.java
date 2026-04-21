package main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;

/**
 * UC15: Train Consist Management - Lambda Expressions & Functional Interfaces
 * Demonstrates using lambdas, Predicate, Consumer, and Function interfaces
 * to process and filter bogies.
 */
public class UseCase15TrainConsistMgmt {

    public static class Bogie {
        private String type;
        private int capacity;
        private String status; // "ACTIVE" or "MAINTENANCE"

        public Bogie(String type, int capacity, String status) {
            this.type = type;
            this.capacity = capacity;
            this.status = status;
        }

        public String getType() { return type; }
        public int getCapacity() { return capacity; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        @Override
        public String toString() {
            return String.format("Bogie{type='%s', cap=%d, status='%s'}", type, capacity, status);
        }
    }

    private List<Bogie> bogies;

    public UseCase15TrainConsistMgmt() {
        this.bogies = new ArrayList<>();
    }

    public void addBogie(Bogie bogie) {
        bogies.add(bogie);
    }

    /**
     * Filters bogies using a Predicate lambda.
     */
    public List<Bogie> filterBogies(Predicate<Bogie> predicate) {
        List<Bogie> result = new ArrayList<>();
        for (Bogie b : bogies) {
            if (predicate.test(b)) result.add(b);
        }
        return result;
    }

    /**
     * Applies a Consumer action to all bogies.
     */
    public void applyToAll(Consumer<Bogie> action) {
        bogies.forEach(action);
    }

    /**
     * Maps each bogie to a String using a Function.
     */
    public List<String> mapBogies(Function<Bogie, String> mapper) {
        List<String> result = new ArrayList<>();
        for (Bogie b : bogies) {
            result.add(mapper.apply(b));
        }
        return result;
    }

    /**
     * Returns count of bogies matching a predicate.
     */
    public long countByPredicate(Predicate<Bogie> predicate) {
        return bogies.stream().filter(predicate).count();
    }

    public List<Bogie> getBogies() { return new ArrayList<>(bogies); }

    public void display(String label) {
        System.out.println(label + ":");
        bogies.forEach(b -> System.out.println("  " + b));
    }

    public static void main(String[] args) {
        UseCase15TrainConsistMgmt consist = new UseCase15TrainConsistMgmt();
        consist.addBogie(new Bogie("Sleeper", 72, "ACTIVE"));
        consist.addBogie(new Bogie("AC Chair Car", 60, "MAINTENANCE"));
        consist.addBogie(new Bogie("General", 90, "ACTIVE"));
        consist.addBogie(new Bogie("First Class AC", 30, "ACTIVE"));

        consist.display("All Bogies");

        // Lambda as Predicate: filter active bogies
        List<Bogie> activeBogies = consist.filterBogies(b -> "ACTIVE".equals(b.getStatus()));
        System.out.println("\nActive Bogies: " + activeBogies.size());

        // Lambda as Predicate: filter high capacity
        List<Bogie> highCap = consist.filterBogies(b -> b.getCapacity() >= 70);
        System.out.println("High Capacity (>=70): " + highCap);

        // Lambda as Consumer: print summary
        consist.applyToAll(b -> System.out.println("  Status check -> " + b.getType() + ": " + b.getStatus()));

        // Lambda as Function: map to summary string
        List<String> summaries = consist.mapBogies(b -> b.getType() + " [" + b.getCapacity() + " seats]");
        System.out.println("\nBogie Summaries:");
        summaries.forEach(s -> System.out.println("  " + s));

        // Count
        long activeCount = consist.countByPredicate(b -> "ACTIVE".equals(b.getStatus()));
        System.out.println("\nTotal Active Bogies: " + activeCount);
    }
}
