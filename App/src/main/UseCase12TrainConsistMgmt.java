package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * UC12: Train Consist Management - Sorting Bogies
 * Sorts bogies by capacity or type using Comparator and Collections.sort().
 */
public class UseCase12TrainConsistMgmt {

    public static class Bogie implements Comparable<Bogie> {
        private String type;
        private int capacity;
        private int position;

        public Bogie(String type, int capacity, int position) {
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("Bogie type cannot be null or empty.");
            }
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity cannot be negative.");
            }
            this.type = type;
            this.capacity = capacity;
            this.position = position;
        }

        public String getType() { return type; }
        public int getCapacity() { return capacity; }
        public int getPosition() { return position; }

        @Override
        public int compareTo(Bogie other) {
            return Integer.compare(this.position, other.position);
        }

        @Override
        public String toString() {
            return String.format("[Pos:%d] %s (Cap:%d)", position, type, capacity);
        }
    }

    private List<Bogie> bogies;

    public UseCase12TrainConsistMgmt() {
        this.bogies = new ArrayList<>();
    }

    public void addBogie(Bogie bogie) {
        bogies.add(bogie);
    }

    /**
     * Sorts bogies by their position (natural order).
     */
    public List<Bogie> sortByPosition() {
        List<Bogie> sorted = new ArrayList<>(bogies);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Sorts bogies by seating capacity (ascending).
     */
    public List<Bogie> sortByCapacityAsc() {
        List<Bogie> sorted = new ArrayList<>(bogies);
        sorted.sort(Comparator.comparingInt(Bogie::getCapacity));
        return sorted;
    }

    /**
     * Sorts bogies by seating capacity (descending).
     */
    public List<Bogie> sortByCapacityDesc() {
        List<Bogie> sorted = new ArrayList<>(bogies);
        sorted.sort(Comparator.comparingInt(Bogie::getCapacity).reversed());
        return sorted;
    }

    /**
     * Sorts bogies alphabetically by type.
     */
    public List<Bogie> sortByType() {
        List<Bogie> sorted = new ArrayList<>(bogies);
        sorted.sort(Comparator.comparing(Bogie::getType));
        return sorted;
    }

    /**
     * Displays a list of bogies.
     */
    public void displayBogies(List<Bogie> list, String label) {
        System.out.println(label + ":");
        list.forEach(b -> System.out.println("  " + b));
    }

    public static void main(String[] args) {
        UseCase12TrainConsistMgmt mgmt = new UseCase12TrainConsistMgmt();

        mgmt.addBogie(new Bogie("General", 90, 4));
        mgmt.addBogie(new Bogie("Sleeper", 72, 2));
        mgmt.addBogie(new Bogie("AC Chair Car", 60, 3));
        mgmt.addBogie(new Bogie("First Class AC", 30, 1));
        mgmt.addBogie(new Bogie("Pantry Car", 0, 5));

        mgmt.displayBogies(mgmt.sortByPosition(), "Sorted by Position");
        mgmt.displayBogies(mgmt.sortByCapacityAsc(), "Sorted by Capacity (Ascending)");
        mgmt.displayBogies(mgmt.sortByCapacityDesc(), "Sorted by Capacity (Descending)");
        mgmt.displayBogies(mgmt.sortByType(), "Sorted by Type (Alphabetical)");
    }
}
