package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * <h1>Model</h1> Responsible for the maze3d algorithms.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface Model {
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg);

	public Maze3d getMazeByName(String name);
	
	public void solveMaze(Searcher<Position> searcher, String name, String type, State<Position> state);

	public Solution<Position> getLastSolution(String name);
	
	public void saveMaze(String name, String fileName);
	
	public void loadMaze(String fileName, String name);
	
	public void loadGameProperties(String path);
	
	public void exit();

	public void displayCrossSection(String name, int index, String section);
	
	public void loadData() throws Exception;

	public int[][] getLastCrossSection();
}
