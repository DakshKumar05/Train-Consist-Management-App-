class Bogie {
    String type;
    int capacity;

    public Bogie(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }
}

import java.util.*;
        import java.util.stream.*;

public class TrainConsistManagementApp {

    public static void main(String[] args) {

        // Create list of bogies
        List<Bogie> bogies = new ArrayList<>();

        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 60));
        bogies.add(new Bogie("First Class", 30));

        // UC10: Calculate total seating capacity using Stream reduce
        int totalSeats = bogies.stream()
                .map(b -> b.getCapacity())     // Extract capacity
                .reduce(0, Integer::sum);      // Aggregate using reduce

        // Output
        System.out.println("Total Seating Capacity: " + totalSeats);
    }
}