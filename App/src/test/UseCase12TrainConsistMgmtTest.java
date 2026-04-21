package test;

import main.UseCase12TrainConsistMgmt;
import main.UseCase12TrainConsistMgmt.GoodsBogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC12: Safety Compliance Check for Goods Bogies
 */
public class UseCase12TrainConsistMgmtTest {

    private UseCase12TrainConsistMgmt manager;

    @BeforeEach
    public void setUp() {
        manager = new UseCase12TrainConsistMgmt();
    }

    @Test
    public void testIsSafe_generalCargoWithinLimit() {
        GoodsBogie b = new GoodsBogie("G001", "Coal", 75.0);
        assertTrue(b.isSafe());
    }

    @Test
    public void testIsSafe_generalCargoOverLimit() {
        GoodsBogie b = new GoodsBogie("G001", "Coal", 85.0);
        assertFalse(b.isSafe());
    }

    @Test
    public void testIsSafe_inflammableCargoWithinLimit() {
        GoodsBogie b = new GoodsBogie("G002", "Inflammable", 45.0);
        assertTrue(b.isSafe());
    }

    @Test
    public void testIsSafe_inflammableCargoOverLimit() {
        GoodsBogie b = new GoodsBogie("G002", "Inflammable", 55.0);
        assertFalse(b.isSafe());
    }

    @Test
    public void testGetUnsafeBogies() {
        manager.addBogie(new GoodsBogie("G1", "Coal", 70.0));
        manager.addBogie(new GoodsBogie("G2", "Coal", 90.0)); // Unsafe
        manager.addBogie(new GoodsBogie("G3", "Inflammable", 40.0));
        manager.addBogie(new GoodsBogie("G4", "Inflammable", 60.0)); // Unsafe

        List<GoodsBogie> unsafe = manager.getUnsafeBogies();
        assertEquals(2, unsafe.size());
        assertTrue(unsafe.stream().anyMatch(b -> b.getId().equals("G2")));
        assertTrue(unsafe.stream().anyMatch(b -> b.getId().equals("G4")));
    }

    @Test
    public void testIsWholeTrainSafe_true() {
        manager.addBogie(new GoodsBogie("G1", "Coal", 70.0));
        manager.addBogie(new GoodsBogie("G2", "Inflammable", 40.0));
        assertTrue(manager.isWholeTrainSafe());
    }

    @Test
    public void testIsWholeTrainSafe_false() {
        manager.addBogie(new GoodsBogie("G1", "Coal", 70.0));
        manager.addBogie(new GoodsBogie("G2", "Coal", 100.0)); // Unsafe
        assertFalse(manager.isWholeTrainSafe());
    }

    @Test
    public void testNullCargoTypeFailsSafety() {
        GoodsBogie b = new GoodsBogie("G005", null, 20.0);
        assertFalse(b.isSafe());
    }

    @Test
    public void testEmptyCargoTypeFailsSafety() {
        GoodsBogie b = new GoodsBogie("G005", "", 20.0);
        assertFalse(b.isSafe());
    }
}
