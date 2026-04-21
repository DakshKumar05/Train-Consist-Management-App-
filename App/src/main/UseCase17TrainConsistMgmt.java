package main;

/**
 * UC17: Interfaces & Abstract Classes (Inspectable & Maintainable)
 * 
 * Scenario:
 * Railway safety depends on strict inspection and maintenance protocols.
 * This use case uses Interfaces to define contracts for items 
 * that can be inspected or maintained.
 */
public class UseCase17TrainConsistMgmt {

    // Interface 1: Contract for safety inspection
    public interface Inspectable {
        boolean performInspection();
        String getInspectionReport();
    }

    // Interface 2: Contract for maintenance
    public interface Maintainable {
        void performMaintenance();
        boolean isMaintenanceRequired();
    }

    // Abstract Class implementing both interfaces
    public abstract static class RailCar implements Inspectable, Maintainable {
        protected String carId;
        protected boolean inspectionPassed = false;
        protected boolean maintenanceComplete = false;

        public RailCar(String id) {
            this.carId = id;
        }

        @Override
        public String getInspectionReport() {
            return "RailCar " + carId + " Inspection Result: " + (inspectionPassed ? "PASS" : "FAIL");
        }

        @Override
        public boolean isMaintenanceRequired() {
            return !maintenanceComplete;
        }
    }

    // Concrete implementation: PassengerBogie
    public static class PassengerBogie extends RailCar {
        public PassengerBogie(String id) {
            super(id);
        }

        @Override
        public boolean performInspection() {
            // Simulated inspection logic
            this.inspectionPassed = true; 
            return true;
        }

        @Override
        public void performMaintenance() {
            System.out.println("Cleaning and oiling Passenger Bogie: " + carId);
            this.maintenanceComplete = true;
        }
    }

    public static void main(String[] args) {
        PassengerBogie bogie = new PassengerBogie("P-202");

        System.out.println("--- Initial State ---");
        System.out.println("Maintenance Required? " + bogie.isMaintenanceRequired());

        System.out.println("\n--- Performing Actions ---");
        bogie.performInspection();
        bogie.performMaintenance();

        System.out.println("\n--- Final State ---");
        System.out.println(bogie.getInspectionReport());
        System.out.println("Maintenance Required? " + bogie.isMaintenanceRequired());
    }
}
