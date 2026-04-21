package test;

import main.UseCase16TrainConsistMgmt;
import main.UseCase16TrainConsistMgmt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC16: Inheritance & Polymorphism
 */
public class UseCase16TrainConsistMgmtTest {

    private UseCase16TrainConsistMgmt train;

    @BeforeEach
    public void setUp() {
        train = new UseCase16TrainConsistMgmt();
        train.addStock(new Locomotive("L001", "CLW", 2015, 6120, "Electric"));
        train.addStock(new PassengerBogie("B001", "ICF", 2018, 72, "Sleeper"));
        train.addStock(new PassengerBogie("B002", "ICF", 2019, 60, "AC Chair Car"));
        train.addStock(new FreightBogie("F001", "RDSO", 2016, 60.5, "Coal"));
    }

    @Test
    public void testGetTotalWeight() {
        // Locomotive: 123 + PassengerBogie: 42 + PassengerBogie: 42 + FreightBogie: 28 = 235
        assertEquals(235.0, train.getTotalWeight(), 0.001);
    }

    @Test
    public void testGetPassengerCapacity() {
        // 72 + 60 = 132
        assertEquals(132, train.getPassengerCapacity());
    }

    @Test
    public void testCountByType_locomotive() {
        assertEquals(1, train.countByType(Locomotive.class));
    }

    @Test
    public void testCountByType_passengerBogie() {
        assertEquals(2, train.countByType(PassengerBogie.class));
    }

    @Test
    public void testCountByType_freightBogie() {
        assertEquals(1, train.countByType(FreightBogie.class));
    }

    @Test
    public void testPolymorphicDescription_passengerBogie() {
        PassengerBogie b = new PassengerBogie("B003", "ICF", 2021, 90, "General");
        assertTrue(b.getDescription().contains("General"));
        assertTrue(b.getDescription().contains("90"));
    }

    @Test
    public void testPolymorphicDescription_locomotive() {
        Locomotive l = new Locomotive("L002", "CLW", 2020, 5400, "Diesel");
        assertTrue(l.getDescription().contains("Diesel"));
        assertTrue(l.getDescription().contains("5400"));
    }

    @Test
    public void testNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new PassengerBogie(null, "ICF", 2020, 72, "Sleeper"));
    }

    @Test
    public void testGetConsistReturnsCopy() {
        train.getConsist().clear();
        assertEquals(4, train.getConsist().size());
    }

    @Test
    public void testPassengerBogieGetters() {
        PassengerBogie b = new PassengerBogie("B010", "ICF", 2022, 80, "AC");
        assertEquals("B010", b.getId());
        assertEquals("ICF", b.getManufacturer());
        assertEquals(2022, b.getYearBuilt());
        assertEquals(80, b.getSeatCapacity());
        assertEquals("AC", b.getClassType());
        assertEquals(42.0, b.getWeightTonnes(), 0.001);
    }

    @Test
    public void testFreightBogieGetters() {
        FreightBogie f = new FreightBogie("F010", "RDSO", 2018, 75.0, "Grain");
        assertEquals(75.0, f.getLoadCapacityTonnes(), 0.001);
        assertEquals("Grain", f.getCargoType());
        assertEquals(28.0, f.getWeightTonnes(), 0.001);
    }
}
