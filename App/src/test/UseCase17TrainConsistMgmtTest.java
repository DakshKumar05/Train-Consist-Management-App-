package test;

import main.UseCase17TrainConsistMgmt;
import main.UseCase17TrainConsistMgmt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC17: Interfaces & Abstract Classes
 */
public class UseCase17TrainConsistMgmtTest {

    private UseCase17TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        mgmt = new UseCase17TrainConsistMgmt();
    }

    @Test
    public void testPassengerBogie_passesInspectionWithinAge() {
        PassengerBogie b = new PassengerBogie("P001", "Sleeper", 10);
        mgmt.addBogie(b);
        assertTrue(b.inspect());
    }

    @Test
    public void testPassengerBogie_failsInspectionOverAge() {
        PassengerBogie b = new PassengerBogie("P002", "AC Chair Car", 30);
        mgmt.addBogie(b);
        assertFalse(b.inspect());
    }

    @Test
    public void testFreightBogie_failsInspectionOverAge() {
        FreightBogie f = new FreightBogie("F001", "Tank", 25);
        assertFalse(f.inspect());
    }

    @Test
    public void testScheduleMaintenance_setsUnderMaintenance() {
        PassengerBogie b = new PassengerBogie("P001", "Sleeper", 5);
        b.scheduleMaintenance("2024-06-01");
        assertTrue(b.isUnderMaintenance());
        assertFalse(b.inspect()); // Under maintenance fails inspection
    }

    @Test
    public void testCompleteMaintenance_clearsMaintenanceFlag() {
        PassengerBogie b = new PassengerBogie("P001", "Sleeper", 5);
        b.scheduleMaintenance("2024-06-01");
        b.completeMaintenance();
        assertFalse(b.isUnderMaintenance());
        assertTrue(b.inspect());
    }

    @Test
    public void testGetMaintenanceSchedule() {
        PassengerBogie b = new PassengerBogie("P001", "Sleeper", 5);
        b.scheduleMaintenance("2024-07-15");
        assertTrue(b.getMaintenanceSchedule().contains("2024-07-15"));
    }

    @Test
    public void testGetInspectionReport_containsRelevantInfo() {
        PassengerBogie b = new PassengerBogie("P010", "General", 10);
        String report = b.getInspectionReport();
        assertTrue(report.contains("P010"));
        assertTrue(report.contains("10"));
    }

    @Test
    public void testCountPassingInspection() {
        mgmt.addBogie(new PassengerBogie("P001", "Sleeper", 10));
        mgmt.addBogie(new PassengerBogie("P002", "AC", 28)); // Fails - over age
        mgmt.addBogie(new FreightBogie("F001", "Goods", 15));
        assertEquals(2, mgmt.countPassingInspection());
    }

    @Test
    public void testGetBogiesUnderMaintenance() {
        PassengerBogie b1 = new PassengerBogie("P001", "Sleeper", 5);
        PassengerBogie b2 = new PassengerBogie("P002", "General", 5);
        b1.scheduleMaintenance("2024-08-01");
        mgmt.addBogie(b1);
        mgmt.addBogie(b2);
        List<AbstractBogie> underMaint = mgmt.getBogiesUnderMaintenance();
        assertEquals(1, underMaint.size());
        assertEquals("P001", underMaint.get(0).getBogieId());
    }

    @Test
    public void testNullBogieIdThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new PassengerBogie(null, "Sleeper", 5));
    }

    @Test
    public void testMaxAgeYears_passenger() {
        PassengerBogie b = new PassengerBogie("P001", "Sleeper", 5);
        assertEquals(25, b.getMaxAgeYears());
    }

    @Test
    public void testMaxAgeYears_freight() {
        FreightBogie f = new FreightBogie("F001", "Goods", 5);
        assertEquals(20, f.getMaxAgeYears());
    }
}
