package test;

import main.UseCase13TrainConsistMgmt;
import main.UseCase13TrainConsistMgmt.InvalidBogieException;
import main.UseCase13TrainConsistMgmt.ConsistCapacityExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC13: Exception Handling
 */
public class UseCase13TrainConsistMgmtTest {

    private UseCase13TrainConsistMgmt consist;

    @BeforeEach
    public void setUp() {
        consist = new UseCase13TrainConsistMgmt(3);
    }

    @Test
    public void testAddBogie_success() throws InvalidBogieException {
        consist.addBogie("Sleeper");
        assertEquals(1, consist.getBogieCount());
    }

    @Test
    public void testAddBogie_nullThrowsInvalidBogieException() {
        assertThrows(InvalidBogieException.class, () -> consist.addBogie(null));
    }

    @Test
    public void testAddBogie_emptyStringThrowsInvalidBogieException() {
        assertThrows(InvalidBogieException.class, () -> consist.addBogie(""));
    }

    @Test
    public void testAddBogie_exceedsCapacityThrowsRuntimeException() throws InvalidBogieException {
        consist.addBogie("Sleeper");
        consist.addBogie("General");
        consist.addBogie("AC Chair Car");
        assertTrue(consist.isFull());
        assertThrows(ConsistCapacityExceededException.class, () -> consist.addBogie("First Class"));
    }

    @Test
    public void testGetBogieAt_validIndex() throws InvalidBogieException {
        consist.addBogie("Sleeper");
        assertEquals("Sleeper", consist.getBogieAt(0));
    }

    @Test
    public void testGetBogieAt_invalidIndexThrowsInvalidBogieException() {
        assertThrows(InvalidBogieException.class, () -> consist.getBogieAt(5));
    }

    @Test
    public void testSafeRemoveBogie_found() throws InvalidBogieException {
        consist.addBogie("Sleeper");
        Optional<String> result = consist.safeRemoveBogie("Sleeper");
        assertTrue(result.isPresent());
        assertEquals("Sleeper", result.get());
        assertEquals(0, consist.getBogieCount());
    }

    @Test
    public void testSafeRemoveBogie_notFound() {
        Optional<String> result = consist.safeRemoveBogie("NonExistent");
        assertFalse(result.isPresent());
    }

    @Test
    public void testConstructor_invalidMaxBogiesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new UseCase13TrainConsistMgmt(0));
        assertThrows(IllegalArgumentException.class, () -> new UseCase13TrainConsistMgmt(-1));
    }

    @Test
    public void testIsFullFalse_whenNotFull() throws InvalidBogieException {
        consist.addBogie("Sleeper");
        assertFalse(consist.isFull());
    }

    @Test
    public void testIsFullTrue_whenFull() throws InvalidBogieException {
        consist.addBogie("A");
        consist.addBogie("B");
        consist.addBogie("C");
        assertTrue(consist.isFull());
    }
}
