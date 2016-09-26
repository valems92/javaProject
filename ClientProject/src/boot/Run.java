package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import model.GameMaze3dModel;
import presenter.CommandsManager;
import presenter.Maze3dCommands;
import presenter.Presenter;
import presenter.Properties;
import view.CommonMaze3dView;
import view.GameMaze3dGuiView;
import view.GameMaze3dTextView;

public class Run {
    private final static String FILE_NAME = "properties.xml";

    public static void main(String[] args) {
	try {
	    readProperties();
	} catch (Exception e) {
	    try {
		generateProperties();
	    } catch (Exception e1) {
		System.out.println("There was an error while generating properties file.");
		return;
	    }
	}

	ServerHandler theServer;
	try {
	    CommonMaze3dView view;
	    String viewType = Properties.properites.getViewType();

	    if (viewType.equals("text"))
		view = new GameMaze3dTextView();
	    else if (viewType.equals("gui"))
		view = new GameMaze3dGuiView();
	    else {
		System.out.println("View type in properties file is incorrect. It should be 'text' or 'gui'");
		return;
	    }

	    CommandsManager commandManager = new Maze3dCommands();
	    commandManager.setCommands();

	    theServer = new ServerHandler(commandManager);
	    GameMaze3dModel model = new GameMaze3dModel(theServer);

	    commandManager.setModelView(model, view);

	    Presenter presenter = new Presenter(view, model, commandManager);

	    view.addObserver(presenter);
	    model.addObserver(presenter);

	    view.start();

	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public static void readProperties() throws Exception {
	XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE_NAME)));
	Properties.properites = (Properties) decoder.readObject();
	decoder.close();
    }

    public static void writeProperties() throws Exception {
	XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
	encoder.writeObject(Properties.properites);
	encoder.close();
    }

    private static void generateProperties() throws Exception {
	Properties.properites.setViewType("gui");
	Properties.properites.setViewHeight(820);
	Properties.properites.setViewWidth(1200);
	Properties.properites.setGenerateAlgorithm("GrowingTree");
	Properties.properites.setSelectCellMethod("Random");
	Properties.properites.setSolveAlgorithm("BFS");
	Properties.properites.setComparator("Cost");
	Properties.properites.setNumberOfThreads(5);
	Properties.properites.setMySQL(false);
	Properties.properites.setHintLength(0.5);
	Properties.properites.setAnimationSpeed(200);
	Properties.properites.setMazeDisplay("3d");

	writeProperties();
    }
}
