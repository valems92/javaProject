package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import Common.Common;
import DB.DBOperational;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import boot.ServerHandler;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
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
	Common o = new Common(args);
	theServer.write(o);
	
	theServer.read();
    }

    @Override
    public void solveMaze(String[] args) {
	Common o = new Common(args);
	theServer.write(o);
	
	theServer.read();
    }

    @Override
    public void saveMaze(String name, String fileName) {
	//TODO: FIX
	Maze3d maze = generatedMazes.get(name);
	if (maze != null) {
	    try {
		OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
		out.write(maze.toByteArray());

		out.flush();
		out.close();

		setChanged();
		notifyObservers("display_message Maze saved");
	    } catch (FileNotFoundException e) {
		setChanged();
		notifyObservers("display_message Error occured while creating file");
	    } catch (IOException e) {
		setChanged();
		notifyObservers("display_message Error occured while writing to file");
	    }
	} else {
	    setChanged();
	    notifyObservers("display_message Maze with name " + name + " doesn't exist");
	}
    }

    @Override
    public void loadMaze(String fileName, String name) {
	//TODO: FIX
	if (!generatedMazes.containsKey(name)) {
	    try {
		InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));

		File file = new File(fileName);
		FileInputStream reader = new FileInputStream(file);
		byte b[] = new byte[(reader.read() * Byte.MAX_VALUE) + reader.read()];
		reader.close();

		in.read(b);
		in.close();

		Maze3d mazeLoaded = new Maze3d(b);
		generatedMazes.put(name, mazeLoaded);

		setChanged();
		notifyObservers("display_maze " + name);

	    } catch (FileNotFoundException e) {
		setChanged();
		notifyObservers("display_message Error occured while finding file");
	    } catch (IOException e) {
		setChanged();
		notifyObservers("display_message Error occured while reading to file");
	    }
	} else {
	    setChanged();
	    notifyObservers("display_maze " + name);
	}
    }

    @Override
    public void loadGameProperties(String path) {
	try {
	    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
	    Properties.properites = (Properties) decoder.readObject();
	    decoder.close();
	} catch (FileNotFoundException e) {
	    setChanged();
	    notifyObservers("display_message There was an error loading the properties file");
	    e.printStackTrace();
	}
    }

    @Override
    public void exit() {
	//TODO: FIX
	if (Properties.properites.getMySQL().booleanValue()) {
	    // new HashMap with maze and solution (the hash map is Object)
	    DBOperational myOperational = new DBOperational();

	    try {
		myOperational.clearDB();
	    } catch (SQLException e1) {
		e1.printStackTrace();
	    }

	    // save the hashMap to DB
	    try {
		myOperational.setJavaObject(this.generatedMazes);
		myOperational.saveObject(this.generatedMazes);

		myOperational.setJavaObject(this.solutions);
		myOperational.saveObject(this.solutions);

		myOperational.conn.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    ObjectOutputStream out;
	    try {
		out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(path)));

		out.writeObject(this.generatedMazes);
		out.writeObject(this.solutions);

		out.close();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void displayCrossSection(Object[] data) {
	Common o = new Common(data);
	theServer.write(o);
	
	theServer.read();
    }

    @Override
    public void loadData() throws Exception {
	//TODO: FIX
	Boolean mySQL = Properties.properites.getMySQL();
	if (mySQL.booleanValue()) {
	    System.out.println("Data loded from DB");
	    DBOperational myOperational = new DBOperational();
	    try {
		ConcurrentHashMap<String, Maze3d> generatedMazes_loaded = (ConcurrentHashMap<String, Maze3d>) myOperational
			.getObject(1);
		ConcurrentHashMap<String, Solution<Position>> solutions_loaded = (ConcurrentHashMap<String, Solution<Position>>) myOperational
			.getObject(2);

		if (generatedMazes_loaded != null && solutions_loaded != null) {
		    this.generatedMazes = generatedMazes_loaded;
		    this.solutions = solutions_loaded;
		}

		myOperational.conn.close();
	    } catch (Exception e) {
		System.out.println("There was an error getting data from SQL.");
	    }
	} else if (!mySQL.booleanValue()) {
	    ObjectInputStream in;
	    try {
		in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(path)));

		this.generatedMazes = (ConcurrentHashMap<String, Maze3d>) in.readObject();
		this.solutions = (ConcurrentHashMap<String, Solution<Position>>) in.readObject();
	    } catch (FileNotFoundException e) {
		System.out.println("Zip file with data doesn't exist. On exit game, it will be created.");
	    } catch (IOException e) {
		System.out.println("There was an error getting data from the zip file.");
	    }
	} else
	    throw new ExceptionInInitializerError("The property mySQL in properties file has an invalid value.");
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
}
