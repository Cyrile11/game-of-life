package net.diegolemos;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.*;

/**
 * @author: Diego Lemos
 * @since: 12/7/12
 */
public class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null || !(object instanceof Cell)) {
            return false;
        }

        Cell candidate = (Cell) object;
        return candidate == this || equal(x, candidate.x) && equal(y, candidate.y);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
