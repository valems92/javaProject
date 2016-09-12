package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View  {
	public void start();
	public void displayMaze(Maze3d maze);
	public void displaySolution(Solution<Position> solution);
	public void displayMessage(String msg);
	public void exit();
	public void print(String string);
	public void println(String string);
}
