package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import CommonData.CommonData;
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
import server.ClientHandler;

/**
 * <h1>GameMaze3dModel</h1> Implements all Model interface functoins. Also save
 * all mazes created / loaded and their's solution if were solved.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class GameMaze3dModel extends Observable implements Model {
	private ClientHandler client;

	public ConcurrentHashMap<String, Maze3d> generatedMazes;
	private ConcurrentHashMap<String, Solution<Position>> solutions;

	private Solution<Position> lastSolution;
	private int[][] crossSection;

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;

	public GameMaze3dModel(ClientHandler client) {
		this.client = client;

		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, Solution<Position>>();

		executorGenerate = Executors.newFixedThreadPool(50);
		executorSolve = Executors.newFixedThreadPool(50);
	}

	@Override
	public void loadData(Object[] data) {
		generatedMazes = (ConcurrentHashMap<String, Maze3d>) data[1];
		solutions = (ConcurrentHashMap<String, Solution<Position>>) data[2];
	}

	@Override
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {

		if (generatedMazes.containsKey(name)) {
			System.out.println("display maze 1");
			Object[] data = { "display_maze", name, generatedMazes.get(name),
					"Maze called " + name + " already exist." };
			CommonData o = new CommonData(data);
			client.write(o);
			return;
		}

		if (z == 0 || y == 0 || x == 0) {
			Object[] data = { "display_message", "Maze dimension is missing." };
			CommonData o = new CommonData(data);
			client.write(o);
			return;
		}

		if (z > 30 || y > 30 || x > 30 || y < 4 || x < 4) {
			Object[] data = { "display_message",
					"Maze dimensions have to be smaller than 30. Also, rows and Columns should be bugger than 4." };
			CommonData o = new CommonData(data);
			client.write(o);
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
			generatedMazes.put(name, maze);
			
			Object[] data = { "display_maze", name, generatedMazes.get(name) };
			CommonData o = new CommonData(data);
			client.write(o);
			
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
	public void solveMaze(Searcher<Position> searcher, String name, String type, State<Position> state) {
		Maze3d maze = generatedMazes.get(name);

		if (solutions.containsKey(name) && state != null && state.getState().equals(maze.getStartPosition())) {
			lastSolution = solutions.get(name);

			Object[] data = { "display_solution", type, lastSolution };
			CommonData o = new CommonData(data);
			client.write(o);

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

			Object[] data = { "display_solution", type, lastSolution };
			CommonData o = new CommonData(data);
			client.write(o);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
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

				Object[] data = { "display_message", "Maze saved" };
				CommonData o = new CommonData(data);
				client.write(o);

			} catch (FileNotFoundException e) {
				Object[] data = { "display_message", "Error occured while creating file" };
				CommonData o = new CommonData(data);
				client.write(o);
			} catch (IOException e) {
				Object[] data = { "display_message", "Error occured while writing to file" };
				CommonData o = new CommonData(data);
				client.write(o);
			}
		} else {
			Object[] data = { "display_message", "Maze with name " + name + " doesn't exist" };
			CommonData o = new CommonData(data);
			client.write(o);
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

				System.out.println("display maze 2");
				Object[] data = { "display_maze", name, mazeLoaded };
				CommonData o = new CommonData(data);
				client.write(o);

			} catch (FileNotFoundException e) {
				Object[] data = { "display_message", "Error occured while finding file" };
				CommonData o = new CommonData(data);
				client.write(o);
			} catch (IOException e) {
				Object[] data = { "display_message", "Error occured while reading to file" };
				CommonData o = new CommonData(data);
				client.write(o);
			}
		} else {
			System.out.println("display maze 3");
			Object[] data = { "display_maze", name, generatedMazes.get(name),
					" Maze called " + name + " already exist." };
			CommonData o = new CommonData(data);
			client.write(o);
		}
	}

	@Override
	public void displayCrossSection(String name, int index, String section) {
		if (index < 0) {
			Object[] data = { "display_message", "Invalid Index!" };
			CommonData o = new CommonData(data);
			client.write(o);
			return;
		}

		if (!generatedMazes.containsKey(name)) {
			Object[] data = { "display_message", "Maze with name " + name + " doesn't exist" };
			CommonData o = new CommonData(data);
			client.write(o);
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
			Object[] data = { "display_message", "Invalid Section!" };
			CommonData o = new CommonData(data);
			client.write(o);
			return;
		}

		Object[] data = { "display_cross_section", crossSection };
		CommonData o = new CommonData(data);
		client.write(o);
	}

	@Override
	public void exit() {
		Object[] data = { "save_data", generatedMazes, solutions };
		CommonData o = new CommonData(data);
		client.write(o);
	}
}
