package test;

import main.UseCase10TrainConsistMgmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC10: Count Total Seats in Train (reduce)
 *
 * Validates:
 * - Stream aggregation using reduce()
 * - Capacity extraction using map()
 * - Multiple bogie aggregation
 * - Single bogie handling
 * - Empty collection handling
 * - Original collection integrity
 */
public class UseCase10TrainConsistMgmtTest {

    private List<UseCase10TrainConsistMgmt> bogies;

    @BeforeEach
    public void setUp() {
        bogies = new ArrayList<>();
        bogies.add(new UseCase10TrainConsistMgmt("Sleeper", 72));
        bogies.add(new UseCase10TrainConsistMgmt("AC Chair", 60));
        bogies.add(new UseCase10TrainConsistMgmt("First Class", 30));
    }

    @Test
    public void testReduce_TotalSeatCalculation() {
        // Verifies that the reduce operation correctly calculates the total seating capacity
        int totalSeats = bogies.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);
        assertEquals(162, totalSeats);
    }

    @Test
    public void testReduce_MultipleBogiesAggregation() {
        // Verifies that when multiple bogies exist, their capacities are correctly summed
        bogies.add(new UseCase10TrainConsistMgmt("General", 90));
        int totalSeats = bogies.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);
        assertEquals(252, totalSeats);
    }

    @Test
    public void testReduce_SingleBogieCapacity() {
        // Verifies behavior when only one bogie exists in the list
        List<UseCase10TrainConsistMgmt> singleBogie = new ArrayList<>();
        singleBogie.add(new UseCase10TrainConsistMgmt("Sleeper", 72));
        int totalSeats = singleBogie.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);
        assertEquals(72, totalSeats);
    }

    @Test
    public void testReduce_EmptyBogieList() {
        // Verifies that aggregation on an empty bogie list returns the identity value (0)
        List<UseCase10TrainConsistMgmt> emptyList = new ArrayList<>();
        int totalSeats = emptyList.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);
        assertEquals(0, totalSeats);
    }

    @Test
    public void testReduce_CorrectCapacityExtraction() {
        // Verifies that map() correctly extracts capacity values from Bogie objects
        List<Integer> capacities = bogies.stream()
                .map(b -> b.getCapacity())
                .collect(java.util.stream.Collectors.toList());
        assertEquals(3, capacities.size());
        assertEquals(72, capacities.get(0));
        assertEquals(60, capacities.get(1));
        assertEquals(30, capacities.get(2));
    }

    @Test
    public void testReduce_AllBogiesIncluded() {
        // Verifies that all bogies in the collection are included in the aggregation
        int expectedTotal = 0;
        for (UseCase10TrainConsistMgmt b : bogies) {
            expectedTotal += b.getCapacity();
        }
        int streamTotal = bogies.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);
        assertEquals(expectedTotal, streamTotal);
    }

    @Test
    public void testReduce_OriginalListUnchanged() {
        // Verifies that the original bogie list remains unchanged after stream aggregation
        int originalSize = bogies.size();
        String originalFirst = bogies.get(0).getType();

        // Perform aggregation
        bogies.stream()
                .map(b -> b.getCapacity())
                .reduce(0, Integer::sum);

        // Verify original list is unchanged
        assertEquals(originalSize, bogies.size());
        assertEquals(originalFirst, bogies.get(0).getType());
    }
}
