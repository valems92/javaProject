package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import model.GameMaze3dModel;
import presenter.CommandsManager;
import presenter.Maze3dCommands;
import presenter.Presenter;
import presenter.Properties;
import view.CommonMaze3dView;
import view.GameMaze3dGuiView;
import view.GameMaze3dTextView;
import view.View;

public class Run {
	private final static String FILE_NAME = "properties.xml";

	public static void main(String[] args) {
		try {
			readProperties();
		} catch (Exception e) {	
			return;
		}
		
		CommonMaze3dView view;
		String viewType = Properties.properites.getViewType();
		if(viewType.equals("text")) 
			view = new GameMaze3dTextView();
		else if(viewType.equals("gui"))
			view = new GameMaze3dGuiView();
		else 
			return;
		
		GameMaze3dModel model = new GameMaze3dModel();

		CommandsManager commandManager = new Maze3dCommands();
		commandManager.setCommands();
		commandManager.setModelView(model, view);

		Presenter presenter = new Presenter(view, model, commandManager);

		view.addObserver(presenter);
		model.addObserver(presenter);

		view.start();
	}

	public static void readProperties() throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE_NAME)));
		Properties.properites = (Properties)decoder.readObject();
		decoder.close();
	}
}
