package boot;

import model.GameMaze3dModel;
import model.Model;
import presenter.Presenter;
import view.GameMaze3dView;
import view.View;

public class Run {

	public static void main(String[] args) {
		Model model = new GameMaze3dModel();
		View ui = new GameMaze3dView();
		
		Presenter presenter = new Presenter(ui, model);
		
	}

}
