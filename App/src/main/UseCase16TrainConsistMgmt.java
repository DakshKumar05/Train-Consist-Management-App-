package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC16: Train Consist Management - Inheritance & Polymorphism
 * Demonstrates OOP concepts with a base RollingStock class and subclasses
 * (PassengerBogie, FreightBogie, Engine) using polymorphism.
 */
public class UseCase16TrainConsistMgmt {

    // Abstract base class
    public abstract static class RollingStock {
        protected String id;
        protected String manufacturer;
        protected int yearBuilt;

        public RollingStock(String id, String manufacturer, int yearBuilt) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID cannot be null or empty.");
            }
            this.id = id;
            this.manufacturer = manufacturer;
            this.yearBuilt = yearBuilt;
        }

        public String getId() { return id; }
        public String getManufacturer() { return manufacturer; }
        public int getYearBuilt() { return yearBuilt; }

        // Abstract method: polymorphic behavior
        public abstract String getDescription();
        public abstract double getWeightTonnes();

        @Override
        public String toString() {
            return getDescription() + " [ID: " + id + ", Built: " + yearBuilt + "]";
        }
    }

    // Passenger Bogie subclass
    public static class PassengerBogie extends RollingStock {
        private int seatCapacity;
        private String classType; // "Sleeper", "AC", "General", etc.

        public PassengerBogie(String id, String manufacturer, int yearBuilt,
                              int seatCapacity, String classType) {
            super(id, manufacturer, yearBuilt);
            this.seatCapacity = seatCapacity;
            this.classType = classType;
        }

        public int getSeatCapacity() { return seatCapacity; }
        public String getClassType() { return classType; }

        @Override
        public String getDescription() {
            return "PassengerBogie{" + classType + ", seats=" + seatCapacity + "}";
        }

        @Override
        public double getWeightTonnes() { return 42.0; }
    }

    // Freight Bogie subclass
    public static class FreightBogie extends RollingStock {
        private double loadCapacityTonnes;
        private String cargoType;

        public FreightBogie(String id, String manufacturer, int yearBuilt,
                            double loadCapacityTonnes, String cargoType) {
            super(id, manufacturer, yearBuilt);
            this.loadCapacityTonnes = loadCapacityTonnes;
            this.cargoType = cargoType;
        }

        public double getLoadCapacityTonnes() { return loadCapacityTonnes; }
        public String getCargoType() { return cargoType; }

        @Override
        public String getDescription() {
            return "FreightBogie{" + cargoType + ", load=" + loadCapacityTonnes + "T}";
        }

        @Override
        public double getWeightTonnes() { return 28.0; }
    }

    // Locomotive subclass
    public static class Locomotive extends RollingStock {
        private int horsePower;
        private String tractionType;

        public Locomotive(String id, String manufacturer, int yearBuilt,
                          int horsePower, String tractionType) {
            super(id, manufacturer, yearBuilt);
            this.horsePower = horsePower;
            this.tractionType = tractionType;
        }

        public int getHorsePower() { return horsePower; }
        public String getTractionType() { return tractionType; }

        @Override
        public String getDescription() {
            return "Locomotive{" + tractionType + ", hp=" + horsePower + "}";
        }

        @Override
        public double getWeightTonnes() { return 123.0; }
    }

    // Consist manager using polymorphism
    private List<RollingStock> consist;

    public UseCase16TrainConsistMgmt() {
        this.consist = new ArrayList<>();
    }

    public void addStock(RollingStock stock) {
        consist.add(stock);
    }

    public double getTotalWeight() {
        return consist.stream().mapToDouble(RollingStock::getWeightTonnes).sum();
    }

    public int getPassengerCapacity() {
        return consist.stream()
                .filter(s -> s instanceof PassengerBogie)
                .mapToInt(s -> ((PassengerBogie) s).getSeatCapacity())
                .sum();
    }

    public long countByType(Class<?> type) {
        return consist.stream().filter(type::isInstance).count();
    }

    public List<RollingStock> getConsist() { return new ArrayList<>(consist); }

    public void displayConsist() {
        System.out.println("Train Consist (" + consist.size() + " units):");
        consist.forEach(s -> System.out.println("  " + s));
        System.out.printf("Total Weight: %.1f tonnes%n", getTotalWeight());
        System.out.println("Total Passenger Capacity: " + getPassengerCapacity() + " seats");
    }

    public static void main(String[] args) {
        UseCase16TrainConsistMgmt train = new UseCase16TrainConsistMgmt();

        train.addStock(new Locomotive("L001", "CLW", 2015, 6120, "Electric"));
        train.addStock(new PassengerBogie("B001", "ICF", 2018, 72, "Sleeper"));
        train.addStock(new PassengerBogie("B002", "ICF", 2019, 60, "AC Chair Car"));
        train.addStock(new PassengerBogie("B003", "ICF", 2020, 90, "General"));
        train.addStock(new FreightBogie("F001", "RDSO", 2016, 60.5, "Coal"));

        train.displayConsist();

        System.out.println("Locomotives: " + train.countByType(Locomotive.class));
        System.out.println("Passenger Bogies: " + train.countByType(PassengerBogie.class));
        System.out.println("Freight Bogies: " + train.countByType(FreightBogie.class));
    }
}
