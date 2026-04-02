import java.util.LinkedList;

public class TrainConsistManagementApp {

    public static void main(String[] args) {
        // Welcome message
        System.out.println("=== Train Consist Management App ===");

        // Initialize LinkedList for train consist
        LinkedList<String> trainConsist = new LinkedList<>();

        // Add bogies in sequence
        trainConsist.add("Engine");
        trainConsist.add("Sleeper");
        trainConsist.add("AC");
        trainConsist.add("Cargo");
        trainConsist.add("Guard");

        // Display initial consist
        System.out.println("Initial train consist: " + trainConsist);

        // Insert Pantry Car at position 2 (index starts at 0)
        trainConsist.add(2, "Pantry Car");
        System.out.println("After inserting Pantry Car at position 2: " + trainConsist);

        // Remove the first bogie (Engine)
        trainConsist.removeFirst();
        System.out.println("After removing first bogie: " + trainConsist);

        // Remove the last bogie (Guard)
        trainConsist.removeLast();
        System.out.println("After removing last bogie: " + trainConsist);

        // Final ordered consist
        System.out.println("Final ordered train consist: " + trainConsist);
    }
}
