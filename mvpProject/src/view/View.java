package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public interface View  {
	public void start();
	public void displayMaze(Maze3d maze);
	public void displaySolution(ArrayList<Position> solution);
	public void exit();
}
