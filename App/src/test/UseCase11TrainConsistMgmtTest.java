package test;

import main.UseCase11TrainConsistMgmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC11: Bogie Type Lookup using HashMap
 */
public class UseCase11TrainConsistMgmtTest {

    private UseCase11TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        mgmt = new UseCase11TrainConsistMgmt();
    }

    @Test
    public void testRegisterBogie() {
        mgmt.registerBogie("B001", "Sleeper");
        assertEquals(1, mgmt.getRegistrySize());
        assertTrue(mgmt.isRegistered("B001"));
    }

    @Test
    public void testLookupBogie_found() {
        mgmt.registerBogie("B001", "AC Chair Car");
        Optional<String> result = mgmt.lookupBogie("B001");
        assertTrue(result.isPresent());
        assertEquals("AC Chair Car", result.get());
    }

    @Test
    public void testLookupBogie_notFound() {
        Optional<String> result = mgmt.lookupBogie("B999");
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateBogie_success() {
        mgmt.registerBogie("B001", "General");
        boolean updated = mgmt.updateBogie("B001", "First Class AC");
        assertTrue(updated);
        assertEquals("First Class AC", mgmt.lookupBogie("B001").get());
    }

    @Test
    public void testUpdateBogie_notFound() {
        boolean updated = mgmt.updateBogie("B999", "Sleeper");
        assertFalse(updated);
    }

    @Test
    public void testDeregisterBogie_success() {
        mgmt.registerBogie("B001", "Sleeper");
        boolean removed = mgmt.deregisterBogie("B001");
        assertTrue(removed);
        assertFalse(mgmt.isRegistered("B001"));
        assertEquals(0, mgmt.getRegistrySize());
    }

    @Test
    public void testDeregisterBogie_notFound() {
        boolean removed = mgmt.deregisterBogie("B999");
        assertFalse(removed);
    }

    @Test
    public void testRegisterBogie_nullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> mgmt.registerBogie(null, "Sleeper"));
    }

    @Test
    public void testRegisterBogie_nullTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> mgmt.registerBogie("B001", null));
    }

    @Test
    public void testRegisterBogie_overwritesExisting() {
        mgmt.registerBogie("B001", "General");
        mgmt.registerBogie("B001", "Sleeper");
        assertEquals(1, mgmt.getRegistrySize());
        assertEquals("Sleeper", mgmt.lookupBogie("B001").get());
    }
}
