package net.diegolemos.gameoflife;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.*;

/**
 * @author: Diego Lemos
 * @since: 12/8/12
 */
public class World {

    private Set<Cell> population;
    private Map<Cell, Integer> allCellsAndNeighboursCount;

    public World(Cell... liveCells) {
        assert liveCells != null : "The live cells set cannot be null.";
        populateTheWorld(newHashSet(liveCells));
    }

    private void populateTheWorld(Set<Cell> liveCells) {
        population = newHashSet(liveCells);
        countNeighboursForAllCells();
    }

    private void countNeighboursForAllCells() {
        allCellsAndNeighboursCount = newHashMap();

        for(Cell cell : population) {
            Set<Cell> neighbours = getNeighbours(cell);

            for(Cell neighbour : neighbours) {
                Integer numberOfNeighbours = allCellsAndNeighboursCount.get(neighbour);

                if(numberOfNeighbours != null) {
                    allCellsAndNeighboursCount.put(neighbour, numberOfNeighbours + 1);
                } else {
                    allCellsAndNeighboursCount.put(neighbour, 1);
                }
            }
        }
    }

    @VisibleForTesting
    static Set<Cell> getNeighbours(Cell cell) {
        Set<Cell> neighbours = newHashSet();

        for(int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for(int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if(x == cell.getX() && y == cell.getY()) {
                    continue;
                }

                neighbours.add(new Cell(x, y));
            }
        }

        return neighbours;
    }

    public boolean nextGeneration() {
        Set<Cell> survivors = destroyCellsWithLessThanTwoAndMoreThanThreeNeighbours();
        Set<Cell> born = createCellsWithThreeNeighbours();
        Set<Cell> newGeneration = union(survivors, born);

        boolean isNewGeneration = !equal(newGeneration, population);

        if(isNewGeneration) {
            populateTheWorld(newGeneration);
        }

        return isNewGeneration;
    }

    @VisibleForTesting
    Set<Cell> destroyCellsWithLessThanTwoAndMoreThanThreeNeighbours() {
        return filter(getLiveAndDeadCells(), new Predicate<Cell>() {
            @Override
            public boolean apply(Cell cell) {
                return isAlive(cell) && hasTwoOrThreeNeighbours(cell);
            }

            private boolean hasTwoOrThreeNeighbours(Cell cell) {
                return countNeighbours(cell) == 2 || countNeighbours(cell) == 3;
            }
        });
    }

    @VisibleForTesting
    Integer countNeighbours(Cell cell) {
        return allCellsAndNeighboursCount.get(cell);
    }

    public boolean isAlive(Cell cell) {
        return population.contains(cell);
    }

    @VisibleForTesting
    Set<Cell> getLiveAndDeadCells() {
        return allCellsAndNeighboursCount.keySet();
    }

    @VisibleForTesting
    Set<Cell> createCellsWithThreeNeighbours() {
        return filter(getLiveAndDeadCells(), new Predicate<Cell>() {
            @Override
            public boolean apply(Cell cell) {
                return !isAlive(cell) && countNeighbours(cell) == 3;
            }
        });
    }

    public Set<Cell> getPopulation() {
        return population;
    }
}