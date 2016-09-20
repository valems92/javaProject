package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1>GameMaze3dTextView</h1>
 * Implements all View interface functoins for text (console) view.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class GameMaze3dTextView extends CommonMaze3dView {
	private BufferedReader in;
	private PrintWriter out;

	public GameMaze3dTextView() {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
	}

	@Override
	public void start() {
		displayMessage("Please enter commands:");
		new Thread(new Runnable() {
			public void run() {
				try {
					String input = null;
					while (input != "exit") {
						input = in.readLine();

						setChanged();
						notifyObservers(input);
					}
				} catch (IOException e) {
					displayMessage(e.getMessage());
				}
			}
		}).start();
	}

	@Override
	public void displayMaze(Maze3d maze, String name) {
		displayMessage(name + " :\n" + maze.toString());
	}

	@Override
	public void displaySolution(Solution<Position> solution, String type) {
		displayMessage(solution.getResults().toString());
	}

	@Override
	public void displayCrossSection(int[][] crossSection) {
		for (int i = 0; i < crossSection.length; i++) {
			for (int j = 0; j < crossSection[0].length; j++)
				System.out.print(crossSection[i][j] + " ");
			System.out.print("\n");
		}
	}

	@Override
	public void displayMessage(String msg) {
		out.println(msg);
		out.flush();
	}

	@Override
	public void exit() {
		try {
			this.in.close();
		} catch (IOException e) {
			displayMessage(e.getMessage());
		}
		System.out.println("The game closed");
		this.out.close();
		Thread.interrupted();
		return;
	}

	@Override
	public void displayExistingMaze(Maze3d maze, String name, String msg) {
		System.out.println(msg);
		displayMaze(maze, name);
	}

}