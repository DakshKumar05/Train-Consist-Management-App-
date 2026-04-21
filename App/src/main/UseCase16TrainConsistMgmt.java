package main;

import java.util.ArrayList;
import java.util.List;

/**
 * UC16: Inheritance & Polymorphism (RollingStock)
 * 
 * Scenario:
 * A train consist is made of different types of rolling stock:
 * Locomotives and Bogies. Both share common traits like weight 
 * and ID, but behave differently.
 * This use case demonstrates Inheritance and Polymorphism.
 */
public class UseCase16TrainConsistMgmt {

    // Base class
    public abstract static class RollingStock {
        protected String id;
        protected double weight;

        public RollingStock(String id, double weight) {
            this.id = id;
            this.weight = weight;
        }

        public abstract String getDescription();

        public String getId() { return id; }
        public double getWeight() { return weight; }
    }

    // Subclass 1
    public static class Locomotive extends RollingStock {
        private double horsePower;

        public Locomotive(String id, double weight, double horsePower) {
            super(id, weight);
            this.horsePower = horsePower;
        }

        @Override
        public String getDescription() {
            return "Locomotive " + id + " [" + horsePower + " HP]";
        }
    }

    // Subclass 2
    public static class Bogie extends RollingStock {
        private int capacity;

        public Bogie(String id, double weight, int capacity) {
            super(id, weight);
            this.capacity = capacity;
        }

        @Override
        public String getDescription() {
            return "Bogie " + id + " [" + capacity + " Seats]";
        }
    }

    public static void main(String[] args) {
        List<RollingStock> consist = new ArrayList<>();
        consist.add(new Locomotive("L001", 120.0, 4400.0));
        consist.add(new Bogie("B001", 45.0, 72));
        consist.add(new Bogie("B002", 45.0, 72));

        System.out.println("=== Train Consist Details (Polymorphism) ===");
        for (RollingStock rs : consist) {
            // Polymorphic call to getDescription()
            System.out.println(rs.getDescription() + " (Weight: " + rs.getWeight() + "T)");
        }
    }
}
