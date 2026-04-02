import java.util.HashSet;
import java.util.Set;

public class TrainConsistManagementApp {

    public static void main(String[] args) {
        // Welcome message
        System.out.println("=== Train Consist Management App ===");

        // Initialize HashSet for bogie IDs
        Set<String> bogieIDs = new HashSet<>();

        // Add bogie IDs (with duplicates intentionally)
        bogieIDs.add("BG101");
        bogieIDs.add("BG102");
        bogieIDs.add("BG103");
        bogieIDs.add("BG101"); // duplicate
        bogieIDs.add("BG102"); // duplicate

        // Display final set of bogie IDs
        System.out.println("Unique bogie IDs in train consist: " + bogieIDs);
    }
}

