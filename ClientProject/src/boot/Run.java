package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private static ObjectOutputStream outToServer;
	private static ObjectInputStream fromTheServer;

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

		// Server
		System.out.println("Client Side");
		Socket theServer;
		try {
			theServer = new Socket("localhost", 5400);
			outToServer = new ObjectOutputStream(theServer.getOutputStream());

			System.out.println("connected to server!");

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

			GameMaze3dModel model = new GameMaze3dModel(outToServer);

			CommandsManager commandManager = new Maze3dCommands();
			commandManager.setCommands();
			commandManager.setModelView(model, view);

			Presenter presenter = new Presenter(view, model, commandManager);

			view.addObserver(presenter);
			model.addObserver(presenter);

			view.start();
			fromTheServer = new ObjectInputStream(theServer.getInputStream());
			model.setFromTheServerinput(fromTheServer);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
