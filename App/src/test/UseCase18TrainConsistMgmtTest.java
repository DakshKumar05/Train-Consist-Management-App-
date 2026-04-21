package test;

import main.UseCase18TrainConsistMgmt;
import main.UseCase18TrainConsistMgmt.Bogie;
import main.UseCase18TrainConsistMgmt.BogieCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC18: Multithreading
 */
public class UseCase18TrainConsistMgmtTest {

    private UseCase18TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        mgmt = new UseCase18TrainConsistMgmt();
    }

    @Test
    public void testBogie_initialStatusIsPending() {
        Bogie b = new Bogie("B001", "Sleeper");
        assertEquals("PENDING", b.getStatus());
    }

    @Test
    public void testBogie_setStatusUpdates() {
        Bogie b = new Bogie("B001", "Sleeper");
        b.setStatus("DONE");
        assertEquals("DONE", b.getStatus());
    }

    @Test
    public void testBogie_nullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Bogie(null, "Sleeper"));
    }

    @Test
    public void testProcessInParallel_allBogiesStatusDone() throws InterruptedException {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper"));
        bogies.add(new Bogie("B002", "General"));
        bogies.add(new Bogie("B003", "AC Chair Car"));

        mgmt.processInParallel(bogies);

        for (Bogie b : bogies) {
            assertEquals("DONE", b.getStatus(), "Bogie " + b.getId() + " should be DONE");
        }
    }

    @Test
    public void testProcessInParallel_logsEntries() throws InterruptedException {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B001", "Sleeper"));
        bogies.add(new Bogie("B002", "General"));

        mgmt.processInParallel(bogies);

        // Each bogie generates 2 log entries (start + complete)
        int logSize = mgmt.getProcessLog().size();
        assertEquals(4, logSize);
    }

    @Test
    public void testSynchronizedLog_threadSafety() throws InterruptedException {
        int numThreads = 50;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final int idx = i;
            threads.add(new Thread(() -> mgmt.log("Entry " + idx)));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(numThreads, mgmt.getProcessLog().size());
    }

    @Test
    public void testBogieCounter_synchronizedIncrement() throws InterruptedException {
        BogieCounter counter = new BogieCounter();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(counter::increment));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(100, counter.getCount());
    }

    @Test
    public void testBogieCounter_initialCountIsZero() {
        BogieCounter counter = new BogieCounter();
        assertEquals(0, counter.getCount());
    }

    @Test
    public void testGetProcessLog_returnsDefensiveCopy() throws InterruptedException {
        List<Bogie> bogies = List.of(new Bogie("B001", "Sleeper"));
        mgmt.processInParallel(bogies);
        mgmt.getProcessLog().clear(); // Should not affect internal log
        assertEquals(2, mgmt.getProcessLog().size());
    }
}
