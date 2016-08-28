package model;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Searcher;

public interface Model {
	public void generateMaze(String name, int z, int y, int x);  // ALGORITHM
	public void displayMazeByName(String name);
	public void displayCrossSection(); // TO DO
	public void saveMazeByName(String name, String fileName);
	public void loadMaze(String fileName, String name);  // TO DO
	public void solveMaze(String name, String algorithmClassName, String comperatorClassName);
	public void displaySolutionByName(String name); // ALGORITHM
}
