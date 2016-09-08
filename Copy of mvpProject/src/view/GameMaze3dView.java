package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class GameMaze3dView extends Observable implements View {
	@Override
	public void start() {
		System.out.println("Please insert the generate command to test: ");
		
		Scanner scanner = new Scanner(System.in);	
		String commandLine = scanner.nextLine();
		
		while(commandLine != "exit") {
			setChanged();
			notifyObservers(commandLine);
			
			commandLine = scanner.nextLine();
		}
	}
	
	@Override
	public void displayMaze(Maze3d maze) {
		System.out.println(maze);
	}

	@Override
	public void displaySolution(ArrayList<Position> solution) {
		System.out.println(solution);
	}
}