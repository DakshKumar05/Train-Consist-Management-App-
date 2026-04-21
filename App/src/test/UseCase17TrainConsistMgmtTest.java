package test;

import main.UseCase17TrainConsistMgmt;
import main.UseCase17TrainConsistMgmt.PassengerBogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC17: Interfaces & Abstract Classes
 */
public class UseCase17TrainConsistMgmtTest {

    private PassengerBogie bogie;

    @BeforeEach
    public void setUp() {
        bogie = new PassengerBogie("TEST-01");
    }

    @Test
    public void testIsMaintenanceRequired_initialTrue() {
        assertTrue(bogie.isMaintenanceRequired());
    }

    @Test
    public void testPerformMaintenance_updatesState() {
        bogie.performMaintenance();
        assertFalse(bogie.isMaintenanceRequired());
    }

    @Test
    public void testPerformInspection_updatesState() {
        assertFalse(bogie.getInspectionReport().contains("PASS"));
        bogie.performInspection();
        assertTrue(bogie.performInspection());
        assertTrue(bogie.getInspectionReport().contains("PASS"));
    }

    @Test
    public void testReportContent() {
        bogie.performInspection();
        String report = bogie.getInspectionReport();
        assertTrue(report.contains("TEST-01"));
        assertTrue(report.contains("PASS"));
    }
}
