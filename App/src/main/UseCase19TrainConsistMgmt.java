package main;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UC19: Train Consist Management - File I/O
 * Demonstrates reading and writing train consist data to/from text files
 * using Java File I/O (BufferedReader, BufferedWriter, NIO Paths).
 */
public class UseCase19TrainConsistMgmt {

    public static class Bogie {
        private String id;
        private String type;
        private int capacity;

        public Bogie(String id, String type, int capacity) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Bogie ID cannot be null or empty.");
            }
            if (capacity < 0) {
                throw new IllegalArgumentException("Capacity cannot be negative.");
            }
            this.id = id;
            this.type = type;
            this.capacity = capacity;
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public int getCapacity() { return capacity; }

        /**
         * Serializes bogie to a CSV line: id,type,capacity
         */
        public String toCsvLine() {
            return id + "," + type + "," + capacity;
        }

        /**
         * Deserializes a bogie from a CSV line.
         */
        public static Bogie fromCsvLine(String csvLine) {
            if (csvLine == null || csvLine.trim().isEmpty()) {
                throw new IllegalArgumentException("CSV line cannot be null or empty.");
            }
            String[] parts = csvLine.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid CSV format. Expected: id,type,capacity");
            }
            return new Bogie(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()));
        }

        @Override
        public String toString() {
            return String.format("Bogie{id='%s', type='%s', capacity=%d}", id, type, capacity);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Bogie)) return false;
            Bogie b = (Bogie) o;
            return capacity == b.capacity && id.equals(b.id) && type.equals(b.type);
        }

        @Override
        public int hashCode() { return id.hashCode(); }
    }

    /**
     * Writes a list of bogies to a file in CSV format.
     * @param bogies  list of bogies to write
     * @param filePath path of the output file
     * @throws IOException if write fails
     */
    public void writeConsistToFile(List<Bogie> bogies, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,type,capacity");
            writer.newLine();
            for (Bogie b : bogies) {
                writer.write(b.toCsvLine());
                writer.newLine();
            }
        }
        System.out.println("Consist written to: " + filePath + " (" + bogies.size() + " bogies)");
    }

    /**
     * Reads bogies from a CSV file (skips header).
     * @param filePath path of the input file
     * @return list of bogies read
     * @throws IOException if read fails
     */
    public List<Bogie> readConsistFromFile(String filePath) throws IOException {
        List<Bogie> bogies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; } // skip header
                if (!line.trim().isEmpty()) {
                    bogies.add(Bogie.fromCsvLine(line));
                }
            }
        }
        System.out.println("Read " + bogies.size() + " bogies from: " + filePath);
        return bogies;
    }

    /**
     * Appends a single bogie to an existing file.
     * @param bogie    bogie to append
     * @param filePath path of the file
     * @throws IOException if append fails
     */
    public void appendBogie(Bogie bogie, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(bogie.toCsvLine());
            writer.newLine();
        }
        System.out.println("Appended bogie to: " + filePath);
    }

    /**
     * Checks whether a file exists at the given path.
     */
    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Deletes the file at the given path.
     * @return true if deleted, false if not found
     */
    public boolean deleteFile(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(filePath));
    }

    public static void main(String[] args) throws IOException {
        UseCase19TrainConsistMgmt mgmt = new UseCase19TrainConsistMgmt();
        String filePath = "train_consist.csv";

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper", 72));
        bogies.add(new Bogie("B002", "AC Chair Car", 60));
        bogies.add(new Bogie("B003", "General", 90));

        // Write to file
        mgmt.writeConsistToFile(bogies, filePath);

        // Read back
        List<Bogie> readBack = mgmt.readConsistFromFile(filePath);
        readBack.forEach(b -> System.out.println("  Read: " + b));

        // Append a new bogie
        mgmt.appendBogie(new Bogie("B004", "First Class AC", 30), filePath);

        // Read again after append
        List<Bogie> afterAppend = mgmt.readConsistFromFile(filePath);
        System.out.println("Total after append: " + afterAppend.size());

        // Cleanup
        mgmt.deleteFile(filePath);
        System.out.println("File deleted: " + !mgmt.fileExists(filePath));
    }
}
