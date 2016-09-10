package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

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
	public void displaySolution(ArrayList<Position> solution) {
		println(solution.toString());
	}
	
	private void println(String str){
		out.println(str);
		out.flush();
	}
}