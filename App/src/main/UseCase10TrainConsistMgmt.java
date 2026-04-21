package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

/**
 * UC10: Count Total Seats in Train (reduce)
 *
 * Drawback of UC9:
 * In UC9, bogies are organized into logical groups using groupingBy().
 * While grouping structures the data, it does not provide numerical insight.
 * In real railway operations, administration often needs to:
 *  - Know the total seating capacity of the train.
 *  - Estimate passenger handling capability.
 *  - Perform utilization planning.
 *
 * Goal:
 * Aggregate seating capacities into a single total value using Stream reduction.
 *
 * Key Concepts:
 * - map() extracts capacity values from Bogie objects.
 * - reduce(0, Integer::sum) sums the capacities into a single total.
 * - Method Reference (Integer::sum) for clean aggregation.
 * - Functional Aggregation replaces manual loops.
 * - Stream Pipeline chains transformation and aggregation.
 */
public class UseCase10TrainConsistMgmt {

    // Bogie class with type and capacity
    String type;
    int capacity;

    public UseCase10TrainConsistMgmt(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " (Capacity: " + capacity + ")";
    }

    public static void main(String[] args) {

        // Create list of bogies
        List<UseCase10TrainConsistMgmt> bogies = new ArrayList<>();

        bogies.add(new UseCase10TrainConsistMgmt("Sleeper", 72));
        bogies.add(new UseCase10TrainConsistMgmt("AC Chair", 60));
        bogies.add(new UseCase10TrainConsistMgmt("First Class", 30));

        // UC10: Calculate total seating capacity using Stream reduce
        int totalSeats = bogies.stream()
                .map(b -> b.getCapacity())     // Extract capacity
                .reduce(0, Integer::sum);      // Aggregate using reduce

        // Output
        System.out.println("Total Seating Capacity: " + totalSeats);
    }
}
