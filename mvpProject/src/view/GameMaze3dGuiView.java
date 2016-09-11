package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import presenter.Properties;

public class GameMaze3dGuiView extends CommonMaze3dView {
	private Maze3dGameWindow window;
	
	@Override
	public void start() {	
		window = new Maze3dGameWindow(Properties.properites.getViewWidth(), Properties.properites.getViewHeight(), this);
		window.run();
	}

	@Override
	public void displayMaze(Maze3d maze) {	
		System.out.println(maze.toString());
	}

	@Override
	public void displaySolution(ArrayList<Position> solution) {
		
	}

	@Override
	public void displayMessage(String msg) {
		window.displayMessage(msg);
	}
	
	@Override
	public void exit() {
		
	}
	
	public void update(String command){
		setChanged();
		notifyObservers(command);
	}
}
