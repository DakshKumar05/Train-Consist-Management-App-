package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC17: Train Consist Management - Interfaces & Abstract Classes
 * Demonstrates interfaces (Inspectable, Maintainable) combined with
 * abstract classes and concrete implementations for bogies.
 */
public class UseCase17TrainConsistMgmt {

    // Interface: something that can be inspected
    public interface Inspectable {
        boolean inspect();
        String getInspectionReport();
    }

    // Interface: something that can be maintained
    public interface Maintainable {
        void scheduleMaintenance(String date);
        String getMaintenanceSchedule();
        boolean isUnderMaintenance();
    }

    // Abstract class: base for all bogies
    public abstract static class AbstractBogie implements Inspectable, Maintainable {
        protected String bogieId;
        protected String type;
        protected int ageYears;
        protected boolean underMaintenance;
        protected String maintenanceDate;

        public AbstractBogie(String bogieId, String type, int ageYears) {
            if (bogieId == null || bogieId.trim().isEmpty()) {
                throw new IllegalArgumentException("Bogie ID cannot be null or empty.");
            }
            this.bogieId = bogieId;
            this.type = type;
            this.ageYears = ageYears;
            this.underMaintenance = false;
            this.maintenanceDate = "Not Scheduled";
        }

        public String getBogieId() { return bogieId; }
        public String getType() { return type; }
        public int getAgeYears() { return ageYears; }

        // Abstract: subclasses define their own max allowed age
        public abstract int getMaxAgeYears();

        @Override
        public boolean inspect() {
            return ageYears <= getMaxAgeYears() && !underMaintenance;
        }

        @Override
        public void scheduleMaintenance(String date) {
            this.maintenanceDate = date;
            this.underMaintenance = true;
            System.out.println(bogieId + " maintenance scheduled for: " + date);
        }

        @Override
        public String getMaintenanceSchedule() {
            return bogieId + " -> Maintenance Date: " + maintenanceDate;
        }

        @Override
        public boolean isUnderMaintenance() { return underMaintenance; }

        public void completeMaintenance() {
            this.underMaintenance = false;
            System.out.println(bogieId + " maintenance completed.");
        }

        @Override
        public String toString() {
            return type + " [" + bogieId + "] Age:" + ageYears + "yrs, Maintenance:" + underMaintenance;
        }
    }

    // Concrete: Passenger Bogie
    public static class PassengerBogie extends AbstractBogie {
        private static final int MAX_AGE = 25;

        public PassengerBogie(String bogieId, String type, int ageYears) {
            super(bogieId, type, ageYears);
        }

        @Override
        public int getMaxAgeYears() { return MAX_AGE; }

        @Override
        public String getInspectionReport() {
            return "PassengerBogie [" + bogieId + "]: Age=" + ageYears +
                    ", MaxAge=" + MAX_AGE + ", PassInspection=" + inspect();
        }
    }

    // Concrete: Freight Bogie
    public static class FreightBogie extends AbstractBogie {
        private static final int MAX_AGE = 20;

        public FreightBogie(String bogieId, String type, int ageYears) {
            super(bogieId, type, ageYears);
        }

        @Override
        public int getMaxAgeYears() { return MAX_AGE; }

        @Override
        public String getInspectionReport() {
            return "FreightBogie [" + bogieId + "]: Age=" + ageYears +
                    ", MaxAge=" + MAX_AGE + ", PassInspection=" + inspect();
        }
    }

    private List<AbstractBogie> bogies;

    public UseCase17TrainConsistMgmt() {
        this.bogies = new ArrayList<>();
    }

    public void addBogie(AbstractBogie bogie) {
        bogies.add(bogie);
    }

    public void inspectAll() {
        System.out.println("=== Inspection Report ===");
        bogies.forEach(b -> System.out.println("  " + b.getInspectionReport()));
    }

    public long countPassingInspection() {
        return bogies.stream().filter(Inspectable::inspect).count();
    }

    public List<AbstractBogie> getBogiesUnderMaintenance() {
        List<AbstractBogie> result = new ArrayList<>();
        for (AbstractBogie b : bogies) {
            if (b.isUnderMaintenance()) result.add(b);
        }
        return result;
    }

    public List<AbstractBogie> getBogies() { return new ArrayList<>(bogies); }

    public static void main(String[] args) {
        UseCase17TrainConsistMgmt mgmt = new UseCase17TrainConsistMgmt();

        mgmt.addBogie(new PassengerBogie("P001", "Sleeper", 10));
        mgmt.addBogie(new PassengerBogie("P002", "AC Chair Car", 28)); // Over age limit
        mgmt.addBogie(new FreightBogie("F001", "Goods Van", 15));
        mgmt.addBogie(new FreightBogie("F002", "Tank Car", 5));

        mgmt.inspectAll();

        // Schedule maintenance
        mgmt.getBogies().get(0).scheduleMaintenance("2024-05-01");

        System.out.println("Passing inspection: " + mgmt.countPassingInspection());
        System.out.println("Under maintenance: " + mgmt.getBogiesUnderMaintenance().size());
    }
}
