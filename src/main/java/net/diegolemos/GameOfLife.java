package net.diegolemos;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author: Diego Lemos
 * @since: 12/7/12
 */
public class GameOfLife {

    private List<Cell> liveCells;

    public GameOfLife(List<Cell> cells) {
        this.liveCells = cells;
    }

    public void nextGeneration() {
        List<Cell> survivors = newArrayList();

        for(Cell cell : liveCells) {
            List<Cell> neighbours = getNeighbours(cell);

            if(hasMoreThanTwoNeighbours(neighbours)
                    && hasLessThanOrThreeNeighbours(neighbours)) {
                survivors.add(cell);
            }
        }

        Set<Cell> deadCells = getDeadCells();

        for(Cell deadCell : deadCells) {
            List<Cell> neighbours = getNeighbours(deadCell);

            if(hasThreeNeighbours(neighbours)) {
                survivors.add(deadCell);
            }
        }

        liveCells = survivors;
    }

    private boolean hasThreeNeighbours(List<Cell> neighbours) {
        return neighbours.size() == 3;
    }

    private Set<Cell> getDeadCells() {
        Set<Cell> deadCells = newHashSet();

        for(Cell liveCell : liveCells) {
            for(int x = liveCell.getX() - 1; x <= liveCell.getX() + 1; x++) {
                for(int y = liveCell.getY() - 1; y <= liveCell.getY() + 1; y++) {
                    deadCells.add(new Cell(x, y));
                }
            }
        }

        return newHashSet(Iterables.filter(deadCells, new Predicate<Cell>() {
            @Override
            public boolean apply(Cell deadCell) {
                return !liveCells.contains(deadCell);  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));
    }

    private boolean hasLessThanOrThreeNeighbours(List<Cell> neighbours) {
        return neighbours.size() <= 3;
    }

    private boolean hasMoreThanTwoNeighbours(List<Cell> neighbours) {
        return neighbours.size() >= 2;
    }

    private List<Cell> getNeighbours(final Cell cell) {
        Iterable<Cell> neighbours = Iterables.filter(liveCells, new Predicate<Cell>() {
            Range xNeighbourhood = Ranges.closed(cell.getX() - 1, cell.getX() + 1);
            Range yNeighbourhood = Ranges.closed(cell.getY() - 1, cell.getY() + 1);

            @Override
            public boolean apply(Cell neighbour) {
                return !neighbour.equals(cell) &&
                    xNeighbourhood.contains(neighbour.getX()) &&
                    yNeighbourhood.contains(neighbour.getY());
            }
        });

        return newArrayList(neighbours);
    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }
}
