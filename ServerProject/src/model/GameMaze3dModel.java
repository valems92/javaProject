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
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;

/**
 * <h1>GameMaze3dModel</h1> 
 * Implements all Model interface functoins. Also save all mazes created / loaded and their's solution if were solved.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class GameMaze3dModel extends Observable implements Model {
	public ConcurrentHashMap<String, Maze3d> generatedMazes;
	private ConcurrentHashMap<String, Solution<Position>> solutions;
	
	private Solution<Position> lastSolution;
	private int[][] crossSection;
	private final String path = "mazeAndsolution.zip";

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;
	
	private ClassGenerator classGenerator;
	private Object generatedObject;
	private ObjectOutputStream outToClient;
	

	public GameMaze3dModel(OutputStream outToClient) {
		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, Solution<Position>>();

		executorGenerate = Executors.newFixedThreadPool(50);
		executorSolve = Executors.newFixedThreadPool(50);
		
		classGenerator = new ClassGenerator(this);
		try {
			this.outToClient=new ObjectOutputStream(outToClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {


		if (generatedMazes.containsKey(name)) {
			setChanged();
			notifyObservers("display_maze " + name + " Maze called " + name + " already exist.");
			return;
		}
		
		if(z == 0 || y == 0 || x == 0) {
			setChanged();
			notifyObservers("display_message Maze dimension is missing.");
			return;
		}
		
		if (z > 30 || y > 30 || x > 30 || y < 4 || x < 4) {
			setChanged();
			notifyObservers("display_message Maze dimensions have to be smaller than 30. Also, rows and Columns should be bugger than 4.");
			return;
		}

		Future<Maze3d> generatedMaze = executorGenerate.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception {
				Maze3d maze = mg.generate(z, y, x);
				return maze;
			}
		});

			Maze3d maze;
			try {
				maze = generatedMaze.get();
				System.out.println("done");
				generatedMazes.put(name, maze);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
	}
	

	@Override
	public Maze3d getMazeByName(String name) {
		return generatedMazes.get(name);
	}

	@Override
	public void solveMaze(Searcher<Position> searcher, String name, String type, State<Position> state) {
		Maze3d maze = generatedMazes.get(name);
		
		if (solutions.containsKey(name) && state != null && state.getState().equals(maze.getStartPosition())) {
			lastSolution = solutions.get(name);
			setChanged();
			notifyObservers("display_solution " + type);
			return;
		}
	
		Searchable<Position> mazeDomain = new Maze3dDomain(maze);
		 State<Position> prevInitial = mazeDomain.getInitialState();

		Future<Solution<Position>> generatedSolution = executorSolve.submit(new Callable<Solution<Position>>() {
			@Override
			public Solution<Position> call() throws Exception {
				if (state != null) 
					mazeDomain.setInitialState(state);

				Solution<Position> solution = searcher.search(mazeDomain);
				return solution;
			}
		});

		try {
			Solution<Position> solution = generatedSolution.get();

			if (state == null || state.getState().equals(maze.getStartPosition()))
				solutions.put(name, solution);

			lastSolution = solution;
			mazeDomain.setInitialState(prevInitial);
			
			setChanged();
			notifyObservers("display_solution " + type);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Solution<Position> getLastSolution() {
		return lastSolution;
	}

	@Override
	public void saveMaze(String name, String fileName) {
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
			}

	@Override
	public void displayCrossSection(String name, int index, String section) {
		if (index < 0) {
			setChanged();
			notifyObservers("display_message Invalid Index!");
			return;
		}

		if (!generatedMazes.containsKey(name)) {
			setChanged();
			notifyObservers("display_message Maze with name " + name + " doesn't exist");
			return;
		}

		Maze3d maze = generatedMazes.get(name);
		if (section.equals("z") || section.equals("Z"))
			crossSection = maze.getCrossSectionByZ(index);

		else if (section.equals("y") || section.equals("Y"))
			crossSection = maze.getCrossSectionByY(index);

		else if (section.equals("x") || section.equals("X"))
			crossSection = maze.getCrossSectionByX(index);

		else {
			setChanged();
			notifyObservers("display_message Invalid Section!");
			return;
		}

		setChanged();
		notifyObservers("display_cross_section");
	}

	@Override
	public int[][] getLastCrossSection() {
		return crossSection;
	}

	@Override
	public void loadData() throws Exception {
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
