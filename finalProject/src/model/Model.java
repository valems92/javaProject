package model;

import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;

public interface Model {
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg);
	public void displayMazeByName(String name);
	public void displayCrossSection(String name, int index, String section);
	public void saveMazeByName(String name, String fileName);
	public void loadMaze(String fileName, String name);
	public void solveMaze(String name, Searcher<Position> searcher);
	public void displaySolutionByName(String name); 
}
