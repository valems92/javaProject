package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public class GameMaze3dGuiView extends CommonMaze3dView {
	private Maze3dGameWindow window;
	
	@Override
	public void start() {	
		window = new Maze3dGameWindow(Properties.properites.getViewWidth(), Properties.properites.getViewHeight(), this);
		window.run();
	}

	@Override
	public void displayMaze(Maze3d maze, String name) {	
		window.displayMaze(maze, name);
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		
	}

	@Override
	public void displayMessage(String msg) {
		window.displayMessage(msg);
	}
	
	public void displayCrossSection(int[][] crossSection) {
		window.displayCrossSection(crossSection);
	}
	
	@Override
	public void exit() {
		
	}
	
	public void update(String command){
		setChanged();
		notifyObservers(command);
	}
}
