package boot;

import model.GameMaze3dModel;
import presenter.CommandsManager;
import presenter.Maze3dCommands;
import presenter.Presenter;
import view.GameMaze3dView;

public class Run {

	public static void main(String[] args) {
		
		CommandsManager mngr=new Maze3dCommands();
		mngr.setCommands();

		
		GameMaze3dModel model = new GameMaze3dModel();
		GameMaze3dView view = new GameMaze3dView();
		
		
		mngr.setModelView(model, view);
		
		Presenter presenter = new Presenter(view, model,mngr);
		model.setPresenter(presenter);
		view.setPresenter(presenter);

		
		view.addObserver(presenter);
		model.addObserver(presenter);
	
		
		view.start();
		
		
	}

}
