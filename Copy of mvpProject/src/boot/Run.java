package boot;

import model.GameMaze3dModel;
import presenter.CommandsManager;
import presenter.Maze3dCommands;
import presenter.Presenter;
import view.GameMaze3dView;

public class Run {

	public static void main(String[] args) {
		GameMaze3dModel model = new GameMaze3dModel();
		GameMaze3dView view = new GameMaze3dView();

		CommandsManager commandManager = new Maze3dCommands();
		commandManager.setCommands();
		commandManager.setModelView(model, view);

		Presenter presenter = new Presenter(view, model, commandManager);

		view.addObserver(presenter);
		model.addObserver(presenter);

		view.start();
	}
}
