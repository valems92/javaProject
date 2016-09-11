package algorithms.search;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;

public class BestFirstSearchTest {
	private final int BIG_MAZE = 30;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test(expected = ExceptionInInitializerError.class)
	public void bfsNullComparator() throws Exception {
		new BestFirstSearch<Position>(null);
	}

	@Test
	public void bfsBigMaze() throws Exception {
		Maze3dGenerator mg = new SimpleMaze3dGenerator();
		Maze3d maze = mg.generate(BIG_MAZE, BIG_MAZE, BIG_MAZE);
		Searchable<Position> mazeDomain = new Maze3dDomain(maze);
		Searcher<Position> algorithm = new BestFirstSearch<Position>(new CostStateComperator<Position>());
		Solution<Position> solution = algorithm.search(mazeDomain);

		Assert.assertNotNull("The maze should have a solution for it maximum size", solution.results);
	}

	@Test(expected = ExceptionInInitializerError.class)
	public void bfsSmallMaze() throws Exception {
		Maze3dGenerator mg = new GrowingTreeGenerator(new RandomSelectMethod());
		mg.generate(0, 0, 0);
	}

	@Test
	public void bfsAllCellsWallsMaze() throws Exception {
		Maze3d maze = new Maze3d(3, 3, 3);
		maze.setStartPosition(new Position(0, 0, 0));
		maze.setGoalPosition(new Position(2, 2, 2));

		Searchable<Position> mazeDomain = new Maze3dDomain(maze);

		Searcher<Position> algorithm = new BestFirstSearch<Position>(new CostStateComperator<Position>());
		Solution<Position> solution = algorithm.search(mazeDomain);

		Assert.assertNull("A solution doesn't exist so it should be null", solution.results);
	}

	@Test(expected = Exception.class)
	public void bfsMazeWithoutInitalOrGoal() throws Exception {
		Maze3d maze = new Maze3d(3, 3, 3);
		Searchable<Position> mazeDomain = new Maze3dDomain(maze);

		Searcher<Position> algorithm = new BestFirstSearch<Position>(new CostStateComperator<Position>());
		algorithm.search(mazeDomain);
	}
}
