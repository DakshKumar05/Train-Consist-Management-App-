package test;

import main.UseCase9TrainConsistMgmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC9: Bogie Queue Management
 */
public class UseCase9TrainConsistMgmtTest {

    private UseCase9TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        mgmt = new UseCase9TrainConsistMgmt();
    }

    @Test
    public void testEnqueueBogie() {
        mgmt.enqueueBogie("Sleeper");
        assertEquals(1, mgmt.getQueueSize());
    }

    @Test
    public void testDequeueBogie_fifoOrder() {
        mgmt.enqueueBogie("Sleeper");
        mgmt.enqueueBogie("AC Chair Car");
        assertEquals("Sleeper", mgmt.dequeueBogie());
        assertEquals("AC Chair Car", mgmt.dequeueBogie());
    }

    @Test
    public void testDequeueBogie_emptyQueue() {
        assertNull(mgmt.dequeueBogie());
    }

    @Test
    public void testPeekBogie() {
        mgmt.enqueueBogie("General");
        mgmt.enqueueBogie("Sleeper");
        assertEquals("General", mgmt.peekBogie());
        assertEquals(2, mgmt.getQueueSize()); // Peek should not remove
    }

    @Test
    public void testPeekBogie_emptyQueue() {
        assertNull(mgmt.peekBogie());
    }

    @Test
    public void testIsQueueEmpty_initiallyEmpty() {
        assertTrue(mgmt.isQueueEmpty());
    }

    @Test
    public void testIsQueueEmpty_afterEnqueue() {
        mgmt.enqueueBogie("Pantry Car");
        assertFalse(mgmt.isQueueEmpty());
    }

    @Test
    public void testEnqueueNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> mgmt.enqueueBogie(null));
    }

    @Test
    public void testEnqueueEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> mgmt.enqueueBogie(""));
    }

    @Test
    public void testQueueSizeAfterDequeue() {
        mgmt.enqueueBogie("Sleeper");
        mgmt.enqueueBogie("General");
        mgmt.dequeueBogie();
        assertEquals(1, mgmt.getQueueSize());
    }
}
