package test;

import main.UseCase19TrainConsistMgmt;
import main.UseCase19TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC19: File I/O
 */
public class UseCase19TrainConsistMgmtTest {

    private UseCase19TrainConsistMgmt mgmt;
    private static final String TEST_FILE = "test_consist_uc19.csv";

    @BeforeEach
    public void setUp() throws IOException {
        mgmt = new UseCase19TrainConsistMgmt();
        // Clean up any leftover test file
        mgmt.deleteFile(TEST_FILE);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mgmt.deleteFile(TEST_FILE);
    }

    @Test
    public void testWriteAndReadConsist() throws IOException {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper", 72));
        bogies.add(new Bogie("B002", "AC Chair Car", 60));

        mgmt.writeConsistToFile(bogies, TEST_FILE);
        List<Bogie> readBack = mgmt.readConsistFromFile(TEST_FILE);

        assertEquals(2, readBack.size());
        assertEquals(bogies.get(0), readBack.get(0));
        assertEquals(bogies.get(1), readBack.get(1));
    }

    @Test
    public void testWriteCreatesFile() throws IOException {
        mgmt.writeConsistToFile(new ArrayList<>(), TEST_FILE);
        assertTrue(mgmt.fileExists(TEST_FILE));
    }

    @Test
    public void testReadConsist_correctFields() throws IOException {
        List<Bogie> bogies = List.of(new Bogie("B001", "Sleeper", 72));
        mgmt.writeConsistToFile(bogies, TEST_FILE);
        List<Bogie> readBack = mgmt.readConsistFromFile(TEST_FILE);

        assertEquals("B001", readBack.get(0).getId());
        assertEquals("Sleeper", readBack.get(0).getType());
        assertEquals(72, readBack.get(0).getCapacity());
    }

    @Test
    public void testAppendBogie() throws IOException {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper", 72));
        mgmt.writeConsistToFile(bogies, TEST_FILE);

        mgmt.appendBogie(new Bogie("B002", "General", 90), TEST_FILE);
        List<Bogie> readBack = mgmt.readConsistFromFile(TEST_FILE);

        assertEquals(2, readBack.size());
        assertEquals("B002", readBack.get(1).getId());
    }

    @Test
    public void testDeleteFile() throws IOException {
        mgmt.writeConsistToFile(new ArrayList<>(), TEST_FILE);
        assertTrue(mgmt.fileExists(TEST_FILE));
        mgmt.deleteFile(TEST_FILE);
        assertFalse(mgmt.fileExists(TEST_FILE));
    }

    @Test
    public void testFileExists_nonExistentFile() {
        assertFalse(mgmt.fileExists("non_existent_file_xyz.csv"));
    }

    @Test
    public void testBogieFromCsvLine_valid() {
        Bogie b = Bogie.fromCsvLine("B001,Sleeper,72");
        assertEquals("B001", b.getId());
        assertEquals("Sleeper", b.getType());
        assertEquals(72, b.getCapacity());
    }

    @Test
    public void testBogieFromCsvLine_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Bogie.fromCsvLine("BadData"));
    }

    @Test
    public void testBogieFromCsvLine_nullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Bogie.fromCsvLine(null));
    }

    @Test
    public void testBogieNegativeCapacityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Bogie("B001", "Sleeper", -1));
    }

    @Test
    public void testBogieNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Bogie(null, "Sleeper", 72));
    }

    @Test
    public void testWriteEmptyConsist_readReturnsEmpty() throws IOException {
        mgmt.writeConsistToFile(new ArrayList<>(), TEST_FILE);
        List<Bogie> result = mgmt.readConsistFromFile(TEST_FILE);
        assertTrue(result.isEmpty());
    }
}
