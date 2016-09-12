package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GameMaze3dTextView extends CommonMaze3dView {
	private BufferedReader in;
	private PrintWriter out;

	public GameMaze3dTextView() {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
	}

	@Override
	public void start() {
		println("Please enter commands:");
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
					println(e.getMessage());
				}
			}
		}).start();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		println(maze.toString());
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		println(solution.getResults().toString());
	}
	
	@Override
	public void displayMessage(String msg) {
		println(msg);
	}

	@Override
	public void exit() {
		try {
			this.in.close();
		} catch (IOException e) {
			println(e.getMessage());
		}
		System.out.println("The game closed");
		this.out.close();
		Thread.interrupted();	
		return;
	}

	@Override
	public void print(String string) {
		out.print(string);
		out.flush();
		
	}

	@Override
	public void println(String string) {
		out.println(string);
		out.flush();
		
	}

}