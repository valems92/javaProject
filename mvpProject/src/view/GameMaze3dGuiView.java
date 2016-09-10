package view;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import presenter.Properties;

public class GameMaze3dGuiView extends CommonMaze3dView {

	@Override
	public void start() {	
		Maze3dGameWindow window = new Maze3dGameWindow(Properties.properites.getViewWidth(), Properties.properites.getViewHeight(), this);
		window.run();
	}

	@Override
	public void displayMaze(Maze3d maze) {	

	}

	@Override
	public void displaySolution(ArrayList<Position> solution) {
		
	}
	
	public void update(String command){
		setChanged();
		notifyObservers(command);
	}
}
