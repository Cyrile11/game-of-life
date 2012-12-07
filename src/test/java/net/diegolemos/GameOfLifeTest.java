package net.diegolemos;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author: Diego Lemos
 * @since: 12/7/12
 */
public class GameOfLifeTest {

    private GameOfLife gameOfLife;

    @Test
    public void any_live_cell_with_fewer_than_two_live_neighbours_dies() {
        gameOfLife = new GameOfLife(newArrayList(new Cell(1, 1), new Cell(2,1), new Cell(2,3), new Cell(3,3), new Cell(4, 3)));

        gameOfLife.nextGeneration();

        assertThat(gameOfLife.getLiveCells()).containsOnly(new Cell(3,3), new Cell(3,4), new Cell(1, 2));
    }

    @Test
    public void any_live_cell_with_more_than_three_live_neighbours_dies_as_if_by_overcrowding() {
        gameOfLife = new GameOfLife(newArrayList(new Cell(2,3), new Cell(3,3), new Cell(4, 3), new Cell(3, 4), new Cell(3, 2)));

        gameOfLife.nextGeneration();

        assertThat(gameOfLife.getLiveCells()).containsOnly(new Cell(2,3), new Cell(3,2), new Cell(4, 3), new Cell(3, 4), new Cell(2, 2), new Cell(2, 4), new Cell(4, 4), new Cell(2, 4), new Cell(4, 2));
    }

    @Test
    public void any_live_cell_with_two_or_three_live_neighbours_lives_on_to_the_next_generation() {
        gameOfLife = new GameOfLife(newArrayList(new Cell(0,0), new Cell(1,1), new Cell(2, 2), new Cell(1, 2), new Cell(0, 2), new Cell(1, 3)));

        gameOfLife.nextGeneration();

        assertThat(gameOfLife.getLiveCells()).containsOnly(new Cell(0,2), new Cell(1,3), new Cell(2,1), new Cell(0,3), new Cell(2,3), new Cell(2, 2));
    }

    @Test
    public void any_dead_cell_with_exactly_three_live_neighbours_becomes_a_live_cell() {
        gameOfLife = new GameOfLife(newArrayList(new Cell(0,2), new Cell(1, 3), new Cell(2, 2)));

        gameOfLife.nextGeneration();

        assertThat(gameOfLife.getLiveCells()).containsOnly(new Cell(1,2), new Cell(1,3));
    }
}
