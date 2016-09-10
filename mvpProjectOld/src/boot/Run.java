package boot;

import model.GameMaze3dModel;
import model.Model;
import presenter.Presenter;
import view.GameMaze3dView;
import view.View;

public class Run {

	public static void main(String[] args) {
		GameMaze3dModel model = new GameMaze3dModel();
		GameMaze3dView ui = new GameMaze3dView();
		
		Presenter presenter = new Presenter(ui, model);
		model.addObserver(presenter);
		ui.addObserver(presenter);
		
	}

}
