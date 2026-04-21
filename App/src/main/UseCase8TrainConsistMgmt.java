package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC8: Train Consist Management
 * Demonstrates adding and removing bogies from a train consist using ArrayList.
 */
public class UseCase8TrainConsistMgmt {

    private List<String> bogies;

    public UseCase8TrainConsistMgmt() {
        this.bogies = new ArrayList<>();
    }

    /**
     * Adds a bogie to the train consist.
     * @param bogieType the type of bogie to add
     */
    public void addBogie(String bogieType) {
        if (bogieType == null || bogieType.trim().isEmpty()) {
            throw new IllegalArgumentException("Bogie type cannot be null or empty.");
        }
        bogies.add(bogieType);
        System.out.println("Bogie added: " + bogieType);
    }

    /**
     * Removes a bogie from the train consist by type.
     * @param bogieType the type of bogie to remove
     * @return true if removed, false if not found
     */
    public boolean removeBogie(String bogieType) {
        boolean removed = bogies.remove(bogieType);
        if (removed) {
            System.out.println("Bogie removed: " + bogieType);
        } else {
            System.out.println("Bogie not found: " + bogieType);
        }
        return removed;
    }

    /**
     * Returns the current list of bogies in the consist.
     * @return list of bogie types
     */
    public List<String> getBogies() {
        return new ArrayList<>(bogies);
    }

    /**
     * Returns the total number of bogies in the consist.
     * @return count of bogies
     */
    public int getBogieCount() {
        return bogies.size();
    }

    /**
     * Displays the current train consist.
     */
    public void displayConsist() {
        System.out.println("Current Train Consist (" + bogies.size() + " bogies):");
        if (bogies.isEmpty()) {
            System.out.println("  [No bogies in consist]");
        } else {
            for (int i = 0; i < bogies.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + bogies.get(i));
            }
        }
    }

    public static void main(String[] args) {
        UseCase8TrainConsistMgmt consist = new UseCase8TrainConsistMgmt();

        // Add bogies
        consist.addBogie("Sleeper");
        consist.addBogie("AC Chair Car");
        consist.addBogie("First Class AC");
        consist.addBogie("General");
        consist.addBogie("Pantry Car");

        // Display current consist
        consist.displayConsist();

        // Remove a bogie
        consist.removeBogie("General");

        // Try removing a non-existent bogie
        consist.removeBogie("Luxury Suite");

        // Display updated consist
        consist.displayConsist();

        System.out.println("Total bogies: " + consist.getBogieCount());
    }
}
