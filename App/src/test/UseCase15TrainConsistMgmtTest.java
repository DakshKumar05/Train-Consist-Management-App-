package test;

import main.UseCase15TrainConsistMgmt;
import main.UseCase15TrainConsistMgmt.CargoAssignmentLogger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC15: Safe Cargo Assignment Using try-with-resources
 */
public class UseCase15TrainConsistMgmtTest {

    @Test
    public void testTryWithResources_closesAutomatically() {
        CargoAssignmentLogger externalRef = null;
        
        try (CargoAssignmentLogger logger = new CargoAssignmentLogger()) {
            externalRef = logger;
            assertTrue(logger.isOpen(), "Logger should be open inside try block");
            logger.logAssignment("T1", "Cargo");
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
        
        assertNotNull(externalRef);
        assertFalse(externalRef.isOpen(), "Logger should be closed after try-with-resources block");
    }

    @Test
    public void testLogger_throwsIfUsedAfterClose() {
        CargoAssignmentLogger logger = new CargoAssignmentLogger();
        logger.close();
        assertFalse(logger.isOpen());
        assertThrows(IllegalStateException.class, () -> logger.logAssignment("T1", "Cargo"));
    }
}
