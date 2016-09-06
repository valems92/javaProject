package presenter;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class Presenter implements Observer {
	private View ui;
	private Model model;
	private CommandsManager mngr;
	
	public Presenter(View ui, Model model, CommandsManager mngr) {
		this.ui = ui;
		this.model = model;
		this.mngr=mngr;
	}

	@Override
	public void update(Observable o, Object commandLine) {
		System.out.println("received");
		if(o==ui){
			try {
				mngr.executeCommand((String)commandLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(o==model){
			
			try {
				mngr.executeCommand((String)commandLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void SendMazeToView(String maze3d) {
		ui.DisplayMaze(maze3d);
		
	}
}
