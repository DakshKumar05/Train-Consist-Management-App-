package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * UC11: Train Consist Management - Bogie Type Lookup using HashMap
 * Manages a registry of bogie types and their specifications using HashMap.
 */
public class UseCase11TrainConsistMgmt {

    private Map<String, String> bogieRegistry; // bogieId -> bogieType

    public UseCase11TrainConsistMgmt() {
        this.bogieRegistry = new HashMap<>();
    }

    /**
     * Registers a bogie in the registry.
     * @param bogieId   unique identifier for the bogie
     * @param bogieType type of the bogie
     */
    public void registerBogie(String bogieId, String bogieType) {
        if (bogieId == null || bogieId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bogie ID cannot be null or empty.");
        }
        if (bogieType == null || bogieType.trim().isEmpty()) {
            throw new IllegalArgumentException("Bogie type cannot be null or empty.");
        }
        bogieRegistry.put(bogieId, bogieType);
        System.out.println("Registered: " + bogieId + " -> " + bogieType);
    }

    /**
     * Looks up bogie type by ID.
     * @param bogieId the bogie's ID
     * @return Optional containing bogie type if found
     */
    public Optional<String> lookupBogie(String bogieId) {
        return Optional.ofNullable(bogieRegistry.get(bogieId));
    }

    /**
     * Updates a bogie's type.
     * @return true if updated, false if ID not found
     */
    public boolean updateBogie(String bogieId, String newType) {
        if (!bogieRegistry.containsKey(bogieId)) {
            System.out.println("Bogie ID not found: " + bogieId);
            return false;
        }
        bogieRegistry.put(bogieId, newType);
        System.out.println("Updated: " + bogieId + " -> " + newType);
        return true;
    }

    /**
     * Removes a bogie from the registry.
     * @return true if removed, false if not found
     */
    public boolean deregisterBogie(String bogieId) {
        boolean existed = bogieRegistry.containsKey(bogieId);
        bogieRegistry.remove(bogieId);
        if (existed) {
            System.out.println("Deregistered: " + bogieId);
        } else {
            System.out.println("Bogie ID not found for deregistration: " + bogieId);
        }
        return existed;
    }

    /**
     * Checks if a bogie ID is already registered.
     */
    public boolean isRegistered(String bogieId) {
        return bogieRegistry.containsKey(bogieId);
    }

    /**
     * Returns the total number of registered bogies.
     */
    public int getRegistrySize() {
        return bogieRegistry.size();
    }

    /**
     * Displays all registered bogies.
     */
    public void displayRegistry() {
        System.out.println("Bogie Registry (" + bogieRegistry.size() + " entries):");
        bogieRegistry.forEach((id, type) ->
                System.out.println("  " + id + " : " + type));
    }

    public static void main(String[] args) {
        UseCase11TrainConsistMgmt mgmt = new UseCase11TrainConsistMgmt();

        mgmt.registerBogie("B001", "Sleeper");
        mgmt.registerBogie("B002", "AC Chair Car");
        mgmt.registerBogie("B003", "General");

        mgmt.displayRegistry();

        System.out.println("Lookup B002: " + mgmt.lookupBogie("B002").orElse("Not Found"));
        System.out.println("Lookup B999: " + mgmt.lookupBogie("B999").orElse("Not Found"));

        mgmt.updateBogie("B003", "First Class AC");
        mgmt.deregisterBogie("B001");

        mgmt.displayRegistry();
    }
}
