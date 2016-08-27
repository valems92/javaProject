package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * <h1>GrowingTreeGenerator</h1> Create a 3D maze using growing tree algorithm.
 * This script allows you to specify how the cells are removed from the list
 * that aggregates as the algorithm runs, just like "random" (pull the cell from
 * list at random) or "last selected" (pull the newest cell).
 * <p>
 * 
 * @author Valentina Munoz
 * @extends CommonMaze3dGenerator
 */
public class GrowingTreeGenerator extends CommonMaze3dGenerator {
	private SelectMethod method;

	/**
	 * Initialize the method data member.
	 * 
	 * @param method
	 *            method to choose the next position, as "random" or "last
	 *            selected"
	 */
	public GrowingTreeGenerator(SelectMethod method) {
		this.method = method;
	}

	@Override
	public Maze3d generate(int z, int y, int x) {
		Maze3d maze = new Maze3d(z, y, x);
		Random rand = new Random();

		// Get random position and set it as start position
		Position randomPosition = chooseEvenRandomPosition(z, y, x);
		maze.setStartPosition(randomPosition);

		// Create empty list and add to in the random cell. Turn this cell to
		// open
		ArrayList<Position> list = new ArrayList<Position>();
		list.add(randomPosition);
		maze.setCellValue(randomPosition, Maze3d.FREE);

		while (list.size() > 0) {
			// Get all valid neighbor
			Position cell = method.getSelectedCell(list);
			ArrayList<Position> allNeighbors = getNeighborsWithWalls(cell, z, y, x);
			ArrayList<Position> neighbors = maze.getNeighborsByValue(allNeighbors, Maze3d.WALL);
			if (neighbors.size() > 0) {
				// Choose a random neighbor and open it (and it's wall). add it
				// to the list
				int randomNeighbor = rand.nextInt(neighbors.size());
				Position chosenNeighbor = neighbors.get(randomNeighbor);
				maze.setCellValue(chosenNeighbor, Maze3d.FREE);
				removeWall(maze, cell, chosenNeighbor);
				list.add(chosenNeighbor);
			} else
				list.remove(cell);
		}

		// Set the goal position
		this.chooseGoalPosition(maze, z, y, x);
		return maze;
	}

	/**
	 * <h1>getNeighborsWithWalls</h1> Create a list of position received
	 * neighbors. For rows and columns, it skips the walls.
	 * <p>
	 * 
	 * @param p
	 *            Position in maze. A list of it's neighbors is return
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows is the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 * @return A list of position received (p) neighbors
	 */
	private ArrayList<Position> getNeighborsWithWalls(Position p, int z, int y, int x) {
		ArrayList<Position> neighbors = new ArrayList<Position>();
		if (p.z > 0)
			neighbors.add(new Position(p.z - 1, p.y, p.x));
		if (p.z < (z - 1))
			neighbors.add(new Position(p.z + 1, p.y, p.x));
		if (p.y > 1)
			neighbors.add(new Position(p.z, p.y - 2, p.x));
		if (p.y < (y - 2))
			neighbors.add(new Position(p.z, p.y + 2, p.x));
		if (p.x > 1)
			neighbors.add(new Position(p.z, p.y, p.x - 2));
		if (p.x < (x - 2))
			neighbors.add(new Position(p.z, p.y, p.x + 2));
		return neighbors;
	}

	/**
	 * <h1>removeWall</h1> Remove wall between cell position and neighbor
	 * position received.
	 * <p>
	 * 
	 * @param maze
	 *            The 3D maze created
	 * @param cell
	 * @param neighbor
	 */
	private void removeWall(Maze3d maze, Position cell, Position neighbor) {
		if (cell.y - neighbor.y < 0)
			maze.setCellValue(new Position(cell.z, cell.y + 1, cell.x), Maze3d.FREE);
		if (cell.y - neighbor.y > 0)
			maze.setCellValue(new Position(cell.z, cell.y - 1, cell.x), Maze3d.FREE);
		if (cell.x - neighbor.x < 0)
			maze.setCellValue(new Position(cell.z, cell.y, cell.x + 1), Maze3d.FREE);
		if (cell.x - neighbor.x > 0)
			maze.setCellValue(new Position(cell.z, cell.y, cell.x - 1), Maze3d.FREE);
	}

	/**
	 * <h1>chooseEvenRandomPosition</h1> Choose a random position in 3D maze.
	 * For rows and columns it random only even cells (odd cells are walls).
	 * <p>
	 * 
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows is the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 * @return A random position in 3D maze
	 */
	private Position chooseEvenRandomPosition(int z, int y, int x) {
		Position randomPos = chooseRandomPosition(z, y, x);
		while (randomPos.x % 2 == 1 || randomPos.y % 2 == 1)
			randomPos = chooseRandomPosition(z, y, x);
		return randomPos;
	}

	/**
	 * <h1>chooseGoalPosition</h1> Looks for an open cell in 3D maze, that it's
	 * not the start position and set it to be the goal position.
	 * <p>
	 * 
	 * @param maze
	 *            The 3D maze created
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows is the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 */
	private void chooseGoalPosition(Maze3d maze, int z, int y, int x) {
		Position randomPos = chooseRandomPosition(z, y, x);
		while (maze.getCellvalue(randomPos) == Maze3d.WALL || randomPos.equals(maze.getStartPosition()))
			randomPos = chooseRandomPosition(z, y, x);
		maze.setGoalPosition(randomPos);
	}
}
