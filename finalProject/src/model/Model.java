package model;

import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;

/**
 * <h1>Model</h1> Responsible for the maze3d algorithms.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface Model {
	/**
	 * <h1>generateMaze</h1> Generate a 3d maze in a another thread, according
	 * to arguments received.When finish, Let the controller know.
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 * @param z
	 *            Total floors of maze
	 * @param y
	 *            Total rows of maze
	 * @param x
	 *            Total columns of maze
	 * @param mg
	 *            The generate algorithm class
	 */
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg);

	/**
	 * <h1>displayMazeByName</h1> Get a maze name, and if exist, display it.
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 */
	public void displayMazeByName(String name);

	/**
	 * <h1>displayCrossSection
	 * <h1>Get a maze name, and if exist, display a 2D array of the cross
	 * section wanted
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 * @param index
	 *            The index if the wanted section
	 * @param section
	 *            The wanted section
	 */
	public void displayCrossSection(String name, int index, String section);

	/**
	 * <h1>saveMazeByName</h1> Get a maze name, and if exist, save it compressed
	 * to a file.
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 * @param fileName
	 *            The name of the new file
	 */
	public void saveMazeByName(String name, String fileName);

	/**
	 * <h1>loadMaze</h1> Get a file name, and if exist, read it, decompress it
	 * and create a maze with the received name
	 * <p>
	 * 
	 * @param fileName
	 *            The file wanted to load
	 * @param name
	 *            The name of the new maze
	 */
	public void loadMaze(String fileName, String name);

	/**
	 * <h1>solveMaze</h1> Get a maze name, and if exist, solve it with the
	 * received algorithm
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 * @param searcher
	 *            The searcher algorithm class
	 */
	public void solveMaze(String name, Searcher<Position> searcher);

	/**
	 * <h1>displaySolutionByName</h1> Get a maze name, and if exist and was
	 * solved before, display it solution.
	 * <p>
	 * 
	 * @param name
	 *            The maze name
	 */
	public void displaySolutionByName(String name);

	public void exit();
}
