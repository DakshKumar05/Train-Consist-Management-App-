import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Bogie {
    String type;
    int capacity;

    public Bogie(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Bogie{Type='" + type + "', Capacity=" + capacity + "}";
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        // 1. Reuse/Create the list of bogies (from UC7)
        List<Bogie> consist = new ArrayList<>();
        consist.add(new Bogie("Sleeper", 72));
        consist.add(new Bogie("AC Chair Car", 60));
        consist.add(new Bogie("First Class", 24));
        consist.add(new Bogie("Sleeper", 72));
        consist.add(new Bogie("General", 90));

        System.out.println("Original Consist:");
        consist.forEach(System.out::println);

        // 2. Define the filtering threshold
        int capacityThreshold = 60;

        // 3. Apply Stream API: stream() -> filter() -> collect()
        List<Bogie> highCapacityBogies = consist.stream()
                .filter(b -> b.capacity > capacityThreshold) // Lambda expression for condition
                .collect(Collectors.toList());               // Terminal operation to create new list

        // 4. Display the filtered results
        System.out.println("\n--- High-Capacity Bogies (Capacity > " + capacityThreshold + ") ---");
        if (highCapacityBogies.isEmpty()) {
            System.out.println("No bogies found matching the criteria.");
        } else {
            highCapacityBogies.forEach(System.out::println);
        }

        // 5. Verification: Original list integrity
        System.out.println("\nOriginal list size remains: " + consist.size());
    }
}