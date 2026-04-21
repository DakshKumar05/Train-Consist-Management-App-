package main;

/**
 * UC14: Handle Invalid Bogie Capacity
 * 
 * Scenario:
 * Railway management needs to ensure that no bogie is registered with 
 * a zero or negative seating capacity. 
 * This use case introduces custom exception handling to protect 
 * the integrity of the train consist data.
 */
public class UseCase14TrainConsistMgmt {

    // Custom Exception for Invalid Capacity
    public static class InvalidBogieCapacityException extends Exception {
        public InvalidBogieCapacityException(String message) {
            super(message);
        }
    }

    private String type;
    private int capacity;

    /**
     * Sets the bogie capacity with validation.
     * @param capacity the number of seats
     * @throws InvalidBogieCapacityException if capacity is not positive
     */
    public void setCapacity(int capacity) throws InvalidBogieCapacityException {
        if (capacity <= 0) {
            throw new InvalidBogieCapacityException("Invalid capacity: " + capacity + 
                ". Capacity must be a positive integer.");
        }
        this.capacity = capacity;
        System.out.println("Capacity set successfully to: " + capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public static void main(String[] args) {
        UseCase14TrainConsistMgmt bogie = new UseCase14TrainConsistMgmt();

        // 1. Try a valid capacity
        try {
            bogie.setCapacity(72);
        } catch (InvalidBogieCapacityException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 2. Try an invalid (zero) capacity
        try {
            bogie.setCapacity(0);
        } catch (InvalidBogieCapacityException e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }

        // 3. Try a negative capacity
        try {
            bogie.setCapacity(-5);
        } catch (InvalidBogieCapacityException e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }
    }
}
