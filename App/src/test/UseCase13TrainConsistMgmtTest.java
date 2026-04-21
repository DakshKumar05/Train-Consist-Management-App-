package test;

import main.UseCase13TrainConsistMgmt;
import main.UseCase13TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC13: Performance Comparison
 */
public class UseCase13TrainConsistMgmtTest {

    private UseCase13TrainConsistMgmt benchmark;
    private List<Bogie> testData;

    @BeforeEach
    public void setUp() {
        benchmark = new UseCase13TrainConsistMgmt();
        testData = new ArrayList<>();
        testData.add(new Bogie(72));
        testData.add(new Bogie(60));
        testData.add(new Bogie(24));
    }

    @Test
    public void testSumWithLoop_correctSum() {
        assertEquals(156, benchmark.sumWithLoop(testData));
    }

    @Test
    public void testSumWithStream_correctSum() {
        assertEquals(156, benchmark.sumWithStream(testData));
    }

    @Test
    public void testSumWithParallelStream_correctSum() {
        assertEquals(156, benchmark.sumWithParallelStream(testData));
    }

    @Test
    public void testEmptyListHandling() {
        List<Bogie> empty = new ArrayList<>();
        assertEquals(0, benchmark.sumWithLoop(empty));
        assertEquals(0, benchmark.sumWithStream(empty));
        assertEquals(0, benchmark.sumWithParallelStream(empty));
    }

    @Test
    public void testLargeDatasetConsistency() {
        List<Bogie> large = new ArrayList<>();
        for (int i = 0; i < 1000; i++) large.add(new Bogie(10));
        
        long loopResult = benchmark.sumWithLoop(large);
        long streamResult = benchmark.sumWithStream(large);
        long parallelResult = benchmark.sumWithParallelStream(large);
        
        assertEquals(10000, loopResult);
        assertEquals(loopResult, streamResult);
        assertEquals(loopResult, parallelResult);
    }
}
