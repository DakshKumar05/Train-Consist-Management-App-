package test;

import main.UseCase20TrainConsistMgmt;
import main.UseCase20TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC20: Advanced Streams & Analytics
 */
public class UseCase20TrainConsistMgmtTest {

    @Test
    public void testGroupingAndStatistics() {
        List<Bogie> fleet = Arrays.asList(
            new Bogie("Sleeper", 70, false),
            new Bogie("Sleeper", 70, false),
            new Bogie("AC", 60, true)
        );

        // Verify Grouping
        Map<String, Long> groups = fleet.stream()
                .collect(Collectors.groupingBy(Bogie::getType, Collectors.counting()));
        assertEquals(2L, groups.get("Sleeper"));
        assertEquals(1L, groups.get("AC"));

        // Verify Partitioning
        Map<Boolean, List<Bogie>> partitioned = fleet.stream()
                .collect(Collectors.partitioningBy(Bogie::isUnderMaintenance));
        assertEquals(2, partitioned.get(false).size());
        assertEquals(1, partitioned.get(true).size());

        // Verify Statistics
        IntSummaryStatistics stats = fleet.stream()
                .mapToInt(Bogie::getCapacity)
                .summaryStatistics();
        assertEquals(200, stats.getSum());
        assertEquals(70, stats.getMax());
        assertEquals(60, stats.getMin());
    }
}
