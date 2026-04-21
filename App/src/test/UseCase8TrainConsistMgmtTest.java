package test;

import main.UseCase8TrainConsistMgmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC8: Train Consist Management
 */
public class UseCase8TrainConsistMgmtTest {

    private UseCase8TrainConsistMgmt consist;

    @BeforeEach
    public void setUp() {
        consist = new UseCase8TrainConsistMgmt();
    }

    @Test
    public void testAddBogie_success() {
        consist.addBogie("Sleeper");
        assertEquals(1, consist.getBogieCount());
        assertTrue(consist.getBogies().contains("Sleeper"));
    }

    @Test
    public void testAddMultipleBogies() {
        consist.addBogie("Sleeper");
        consist.addBogie("AC Chair Car");
        consist.addBogie("General");
        assertEquals(3, consist.getBogieCount());
    }

    @Test
    public void testRemoveBogie_success() {
        consist.addBogie("Sleeper");
        consist.addBogie("General");
        boolean result = consist.removeBogie("Sleeper");
        assertTrue(result);
        assertEquals(1, consist.getBogieCount());
        assertFalse(consist.getBogies().contains("Sleeper"));
    }

    @Test
    public void testRemoveBogie_notFound() {
        consist.addBogie("Sleeper");
        boolean result = consist.removeBogie("Luxury Suite");
        assertFalse(result);
        assertEquals(1, consist.getBogieCount());
    }

    @Test
    public void testAddBogie_nullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> consist.addBogie(null));
    }

    @Test
    public void testAddBogie_emptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> consist.addBogie("   "));
    }

    @Test
    public void testGetBogies_returnsDefensiveCopy() {
        consist.addBogie("Sleeper");
        consist.getBogies().add("Tampered");
        // Original list should not be modified
        assertEquals(1, consist.getBogieCount());
    }

    @Test
    public void testInitialConsistIsEmpty() {
        assertEquals(0, consist.getBogieCount());
        assertTrue(consist.getBogies().isEmpty());
    }
}
