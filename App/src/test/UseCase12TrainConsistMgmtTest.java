package test;

import main.UseCase12TrainConsistMgmt;
import main.UseCase12TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC12: Sorting Bogies
 */
public class UseCase12TrainConsistMgmtTest {

    private UseCase12TrainConsistMgmt mgmt;

    @BeforeEach
    public void setUp() {
        mgmt = new UseCase12TrainConsistMgmt();
        mgmt.addBogie(new Bogie("General", 90, 4));
        mgmt.addBogie(new Bogie("Sleeper", 72, 2));
        mgmt.addBogie(new Bogie("AC Chair Car", 60, 3));
        mgmt.addBogie(new Bogie("First Class AC", 30, 1));
        mgmt.addBogie(new Bogie("Pantry Car", 0, 5));
    }

    @Test
    public void testSortByPosition() {
        List<Bogie> sorted = mgmt.sortByPosition();
        assertEquals(1, sorted.get(0).getPosition());
        assertEquals(2, sorted.get(1).getPosition());
        assertEquals(5, sorted.get(4).getPosition());
    }

    @Test
    public void testSortByCapacityAsc() {
        List<Bogie> sorted = mgmt.sortByCapacityAsc();
        assertEquals(0, sorted.get(0).getCapacity());
        assertEquals(30, sorted.get(1).getCapacity());
        assertEquals(90, sorted.get(4).getCapacity());
    }

    @Test
    public void testSortByCapacityDesc() {
        List<Bogie> sorted = mgmt.sortByCapacityDesc();
        assertEquals(90, sorted.get(0).getCapacity());
        assertEquals(72, sorted.get(1).getCapacity());
        assertEquals(0, sorted.get(4).getCapacity());
    }

    @Test
    public void testSortByType_alphabeticalOrder() {
        List<Bogie> sorted = mgmt.sortByType();
        assertEquals("AC Chair Car", sorted.get(0).getType());
        assertEquals("First Class AC", sorted.get(1).getType());
        assertEquals("General", sorted.get(2).getType());
    }

    @Test
    public void testSortDoesNotMutateOriginal() {
        List<Bogie> sorted = mgmt.sortByCapacityAsc();
        // Force a change in sorted does not affect internal list
        sorted.clear();
        List<Bogie> again = mgmt.sortByCapacityAsc();
        assertEquals(5, again.size());
    }

    @Test
    public void testBogieNullTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Bogie(null, 60, 1));
    }

    @Test
    public void testBogieNegativeCapacityThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Bogie("General", -5, 1));
    }

    @Test
    public void testCompareTo() {
        Bogie b1 = new Bogie("Sleeper", 72, 1);
        Bogie b2 = new Bogie("General", 90, 3);
        assertTrue(b1.compareTo(b2) < 0);
        assertTrue(b2.compareTo(b1) > 0);
        assertEquals(0, b1.compareTo(new Bogie("Other", 50, 1)));
    }
}
