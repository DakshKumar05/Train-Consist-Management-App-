package test;

import main.UseCase15TrainConsistMgmt;
import main.UseCase15TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC15: Lambda Expressions & Functional Interfaces
 */
public class UseCase15TrainConsistMgmtTest {

    private UseCase15TrainConsistMgmt consist;

    @BeforeEach
    public void setUp() {
        consist = new UseCase15TrainConsistMgmt();
        consist.addBogie(new Bogie("Sleeper", 72, "ACTIVE"));
        consist.addBogie(new Bogie("AC Chair Car", 60, "MAINTENANCE"));
        consist.addBogie(new Bogie("General", 90, "ACTIVE"));
        consist.addBogie(new Bogie("First Class AC", 30, "ACTIVE"));
    }

    @Test
    public void testFilterBogies_byActiveStatus() {
        List<Bogie> active = consist.filterBogies(b -> "ACTIVE".equals(b.getStatus()));
        assertEquals(3, active.size());
        active.forEach(b -> assertEquals("ACTIVE", b.getStatus()));
    }

    @Test
    public void testFilterBogies_byMaintenanceStatus() {
        List<Bogie> maintenance = consist.filterBogies(b -> "MAINTENANCE".equals(b.getStatus()));
        assertEquals(1, maintenance.size());
        assertEquals("AC Chair Car", maintenance.get(0).getType());
    }

    @Test
    public void testFilterBogies_byCapacity() {
        List<Bogie> highCap = consist.filterBogies(b -> b.getCapacity() >= 70);
        assertEquals(2, highCap.size());
    }

    @Test
    public void testFilterBogies_noMatch() {
        List<Bogie> result = consist.filterBogies(b -> b.getCapacity() > 200);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testApplyToAll_updatesStatus() {
        consist.applyToAll(b -> b.setStatus("INACTIVE"));
        consist.getBogies().forEach(b -> assertEquals("INACTIVE", b.getStatus()));
    }

    @Test
    public void testMapBogies_toSummaryString() {
        List<String> summaries = consist.mapBogies(b -> b.getType() + ":" + b.getCapacity());
        assertEquals(4, summaries.size());
        assertTrue(summaries.contains("Sleeper:72"));
        assertTrue(summaries.contains("General:90"));
    }

    @Test
    public void testCountByPredicate_active() {
        long count = consist.countByPredicate(b -> "ACTIVE".equals(b.getStatus()));
        assertEquals(3, count);
    }

    @Test
    public void testCountByPredicate_noneMatch() {
        long count = consist.countByPredicate(b -> b.getCapacity() > 1000);
        assertEquals(0, count);
    }

    @Test
    public void testGetBogiesReturnsCopy() {
        consist.getBogies().clear();
        assertEquals(4, consist.getBogies().size());
    }
}
