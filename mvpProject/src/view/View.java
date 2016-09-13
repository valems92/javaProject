package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View  {
	public void start();
	public void displayMaze(Maze3d maze);
	public void displaySolution(Solution<Position> solution);
	public void displayMessage(String msg);
	public void displayCrossSection(int[][] crossSection);
	public void exit();
}
