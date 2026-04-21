package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UC12: Safety Compliance Check for Goods Bogies
 * 
 * Scenario:
 * Railway authorities must ensure that goods bogies comply with safety standards.
 * Each goods bogie has a weight limit and specific cargo type.
 * Specifically, bogies carrying "Inflammable" cargo have stricter weight limits.
 * 
 * Rules:
 * 1. General Limit: Max 80.0 tonnes.
 * 2. Inflammable Limit: Max 50.0 tonnes.
 * 3. Cargo must not be null/empty.
 */
public class UseCase12TrainConsistMgmt {

    public static class GoodsBogie {
        private String id;
        private String cargoType;
        private double weightTonnes;

        public GoodsBogie(String id, String cargoType, double weightTonnes) {
            this.id = id;
            this.cargoType = cargoType;
            this.weightTonnes = weightTonnes;
        }

        public String getId() { return id; }
        public String getCargoType() { return cargoType; }
        public double getWeightTonnes() { return weightTonnes; }

        public boolean isSafe() {
            if (cargoType == null || cargoType.isEmpty()) return false;
            
            if (cargoType.equalsIgnoreCase("Inflammable")) {
                return weightTonnes <= 50.0;
            }
            return weightTonnes <= 80.0;
        }

        @Override
        public String toString() {
            return String.format("GoodsBogie[ID=%s, Cargo=%s, Weight=%.1fT, Safe=%b]", 
                id, cargoType, weightTonnes, isSafe());
        }
    }

    private List<GoodsBogie> consist;

    public UseCase12TrainConsistMgmt() {
        this.consist = new ArrayList<>();
    }

    public void addBogie(GoodsBogie bogie) {
        consist.add(bogie);
    }

    /**
     * Filters bogies that fail safety compliance.
     */
    public List<GoodsBogie> getUnsafeBogies() {
        return consist.stream()
                .filter(b -> !b.isSafe())
                .collect(Collectors.toList());
    }

    /**
     * Checks if the entire train consist is safety compliant.
     */
    public boolean isWholeTrainSafe() {
        return consist.stream().allMatch(GoodsBogie::isSafe);
    }

    public void displaySafetyReport() {
        System.out.println("=== Railway Safety Compliance Report ===");
        consist.forEach(System.out::println);
        
        if (isWholeTrainSafe()) {
            System.out.println("\nRESULT: Train is READY for departure. All bogies compliant.");
        } else {
            System.out.println("\nRESULT: Train HALTED. Safety violations found!");
            getUnsafeBogies().forEach(b -> System.out.println("  - Violation: " + b.getId()));
        }
    }

    public static void main(String[] args) {
        UseCase12TrainConsistMgmt manager = new UseCase12TrainConsistMgmt();

        manager.addBogie(new GoodsBogie("G101", "Coal", 75.0));
        manager.addBogie(new GoodsBogie("G102", "Inflammable", 45.0));
        manager.addBogie(new GoodsBogie("G103", "Inflammable", 65.0)); // Violation (>50)
        manager.addBogie(new GoodsBogie("G104", "Steel", 85.0));       // Violation (>80)

        manager.displaySafetyReport();
    }
}
