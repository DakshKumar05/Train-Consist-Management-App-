import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Bogie {
    String type;
    int capacity;

    public Bogie(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    // Helper method to get type for grouping
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + type + ", Cap: " + capacity + ")";
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        // 1. Create a diverse list of bogies
        List<Bogie> consist = new ArrayList<>();
        consist.add(new Bogie("Sleeper", 72));
        consist.add(new Bogie("AC Chair", 60));
        consist.add(new Bogie("Sleeper", 72));
        consist.add(new Bogie("First Class", 24));
        consist.add(new Bogie("AC Chair", 60));
        consist.add(new Bogie("Goods-Rectangular", 100));

        System.out.println("--- Flat Consist List ---");
        System.out.println(consist);

        // 2. Apply groupingBy to categorize bogies by their type
        // Key: Bogie Type (String), Value: List of Bogies belonging to that type
        Map<String, List<Bogie>> groupedBogies = consist.stream()
                .collect(Collectors.groupingBy(Bogie::getType));

        // 3. Display the structured output
        System.out.println("\n--- Grouped Bogie Report ---");
        groupedBogies.forEach((type, list) -> {
            System.out.println("Category: [" + type + "] -> Count: " + list.size());
            System.out.println("   Details: " + list);
        });

        // 4. Verify original list integrity
        System.out.println("\nOriginal list remains untouched. Size: " + consist.size());
    }
}