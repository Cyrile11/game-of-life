package net.diegolemos;

import com.google.common.testing.EqualsTester;
import org.junit.Test;

/**
 * @author: Diego Lemos
 * @since: 12/7/12
 */
public class CellTest {

    @Test
    public void testEquality() {
        new EqualsTester()
                .addEqualityGroup(new Cell(1,1), new Cell(1,1))
                .addEqualityGroup(new Cell(2, 2))
                .testEquals();
    }
}
