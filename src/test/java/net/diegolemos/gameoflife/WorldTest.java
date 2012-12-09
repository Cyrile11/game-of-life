package net.diegolemos.gameoflife;

import net.diegolemos.gameoflife.Cell;
import net.diegolemos.gameoflife.World;
import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author: Diego Lemos
 * @since: 12/8/12
 */
public class WorldTest {

    private World world;

    @Test
    public void should_get_live_and_dead_neighbours() {
        Set<Cell> allNeighbours = world.getNeighbours(new Cell(1, 1));

        assertThat(allNeighbours).containsOnly(new Cell(0, 0), new Cell(1, 2), new Cell(1, 0), new Cell(2, 0),
                new Cell(0, 1), new Cell(0, 2), new Cell(2, 1), new Cell(2, 2));
    }

    @Test
    public void should_count_neighbours() {
        world = new World(new Cell(1, 1), new Cell(1, 2));

        assertThat(world.countNeighbours(new Cell(1, 1))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(1, 2))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(1, 3))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(2, 3))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(2, 2))).isEqualTo(2);
    }

    @Test
    public void any_live_cell_with_more_than_three_live_neighbours_dies_as_if_by_overcrowding() {
        world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2), new Cell(3, 2));

        Set<Cell> survivors = world.destroyCellsWithLessThanTwoAndMoreThanThreeNeighbours();

        assertThat(survivors).containsOnly(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3));
    }

    @Test
    public void any_live_cell_with_fewer_than_two_live_neighbours_dies_as_if_caused_by_underpopulation() {
        world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3));

        Set<Cell> survivors = world.destroyCellsWithLessThanTwoAndMoreThanThreeNeighbours();

        assertThat(survivors).containsOnly(new Cell(1, 2));
    }

    @Test
    public void any_live_cell_with_two_or_three_live_neighbours_lives_on_to_the_next_generation() {
        world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2), new Cell(3, 2));

        Set<Cell> survivors = world.destroyCellsWithLessThanTwoAndMoreThanThreeNeighbours();

        assertThat(survivors).containsOnly(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3));
    }

    @Test
    public void any_dead_cell_with_exactly_three_live_neighbours_becomes_a_live_cell() {
        world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2));

        Set<Cell> born = world.createCellsWithThreeNeighbours();

        assertThat(born).containsOnly(new Cell(2, 3), new Cell(2, 1), new Cell(0, 2));
    }

    @Test
    public void should_move_to_next_generation() {
        Cell[] blinder = {new Cell(3, 2), new Cell(3, 3), new Cell(3, 4)};
        world = new World(blinder);

        boolean isNewGeneration = world.nextGeneration();

        assertThat(isNewGeneration).isTrue();
        assertThat(world.getPopulation()).containsOnly(new Cell(2,3), new Cell(3,3), new Cell(4,3));
    }
}
