package test;

import main.UseCase20TrainConsistMgmt;
import main.UseCase20TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC20: Advanced Java Streams
 */
public class UseCase20TrainConsistMgmtTest {

    private UseCase20TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        List<Bogie> bogies = Arrays.asList(
            new Bogie("B001", "First Class AC",   "PREMIUM",  24,  3.5, "ACTIVE"),
            new Bogie("B002", "AC Chair Car",      "PREMIUM",  60,  2.5, "ACTIVE"),
            new Bogie("B003", "Sleeper",           "STANDARD", 72,  1.5, "ACTIVE"),
            new Bogie("B004", "AC 3-Tier",         "STANDARD", 64,  2.0, "MAINTENANCE"),
            new Bogie("B005", "General",           "ECONOMY",  90,  1.0, "ACTIVE"),
            new Bogie("B006", "Unreserved",        "ECONOMY",  100, 1.0, "RETIRED"),
            new Bogie("B007", "Pantry Car",        "STANDARD",  0,  1.0, "ACTIVE")
        );
        mgmt = new UseCase20TrainConsistMgmt(bogies);
    }

    @Test
    public void testGroupByCategory_correctGroupCount() {
        Map<String, List<Bogie>> grouped = mgmt.groupByCategory();
        assertEquals(3, grouped.size());
        assertTrue(grouped.containsKey("PREMIUM"));
        assertTrue(grouped.containsKey("STANDARD"));
        assertTrue(grouped.containsKey("ECONOMY"));
    }

    @Test
    public void testGroupByCategory_correctSizes() {
        Map<String, List<Bogie>> grouped = mgmt.groupByCategory();
        assertEquals(2, grouped.get("PREMIUM").size());
        assertEquals(3, grouped.get("STANDARD").size());
        assertEquals(2, grouped.get("ECONOMY").size());
    }

    @Test
    public void testCountByStatus() {
        Map<String, Long> counts = mgmt.countByStatus();
        assertEquals(5L, counts.get("ACTIVE"));
        assertEquals(1L, counts.get("MAINTENANCE"));
        assertEquals(1L, counts.get("RETIRED"));
    }

    @Test
    public void testPartitionByActive() {
        Map<Boolean, List<Bogie>> partitioned = mgmt.partitionByActive();
        assertEquals(5, partitioned.get(true).size());
        assertEquals(2, partitioned.get(false).size());
    }

    @Test
    public void testTotalActiveCapacity() {
        // ACTIVE: B001(24)+B002(60)+B003(72)+B005(90)+B007(0) = 246
        assertEquals(246, mgmt.totalActiveCapacity());
    }

    @Test
    public void testCapacityStatistics_min() {
        IntSummaryStatistics stats = mgmt.capacityStatistics();
        assertEquals(0, stats.getMin());
    }

    @Test
    public void testCapacityStatistics_max() {
        IntSummaryStatistics stats = mgmt.capacityStatistics();
        assertEquals(100, stats.getMax());
    }

    @Test
    public void testCapacityStatistics_count() {
        IntSummaryStatistics stats = mgmt.capacityStatistics();
        assertEquals(7, stats.getCount());
    }

    @Test
    public void testHighestCapacityBogie_present() {
        Optional<Bogie> result = mgmt.highestCapacityBogie();
        assertTrue(result.isPresent());
        assertEquals("B006", result.get().getId());
        assertEquals(100, result.get().getCapacity());
    }

    @Test
    public void testHighestCapacityBogie_emptyConsist() {
        UseCase20TrainConsistMgmt empty = new UseCase20TrainConsistMgmt(new ArrayList<>());
        assertFalse(empty.highestCapacityBogie().isPresent());
    }

    @Test
    public void testGetSortedTypesJoined() {
        String joined = mgmt.getSortedTypesJoined();
        // Should start with AC 3-Tier alphabetically
        assertTrue(joined.startsWith("AC 3-Tier"));
        assertTrue(joined.contains(", "));
    }

    @Test
    public void testTotalCapacityByCategory() {
        Map<String, Integer> totals = mgmt.totalCapacityByCategory();
        assertEquals(84, totals.get("PREMIUM"));   // 24 + 60
        assertEquals(136, totals.get("STANDARD")); // 72 + 64 + 0
        assertEquals(190, totals.get("ECONOMY"));  // 90 + 100
    }

    @Test
    public void testDistinctCategories() {
        List<String> categories = mgmt.distinctCategories();
        assertEquals(List.of("ECONOMY", "PREMIUM", "STANDARD"), categories);
    }

    @Test
    public void testAverageFareMultiplierActive() {
        // ACTIVE: 3.5, 2.5, 1.5, 1.0, 1.0 => avg = 9.5/5 = 1.9
        OptionalDouble avg = mgmt.averageFareMultiplierActive();
        assertTrue(avg.isPresent());
        assertEquals(1.9, avg.getAsDouble(), 0.001);
    }

    @Test
    public void testBogieNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Bogie(null, "Sleeper", "STANDARD", 72, 1.5, "ACTIVE"));
    }

    @Test
    public void testBogieNegativeCapacityThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Bogie("B001", "Sleeper", "STANDARD", -1, 1.5, "ACTIVE"));
    }

    @Test
    public void testGetBogiesReturnsCopy() {
        mgmt.getBogies().clear();
        assertEquals(7, mgmt.getBogies().size());
    }
}
