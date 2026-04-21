package test;

import main.UseCase14TrainConsistMgmt;
import main.UseCase14TrainConsistMgmt.InvalidBogieCapacityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC14: Handle Invalid Bogie Capacity
 */
public class UseCase14TrainConsistMgmtTest {

    private UseCase14TrainConsistMgmt bogie;

    @BeforeEach
    public void setUp() {
        bogie = new UseCase14TrainConsistMgmt();
    }

    @Test
    public void testSetCapacity_valid() throws InvalidBogieCapacityException {
        bogie.setCapacity(72);
        assertEquals(72, bogie.getCapacity());
    }

    @Test
    public void testSetCapacity_zeroThrowsException() {
        InvalidBogieCapacityException exception = assertThrows(
            InvalidBogieCapacityException.class, 
            () -> bogie.setCapacity(0)
        );
        assertTrue(exception.getMessage().contains("0"));
    }

    @Test
    public void testSetCapacity_negativeThrowsException() {
        InvalidBogieCapacityException exception = assertThrows(
            InvalidBogieCapacityException.class, 
            () -> bogie.setCapacity(-10)
        );
        assertTrue(exception.getMessage().contains("-10"));
    }

    @Test
    public void testGetCapacity_initiallyZero() {
        assertEquals(0, bogie.getCapacity());
    }
}
