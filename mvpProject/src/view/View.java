package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1>View</h1> 
 * Responsible for the view of the program.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public interface View  {
	/**
	 * <h1>start</h1> 
	 * Responsible to start the program.
	 * <p>
	 */
	public void start();
	
	/**
	 * <h1>displayMaze</h1>
	 * Get a maze and display it.
	 * @param maze Maze to display
	 * @param name Maze name
	 */
	public void displayMaze(Maze3d maze, String name);
	
	/**
	 * <h1>displaySolution</h1>
	 * Get a solution and it's type (hint or entire solve) and display it.
	 * @param solution The solution wanted
	 * @param type Hint or entire solve
	 */
	public void displaySolution(Solution<Position> solution, String type);
	
	/**
	 * <h1>displayMessage</h1>
	 * Get a message and display it
	 * @param msg
	 */
	public void displayMessage(String msg);
	
	/**
	 * <h1>displayExistingMaze</h1>
	 * Get a message, display it and then display the maze received.
	 * @param maze The maze to display
	 * @param name The maze name
	 * @param msg The message to display before the maze
	 */
	public void displayExistingMaze(Maze3d maze, String name, String msg);
	
	/**
	 * <h1>displayCrossSection</h1>
	 * Get a cross section (2D array) and display it.
	 * @param crossSection 2D array to display
	 */
	public void displayCrossSection(int[][] crossSection);
	
	/**
	 * <h1>exit</h1> 
	 * Close all needed stuff in view before exit.
	 * <p>
	 */
	public void exit();
}
