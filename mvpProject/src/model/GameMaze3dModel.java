package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import DB.DBOperational;
import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import presenter.Properties;

public class GameMaze3dModel extends Observable implements Model {
	public ConcurrentHashMap<String, Maze3d> generatedMazes;
	private ConcurrentHashMap<String, Solution<Position>> solutions;
	private ConcurrentHashMap<String, int[][][]> crossSections;
	private final String path = "mazeAndsolution.zip";

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;

	public GameMaze3dModel() {
		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, Solution<Position>>();
		crossSections = new ConcurrentHashMap<String, int[][][]>();

		executorGenerate = Executors.newFixedThreadPool(Properties.properites.getNumberOfThreads());
		executorSolve = Executors.newFixedThreadPool(Properties.properites.getNumberOfThreads());
	}

	@Override
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {
		if (generatedMazes.containsKey(name)) {
			setChanged();
			notifyObservers("display_message " + "Maze called " + name + " already exist.");
			return;
		}

		Future<Maze3d> generatedMaze = executorGenerate.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = mg.generate(z, y, x);
				return maze;
			}
		});

		try {
			Maze3d maze = generatedMaze.get();
			generatedMazes.put(name, maze);

			setChanged();
			notifyObservers("display_maze " + name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Maze3d getMazeByName(String name) {
		return generatedMazes.get(name);
	}

	@Override
	public void solveMaze(String name, Searcher<Position> searcher) {
		if (solutions.containsKey(name)) {
			setChanged();
			notifyObservers("display_solution " + name);
			return;
		}

		Future<Solution<Position>> generatedSolution = executorSolve.submit(new Callable<Solution<Position>>() {
			@Override
			public Solution<Position> call() throws Exception {
				Maze3d maze = generatedMazes.get(name);
				Searchable<Position> mazeDomain = new Maze3dDomain(maze);
				Solution<Position> solution = searcher.search(mazeDomain);

				return solution;
			}
		});

		try {
			Solution<Position> solution = generatedSolution.get();
			solutions.put(name, solution);

			setChanged();
			notifyObservers("display_solution " + name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Solution<Position> getSolutionByMazeName(String name) {
		return solutions.get(name);
	}

	@Override
	public void saveData(String fileName) {
		try {
			MyCompressorOutputStream file = new MyCompressorOutputStream(new FileOutputStream(fileName));
			GZIPOutputStream out = new GZIPOutputStream(file, 100000);

			for (Maze3d maze : generatedMazes.values())
				out.write(maze.toByteArray());

			out.finish();
		} catch (IOException e) {
			setChanged();
			notifyObservers("display_message " + "There was an error saving the data");
			e.printStackTrace();
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
			notifyObservers("display_message " + "There was an error loading the properties file");
			e.printStackTrace();
		}
	}

	@Override
	public void exit() {
		executorGenerate.shutdown();
		executorSolve.shutdown();
		try {
			executorGenerate.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
			executorSolve.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (Properties.properites.getMySQL().booleanValue()) {
			// new HashMap with maze and solution (the hash map is Object)
			DBOperational myOperational = new DBOperational();
			try {
				myOperational.clearDB();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			myOperational.setJavaObject(this.generatedMazes);

			// save the hashMap to DB
			try {
				myOperational.saveObject(this.generatedMazes);
				myOperational.setJavaObject(this.solutions);
				myOperational.saveObject(this.solutions);
				myOperational.conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
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
	public void displayCrossSection(String name, int index, String section) {
		if (index < 0) {
			setChanged();
			notifyObservers("display_message " + "Invalid Index!");
			return;
		}

		if (!generatedMazes.containsKey(name)) {
			setChanged();
			notifyObservers("display_message " + "Maze with name " + name + " doesn't exist");
			return;
		}

		int wantedSection;
		if (section == "z" || section == "Z")
			wantedSection = 0;
		else if (section == "y" || section == "Y")
			wantedSection = 1;
		else if (section == "x" || section == "X")
			wantedSection = 2;
		else {
			setChanged();
			notifyObservers("display_message " + "Invalid Section!");
			return;
		}

		if (crossSections.containsKey(name)) {
			int[][][] mazeSections = crossSections.get(name);
			if (mazeSections[wantedSection] != null) {
				setChanged();
				notifyObservers("display_cross_section " + name + " " + wantedSection);
				return;
			} else
				createCrossSection(name, index, section);
		}

		crossSections.put(name, new int[3][][]);
		createCrossSection(name, index, section);

		setChanged();
		notifyObservers("display_cross_section " + name + " " + wantedSection);
	}

	private void createCrossSection(String name, int index, String section) {
		Maze3d maze = generatedMazes.get(name);
		int[][][] value = crossSections.get(name);

		if (section == "z" || section == "Z")
			value[0] = maze.getCrossSectionByZ(index);

		else if (section == "y" || section == "Y")
			value[1] = maze.getCrossSectionByY(index);
		else
			value[2] = maze.getCrossSectionByX(index);
		crossSections.put(name, value);
	}

	public int[][] getCrossSectionByNameBySection(String name, int section) {
		int[][][] value = crossSections.get(name);
		return value[section];
	}

	@Override
	public void loadData() throws Exception {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (!mySQL.booleanValue()) {
			System.out.println("zip loaded");

			ObjectInputStream in;
			try {
				in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(path)));
				this.generatedMazes = (ConcurrentHashMap<String, Maze3d>) in.readObject(); // need
																							// to
																							// get
																							// the
																							// Object
																							// to
																							// new
																							// HashMap
				this.solutions = (ConcurrentHashMap<String, Solution<Position>>) in.readObject();
				// The model should updated the data member

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			throw new ExceptionInInitializerError("mySQL field's value invalid!");

	}

}
