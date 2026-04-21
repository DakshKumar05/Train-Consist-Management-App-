package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UC13: Train Consist Management - Exception Handling
 * Demonstrates robust exception handling for invalid bogie operations,
 * including custom exceptions and try-catch-finally blocks.
 */
public class UseCase13TrainConsistMgmt {

    // Custom exception for invalid bogie operations
    public static class InvalidBogieException extends Exception {
        public InvalidBogieException(String message) {
            super(message);
        }

        public InvalidBogieException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Custom exception for consist overflow
    public static class ConsistCapacityExceededException extends RuntimeException {
        public ConsistCapacityExceededException(String message) {
            super(message);
        }
    }

    private List<String> bogies;
    private final int maxBogies;

    public UseCase13TrainConsistMgmt(int maxBogies) {
        if (maxBogies <= 0) {
            throw new IllegalArgumentException("Max bogies must be greater than 0.");
        }
        this.bogies = new ArrayList<>();
        this.maxBogies = maxBogies;
    }

    /**
     * Adds a bogie with full validation and exception handling.
     * @throws InvalidBogieException if type is invalid
     * @throws ConsistCapacityExceededException if consist is full
     */
    public void addBogie(String bogieType) throws InvalidBogieException {
        try {
            if (bogieType == null || bogieType.trim().isEmpty()) {
                throw new InvalidBogieException("Bogie type cannot be null or empty.");
            }
            if (bogies.size() >= maxBogies) {
                throw new ConsistCapacityExceededException(
                        "Cannot add bogie: consist is at maximum capacity of " + maxBogies + ".");
            }
            bogies.add(bogieType);
            System.out.println("Bogie added: " + bogieType);
        } catch (InvalidBogieException e) {
            System.err.println("Invalid bogie error: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("addBogie operation completed. Current size: " + bogies.size());
        }
    }

    /**
     * Retrieves a bogie at a given index with bounds checking.
     * @throws InvalidBogieException if index is out of range
     */
    public String getBogieAt(int index) throws InvalidBogieException {
        try {
            return bogies.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidBogieException("Index " + index + " is out of range. Current size: " + bogies.size(), e);
        }
    }

    /**
     * Safely removes a bogie, returning Optional to avoid null handling.
     */
    public Optional<String> safeRemoveBogie(String bogieType) {
        boolean removed = bogies.remove(bogieType);
        return removed ? Optional.of(bogieType) : Optional.empty();
    }

    public int getBogieCount() { return bogies.size(); }
    public int getMaxBogies() { return maxBogies; }
    public boolean isFull() { return bogies.size() >= maxBogies; }

    public void displayConsist() {
        System.out.println("Consist (" + bogies.size() + "/" + maxBogies + "): " + bogies);
    }

    public static void main(String[] args) {
        UseCase13TrainConsistMgmt consist = new UseCase13TrainConsistMgmt(3);

        try {
            consist.addBogie("Sleeper");
            consist.addBogie("AC Chair Car");
            consist.addBogie("General");
            consist.displayConsist();

            // This should throw ConsistCapacityExceededException
            consist.addBogie("First Class");
        } catch (InvalidBogieException e) {
            System.err.println("Caught: " + e.getMessage());
        } catch (ConsistCapacityExceededException e) {
            System.err.println("Capacity exceeded: " + e.getMessage());
        }

        // Try invalid index
        try {
            System.out.println(consist.getBogieAt(10));
        } catch (InvalidBogieException e) {
            System.err.println("Index error: " + e.getMessage());
        }

        // Try null bogie
        try {
            consist.addBogie(null);
        } catch (InvalidBogieException e) {
            System.err.println("Caught null bogie: " + e.getMessage());
        }

        // Safe remove
        Optional<String> removed = consist.safeRemoveBogie("Sleeper");
        System.out.println("Removed: " + removed.orElse("Not found"));
    }
}
