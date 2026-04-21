package test;

import main.UseCase18TrainConsistMgmt;
import main.UseCase18TrainConsistMgmt.MaintenanceTask;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC18: Multithreading
 */
public class UseCase18TrainConsistMgmtTest {

    @Test
    public void testParallelExecution() throws InterruptedException {
        String[] ids = {"T1", "T2", "T3"};
        List<Thread> threads = new ArrayList<>();
        List<MaintenanceTask> tasks = new ArrayList<>();

        for (String id : ids) {
            MaintenanceTask task = new MaintenanceTask(id);
            tasks.add(task);
            Thread t = new Thread(task);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        for (MaintenanceTask task : tasks) {
            assertTrue(task.isDone(), "Task should be finished after join");
        }
    }

    @Test
    public void testThreadInterruption() {
        MaintenanceTask task = new MaintenanceTask("INT-01");
        Thread t = new Thread(task);
        t.start();
        t.interrupt(); // Interrupt the thread
        
        // Wait for thread to respond
        try {
            t.join(100);
        } catch (InterruptedException e) {}
        
        // Even if interrupted, we handle it in catch
        // UseCase18 implementation just prints to syserr
    }
}
