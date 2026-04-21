package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UC14: Train Consist Management - Generics
 * Demonstrates the use of Java Generics to create a type-safe consist manager
 * that can manage any type of rolling stock (Bogie, Engine, etc.)
 */
public class UseCase14TrainConsistMgmt {

    // Generic Consist class that works with any rolling stock type
    public static class ConsistManager<T> {
        private List<T> items;
        private final String consistName;

        public ConsistManager(String consistName) {
            this.consistName = consistName;
            this.items = new ArrayList<>();
        }

        public void addItem(T item) {
            if (item == null) {
                throw new IllegalArgumentException("Item cannot be null.");
            }
            items.add(item);
        }

        public boolean removeItem(T item) {
            return items.remove(item);
        }

        public T getItem(int index) {
            if (index < 0 || index >= items.size()) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
            }
            return items.get(index);
        }

        public int getSize() { return items.size(); }
        public boolean isEmpty() { return items.isEmpty(); }
        public boolean contains(T item) { return items.contains(item); }
        public String getConsistName() { return consistName; }

        public List<T> getAllItems() {
            return new ArrayList<>(items);
        }

        public void display() {
            System.out.println("Consist [" + consistName + "] - " + items.size() + " items:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + items.get(i));
            }
        }
    }

    // Bogie type
    public static class Bogie {
        private final String type;
        private final int capacity;

        public Bogie(String type, int capacity) {
            this.type = type;
            this.capacity = capacity;
        }

        public String getType() { return type; }
        public int getCapacity() { return capacity; }

        @Override
        public String toString() {
            return "Bogie{" + type + ", cap=" + capacity + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Bogie)) return false;
            Bogie b = (Bogie) o;
            return capacity == b.capacity && type.equals(b.type);
        }

        @Override
        public int hashCode() { return type.hashCode() + capacity; }
    }

    // Engine type
    public static class Engine {
        private final String model;
        private final int horsepower;

        public Engine(String model, int horsepower) {
            this.model = model;
            this.horsepower = horsepower;
        }

        public String getModel() { return model; }
        public int getHorsepower() { return horsepower; }

        @Override
        public String toString() {
            return "Engine{" + model + ", hp=" + horsepower + "}";
        }
    }

    // Utility: generic method to find all items matching a name string
    public static <T> List<T> filterByString(List<T> items, String keyword) {
        return items.stream()
                .filter(item -> item.toString().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // Bogie consist
        ConsistManager<Bogie> bogieConsist = new ConsistManager<>("Express Train Bogies");
        bogieConsist.addItem(new Bogie("Sleeper", 72));
        bogieConsist.addItem(new Bogie("AC Chair Car", 60));
        bogieConsist.addItem(new Bogie("General", 90));
        bogieConsist.display();

        // Engine consist
        ConsistManager<Engine> engineConsist = new ConsistManager<>("Locomotive Pool");
        engineConsist.addItem(new Engine("WAP-7", 6120));
        engineConsist.addItem(new Engine("WAP-5", 5400));
        engineConsist.display();

        // Generic filter
        List<Bogie> sleepers = filterByString(bogieConsist.getAllItems(), "sleeper");
        System.out.println("Filtered (contains 'sleeper'): " + sleepers);
    }
}
