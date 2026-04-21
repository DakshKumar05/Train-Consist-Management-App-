package test;

import main.UseCase19TrainConsistMgmt;
import main.UseCase19TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC19: File I/O
 */
public class UseCase19TrainConsistMgmtTest {

    @TempDir
    Path tempDir;

    @Test
    public void testSaveAndLoadConsist() throws IOException {
        UseCase19TrainConsistMgmt manager = new UseCase19TrainConsistMgmt();
        File tempFile = tempDir.resolve("test_consist.txt").toFile();

        List<Bogie> consist = new ArrayList<>();
        consist.add(new Bogie("B1", 72));
        consist.add(new Bogie("B2", 60));

        // Save
        manager.saveConsistToFile(consist, tempFile.getAbsolutePath());

        // Load
        List<Bogie> loaded = manager.loadConsistFromFile(tempFile.getAbsolutePath());

        assertEquals(2, loaded.size());
        assertEquals("B1", loaded.get(0).getId());
        assertEquals(72, loaded.get(0).getCapacity());
        assertEquals("B2", loaded.get(1).getId());
    }

    @Test
    public void testEmptyFileHandling() throws IOException {
        UseCase19TrainConsistMgmt manager = new UseCase19TrainConsistMgmt();
        File tempFile = tempDir.resolve("empty.txt").toFile();
        tempFile.createNewFile();

        List<Bogie> loaded = manager.loadConsistFromFile(tempFile.getAbsolutePath());
        assertTrue(loaded.isEmpty());
    }
}
