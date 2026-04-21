package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UC19: File I/O (Consist Data Persistence)
 * 
 * Scenario:
 * Railway yards need to save the current train composition to a file 
 * before shifting duties. This use case demonstrates saving and 
 * loading data using BufferedReader and BufferedWriter.
 */
public class UseCase19TrainConsistMgmt {

    public static class Bogie {
        private String id;
        private int capacity;

        public Bogie(String id, int capacity) {
            this.id = id;
            this.capacity = capacity;
        }

        @Override
        public String toString() {
            return id + "," + capacity;
        }

        public static Bogie fromString(String line) {
            String[] parts = line.split(",");
            return new Bogie(parts[0], Integer.parseInt(parts[1]));
        }

        public String getId() { return id; }
        public int getCapacity() { return capacity; }
    }

    /**
     * Saves the list of bogies to a file.
     */
    public void saveConsistToFile(List<Bogie> consist, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Bogie b : consist) {
                writer.write(b.toString());
                writer.newLine();
            }
            System.out.println("Consist saved to " + filename);
        }
    }

    /**
     * Loads the list of bogies from a file.
     */
    public List<Bogie> loadConsistFromFile(String filename) throws IOException {
        List<Bogie> consist = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    consist.add(Bogie.fromString(line));
                }
            }
            System.out.println("Consist loaded from " + filename);
        }
        return consist;
    }

    public static void main(String[] args) {
        UseCase19TrainConsistMgmt manager = new UseCase19TrainConsistMgmt();
        String filename = "train_consist.txt";

        List<Bogie> originalConsist = new ArrayList<>();
        originalConsist.add(new Bogie("B001", 72));
        originalConsist.add(new Bogie("B002", 64));

        try {
            manager.saveConsistToFile(originalConsist, filename);
            List<Bogie> loadedConsist = manager.loadConsistFromFile(filename);
            
            System.out.println("Loaded Bogies:");
            for (Bogie b : loadedConsist) {
                System.out.println(" - " + b.getId() + " (Capacity: " + b.getCapacity() + ")");
            }
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }
}
