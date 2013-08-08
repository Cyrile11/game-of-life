package net.diegolemos.gameoflife;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.*;
import static java.util.Collections.addAll;

/**
 * @author: Diego Lemos
 * @since: 12/8/12
 */
public class World {

    private Set<Cell> population;
    private Map<Cell, Integer> allCellsAndNeighboursCount;
    private boolean isNewGeneration;

    public World(Cell[]... patterns) {
        Set<Cell> liveCells = newHashSet();

        for(Cell[] pattern : patterns) {
            addAll(liveCells, pattern);
        }

        populateTheWorldWith(liveCells);
    }

    public World(Cell... liveCells) {
        this(newHashSet(liveCells));
    }

    private World(Set<Cell> liveCells) {
        populateTheWorldWith(liveCells);
    }

    private void populateTheWorldWith(Set<Cell> liveCells) {
        population = liveCells;
        allCellsAndNeighboursCount = mapNeighbours(population);
    }

    private static Map<Cell, Integer> mapNeighbours(Set<Cell> liveCells) {
        Map<Cell, Integer> allCellsAndNeighboursCount = newHashMap();

        for(Cell cell : liveCells) {
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

        return allCellsAndNeighboursCount;
    }

    @VisibleForTesting
    static Set<Cell> getNeighbours(Cell cell) {
        Set<Cell> neighbours = newHashSet();

        for(int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for(int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                Cell neighbour = new Cell(x, y);

                if(!cell.equals(neighbour)) {
                    neighbours.add(neighbour);
                }
            }
        }

        return neighbours;
    }

    public World nextGeneration() {
        Set<Cell> survivors = from(population)
                .filter(hasTwoOrThreeNeighbours())
                .toImmutableSet();
        Set<Cell> born = from(getDeadCells())
                .filter(hasThreeNeighbours())
                .toImmutableSet();
        Set<Cell> newGeneration = union(survivors, born);

        isNewGeneration = !equal(newGeneration, population);

        if(isNewGeneration) {
            populateTheWorldWith(newGeneration);
        }

        return this;
    }

    private Predicate<Cell> hasTwoOrThreeNeighbours() {
        return new Predicate<Cell>() {
            @Override
            public boolean apply(Cell cell) {
                Integer numberOfNeighbours = countNeighbours(cell);
                return numberOfNeighbours == 2 || numberOfNeighbours == 3;
            }
        };
    }

    @VisibleForTesting
    Integer countNeighbours(Cell cell) {
        return allCellsAndNeighboursCount.get(cell);
    }

    private Set<Cell> getDeadCells() {
        return difference(allCellsAndNeighboursCount.keySet(), population);
    }

    private Predicate<Cell> hasThreeNeighbours() {
        return new Predicate<Cell>() {
            @Override
            public boolean apply(Cell cell) {
                return countNeighbours(cell) == 3;
            }
        };
    }

    public Set<Cell> getPopulation() {
        return population;
    }

    public boolean hasChanged() {
        return isNewGeneration;
    }
}