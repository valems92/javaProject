package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import CommonData.CommonData;
import DB.DBOperational;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import boot.ServerHandler;
import presenter.Properties;

/**
 * <h1>GameMaze3dModel</h1> Implements all Model interface functoins. Also save
 * all mazes created / loaded and their's solution if were solved.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class GameMaze3dModel extends Observable implements Model {
	private final String path = "mazeAndsolution.zip";

	private ClassGenerator classGenerator;
	private Object generatedObject;

	private ServerHandler theServer;

	public GameMaze3dModel(ServerHandler theServer) {
		this.theServer = theServer;
		classGenerator = new ClassGenerator(this);
	}

	@Override
	public void setGeneratedObject(Object o) {
		generatedObject = o;
	}

	@Override
	public Object getGeneratedObject() {
		return generatedObject;
	}

	@Override
	public void generateMaze(String[] args) {
		CommonData o = new CommonData(args);
		theServer.write(o);

		theServer.read();
	}

	@Override
	public void solveMaze(String[] args) {
		CommonData o = new CommonData(args);
		theServer.write(o);

		theServer.read();
	}

	@Override
	public void saveMaze(String[] args) {
		CommonData o = new CommonData(args);
		theServer.write(o);

		theServer.read();
	}

	@Override
	public void loadMaze(String[] args) {
		CommonData o = new CommonData(args);
		theServer.write(o);

		theServer.read();
	}

	@Override
	public void loadGameProperties(String path) {
		try {
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
			Properties.properites = (Properties) decoder.readObject();
			decoder.close();

			Object[] data = { "set_properties", Properties.properites };
			CommonData o = new CommonData(data);
			theServer.write(o);

		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("display_message There was an error loading the properties file");
			e.printStackTrace();
		}
	}

	@Override
	public void exit() {
		Object[] data = { "exit" };
		CommonData o = new CommonData(data);
		theServer.write(o);

		theServer.read();
	}

	public void saveData(Object[] data) {
		ConcurrentHashMap<String, Maze3d> generatedMazes = (ConcurrentHashMap<String, Maze3d>) data[1];
		ConcurrentHashMap<String, Solution<Position>> solutions = (ConcurrentHashMap<String, Solution<Position>>) data[2];

		if (Properties.properites.getMySQL().booleanValue()) {
			DBOperational myOperational = new DBOperational();

			try {
				myOperational.clearDB();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			try {
				myOperational.setJavaObject(generatedMazes);
				myOperational.saveObject(generatedMazes);

				myOperational.setJavaObject(solutions);
				myOperational.saveObject(solutions);

				myOperational.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(path)));

				out.writeObject(generatedMazes);
				out.writeObject(solutions);

				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		theServer.close();
	}

	@Override
	public void displayCrossSection(Object[] data) {
		CommonData o = new CommonData(data);
		theServer.write(o);

		theServer.read();
	}

	@Override
	public void loadData() throws Exception {
		Boolean mySQL = Properties.properites.getMySQL();

		ConcurrentHashMap<String, Maze3d> generatedMazes_loaded = null;
		ConcurrentHashMap<String, Solution<Position>> solutions_loaded = null;

		if (mySQL) {
			DBOperational myOperational = new DBOperational();
			try {
				generatedMazes_loaded = (ConcurrentHashMap<String, Maze3d>) myOperational.getObject(1);
				solutions_loaded = (ConcurrentHashMap<String, Solution<Position>>) myOperational.getObject(2);
				myOperational.conn.close();
			} catch (Exception e) {
				System.out.println("There was an error getting data from SQL.");
			}
		} else if (!mySQL) {
			try {
				ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(path)));

				generatedMazes_loaded = (ConcurrentHashMap<String, Maze3d>) in.readObject();
				solutions_loaded = (ConcurrentHashMap<String, Solution<Position>>) in.readObject();

			} catch (FileNotFoundException e) {
				System.out.println("Zip file with data doesn't exist. On exit game, it will be created.");
			} catch (IOException e) {
				System.out.println("There was an error getting data from the zip file.");
			}
		} else
			throw new ExceptionInInitializerError("The property mySQL in properties file has an invalid value.");

		if (generatedMazes_loaded != null && solutions_loaded != null) {
			Object[] data = { "load_data", generatedMazes_loaded, solutions_loaded };
			CommonData o = new CommonData(data);
			theServer.write(o);
		}
	}

	@Override
	public void generateClass(String fieldsValues) {
		classGenerator.createObjects(fieldsValues, Properties.class);
	}

	@Override
	public void update(String str) {
		setChanged();
		notifyObservers(str);
	}

	@Override
	public void saveSettingToServer() {
		Object[] data = { "set_properties", Properties.properites };
		CommonData o = new CommonData(data);
		theServer.write(o);
	}
}
