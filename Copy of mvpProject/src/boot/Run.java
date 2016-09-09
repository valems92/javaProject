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
import view.GameMaze3dView;

public class Run {
	private final static String FILE_NAME = "properties.xml";

	public static void main(String[] args) {
		try {
			readProperties();
		} catch (Exception e) {
			generateProperties();
		}
		
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

	private static void generateProperties() {
		Properties.properites.setGenerateAlgorithm("GrowingTree");
		Properties.properites.setSelectCellMethod("Random");
		Properties.properites.setSolveAlgorithm("BFS");
		Properties.properites.setComparator("Cost");
		Properties.properites.setNumberOfThreads(5);
		
		try {
			writeProperties();
			System.out.println("properties.xml is missing so default properties file was crated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeProperties() throws Exception {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
		encoder.writeObject(Properties.properites);
		encoder.close();
	}

	public static void readProperties() throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE_NAME)));
		Properties.properites = (Properties)decoder.readObject();
		decoder.close();
	}
}
