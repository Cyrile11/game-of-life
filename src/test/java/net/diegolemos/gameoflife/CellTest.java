package net.diegolemos.gameoflife;

import com.google.common.testing.EqualsTester;
import net.diegolemos.gameoflife.Cell;
import org.junit.Test;

/**
 * @author: Diego Lemos
 * @since: 12/7/12
 */
public class CellTest {

    @Test
    public void test_equality() {
        new EqualsTester()
                .addEqualityGroup(new Cell(1, 1), new Cell(1, 1))
                .addEqualityGroup(new Cell(2, 2))
                .testEquals();
    }
}
