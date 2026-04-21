package test;

import main.UseCase14TrainConsistMgmt;
import main.UseCase14TrainConsistMgmt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC14: Generics
 */
public class UseCase14TrainConsistMgmtTest {

    private ConsistManager<Bogie> bogieConsist;
    private ConsistManager<Engine> engineConsist;

    @BeforeEach
    public void setUp() {
        bogieConsist = new ConsistManager<>("TestConsist");
        engineConsist = new ConsistManager<>("TestEngines");
    }

    @Test
    public void testAddItem_bogie() {
        bogieConsist.addItem(new Bogie("Sleeper", 72));
        assertEquals(1, bogieConsist.getSize());
    }

    @Test
    public void testAddItem_engine() {
        engineConsist.addItem(new Engine("WAP-7", 6120));
        assertEquals(1, engineConsist.getSize());
    }

    @Test
    public void testAddItem_nullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bogieConsist.addItem(null));
    }

    @Test
    public void testRemoveItem_success() {
        Bogie b = new Bogie("Sleeper", 72);
        bogieConsist.addItem(b);
        assertTrue(bogieConsist.removeItem(b));
        assertEquals(0, bogieConsist.getSize());
    }

    @Test
    public void testRemoveItem_notFound() {
        assertFalse(bogieConsist.removeItem(new Bogie("NonExistent", 0)));
    }

    @Test
    public void testGetItem_validIndex() {
        bogieConsist.addItem(new Bogie("General", 90));
        Bogie result = bogieConsist.getItem(0);
        assertEquals("General", result.getType());
        assertEquals(90, result.getCapacity());
    }

    @Test
    public void testGetItem_invalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> bogieConsist.getItem(5));
    }

    @Test
    public void testContains() {
        Bogie b = new Bogie("Sleeper", 72);
        bogieConsist.addItem(b);
        assertTrue(bogieConsist.contains(b));
        assertFalse(bogieConsist.contains(new Bogie("General", 90)));
    }

    @Test
    public void testIsEmpty_initial() {
        assertTrue(bogieConsist.isEmpty());
    }

    @Test
    public void testIsEmpty_afterAdd() {
        bogieConsist.addItem(new Bogie("Sleeper", 72));
        assertFalse(bogieConsist.isEmpty());
    }

    @Test
    public void testFilterByString() {
        List<Bogie> items = List.of(
                new Bogie("Sleeper", 72),
                new Bogie("AC Chair Car", 60),
                new Bogie("General", 90)
        );
        List<Bogie> result = UseCase14TrainConsistMgmt.filterByString(items, "sleeper");
        assertEquals(1, result.size());
        assertEquals("Sleeper", result.get(0).getType());
    }

    @Test
    public void testGetAllItemsReturnsCopy() {
        bogieConsist.addItem(new Bogie("Sleeper", 72));
        bogieConsist.getAllItems().clear();
        assertEquals(1, bogieConsist.getSize());
    }
}
