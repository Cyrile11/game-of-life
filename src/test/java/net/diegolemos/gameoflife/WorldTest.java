package net.diegolemos.gameoflife;

import org.junit.Test;

import java.util.Set;

import static net.diegolemos.gameoflife.World.getNeighbours;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author: Diego Lemos
 * @since: 12/8/12
 */
public class WorldTest {

    @Test
    public void should_get_live_and_dead_neighbours() {
        Set<Cell> allNeighbours = getNeighbours(new Cell(1, 1));

        assertThat(allNeighbours).containsOnly(new Cell(0, 0), new Cell(1, 2), new Cell(1, 0), new Cell(2, 0),
                new Cell(0, 1), new Cell(0, 2), new Cell(2, 1), new Cell(2, 2));
    }

    @Test
    public void should_count_neighbours() {
        World world = new World(new Cell(1, 1), new Cell(1, 2));

        assertThat(world.countNeighbours(new Cell(1, 1))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(1, 2))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(1, 3))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(2, 3))).isEqualTo(1);
        assertThat(world.countNeighbours(new Cell(2, 2))).isEqualTo(2);
    }

    @Test
    public void any_live_cell_with_more_than_three_live_neighbours_dies_as_if_by_overcrowding() {
        World world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2), new Cell(0, 2));

        world.nextGeneration();

        assertThat(world.getPopulation()).excludes(new Cell(1, 2));
    }

    @Test
    public void any_live_cell_with_fewer_than_two_live_neighbours_dies_as_if_caused_by_underpopulation() {
        World world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3));

        world.nextGeneration();

        assertThat(world.getPopulation()).excludes(new Cell(1, 1), new Cell(1, 3));
    }

    @Test
    public void any_live_cell_with_two_or_three_live_neighbours_lives_on_to_the_next_generation() {
        World world = new World(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2));

        world.nextGeneration();

        assertThat(world.getPopulation()).contains(new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(2, 2));
    }

    @Test
    public void any_dead_cell_with_exactly_three_live_neighbours_becomes_a_live_cell() {
        World world = new World(new Cell(1, 1), new Cell(2, 2), new Cell(1, 3));

        world.nextGeneration();

        assertThat(world.getPopulation()).contains(new Cell(1, 2));
    }

    @Test
    public void should_move_to_next_generation() {
        Cell[] blinder = {new Cell(3, 2), new Cell(3, 3), new Cell(3, 4)};
        World world = new World(blinder);

        world.nextGeneration();

        assertThat(world.hasChanged()).isTrue();
        assertThat(world.getPopulation()).containsOnly(new Cell(2,3), new Cell(3,3), new Cell(4,3));
    }
}
