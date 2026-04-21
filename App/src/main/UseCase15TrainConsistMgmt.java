package main;

/**
 * UC15: Safe Cargo Assignment Using try-with-resources
 * 
 * Scenario:
 * When assigning cargo to a goods bogie, a log entry must be managed safely.
 * This use case uses an AutoCloseable logger within a try-with-resources 
 * block to ensure that resources are released even if an error occurs.
 */
public class UseCase15TrainConsistMgmt {

    /**
     * A simulated Cargo Assignment Logger that implements AutoCloseable.
     */
    public static class CargoAssignmentLogger implements AutoCloseable {
        private boolean isOpen = false;

        public CargoAssignmentLogger() {
            this.isOpen = true;
            System.out.println("[Resource] Cargo Logger opened.");
        }

        public void logAssignment(String bogieId, String cargo) {
            if (!isOpen) {
                throw new IllegalStateException("Logger is closed!");
            }
            System.out.println("[Log] assigned " + cargo + " to " + bogieId);
        }

        public boolean isOpen() {
            return isOpen;
        }

        @Override
        public void close() {
            isOpen = false;
            System.out.println("[Resource] Cargo Logger closed automatically.");
        }
    }

    /**
     * Performs a safe cargo assignment using try-with-resources.
     */
    public void assignCargoSafely(String bogieId, String cargo) {
        try (CargoAssignmentLogger logger = new CargoAssignmentLogger()) {
            logger.logAssignment(bogieId, cargo);
            // Logger will be closed here automatically
        } catch (Exception e) {
            System.err.println("Assignment failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        UseCase15TrainConsistMgmt manager = new UseCase15TrainConsistMgmt();

        System.out.println("--- Starting Safe Cargo Assignment ---");
        manager.assignCargoSafely("G101", "Chemicals");
        System.out.println("--- Assignment Process Finished ---\n");

        System.out.println("--- Starting Another Assignment ---");
        manager.assignCargoSafely("G102", "Steel Beams");
        System.out.println("--- Assignment Process Finished ---");
    }
}
