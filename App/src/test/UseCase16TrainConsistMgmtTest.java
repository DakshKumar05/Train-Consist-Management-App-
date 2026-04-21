package test;

import main.UseCase16TrainConsistMgmt;
import main.UseCase16TrainConsistMgmt.RollingStock;
import main.UseCase16TrainConsistMgmt.Locomotive;
import main.UseCase16TrainConsistMgmt.Bogie;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UC16: Inheritance & Polymorphism
 */
public class UseCase16TrainConsistMgmtTest {

    @Test
    public void testLocomotiveDescription() {
        Locomotive l = new Locomotive("L1", 100.0, 4000.0);
        assertTrue(l.getDescription().contains("Locomotive"));
        assertTrue(l.getDescription().contains("4000.0"));
    }

    @Test
    public void testBogieDescription() {
        Bogie b = new Bogie("B1", 40.0, 60);
        assertTrue(b.getDescription().contains("Bogie"));
        assertTrue(b.getDescription().contains("60"));
    }

    @Test
    public void testPolymorphicBehavior() {
        List<RollingStock> consist = new ArrayList<>();
        consist.add(new Locomotive("L1", 100.0, 4000.0));
        consist.add(new Bogie("B1", 40.0, 64));

        assertEquals(2, consist.size());
        assertTrue(consist.get(0) instanceof RollingStock);
        assertTrue(consist.get(1) instanceof RollingStock);

        // Verify different descriptions via base reference
        assertNotEquals(consist.get(0).getDescription(), consist.get(1).getDescription());
    }

    @Test
    public void testWeightInheritance() {
        RollingStock rs = new Locomotive("L1", 150.5, 5000.0);
        assertEquals(150.5, rs.getWeight());
    }
}
