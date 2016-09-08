package model;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
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
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg);

	public Maze3d getMazeByName(String name);
	
	public void solveMaze(String name, Searcher<Position> searcher);

	public ArrayList<Position> getSolutionByMazeName(String name);
}