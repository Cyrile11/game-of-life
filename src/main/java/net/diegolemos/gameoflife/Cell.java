package net.diegolemos.gameoflife;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null || !(object instanceof Cell)) {
            return false;
        }

        else {
            Cell candidate = (Cell) object;
            return (candidate == this) || (x == candidate.x && y == candidate.y);
        }
    }

    @Override
    public int hashCode() {
        return x * 3 + y * 5;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
