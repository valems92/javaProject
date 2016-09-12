package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

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

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;

	public GameMaze3dModel() {
		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, Solution<Position>>();

		executorGenerate = Executors.newFixedThreadPool(Properties.properites.getNumberOfThreads());
		executorSolve = Executors.newFixedThreadPool(Properties.properites.getNumberOfThreads());
	}

	@Override
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {
		if (generatedMazes.containsKey(name)) {
			// presenter.println("Maze " + name + " already exist");
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
			// OutputStream out = new MyCompressorOutputStream(file);

			for (Maze3d maze : generatedMazes.values()) {
				out.write(maze.toByteArray());
			}
			out.finish();

		} catch (IOException e) {
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
	}

	@Override
	public void displayCrossSection(String name, int index, String section) {
		StringBuilder crossBuilder = new StringBuilder();
		if (index < 0){
			crossBuilder.append("MessageCommand " + "Invalid Index!");
			setChanged();
			notifyObservers(crossBuilder.toString());
		}
		else if (generatedMazes.containsKey(name)) {
			Maze3d maze = generatedMazes.get(name);

			switch (section) {
			case "x":
			case "X":
				crossBuilder.append("MessageCommand " + (PrintMaze2d(maze.getCrossSectionByX(index), maze.getZ(), maze.getY())));
				setChanged();
				notifyObservers(crossBuilder.toString());
				break;

			case "y":
			case "Y":
				crossBuilder.append("MessageCommand " + (PrintMaze2d(maze.getCrossSectionByY(index), maze.getZ(), maze.getX())));
				setChanged();
				notifyObservers(crossBuilder.toString());
				break;

			case "z":
			case "Z":
				crossBuilder.append("MessageCommand " + (PrintMaze2d(maze.getCrossSectionByZ(index), maze.getY(), maze.getX())));
				setChanged();
				notifyObservers(crossBuilder.toString());
				break;

			default:
				crossBuilder.append("MessageCommand "+ "Invalid Section!");
				setChanged();
				notifyObservers(crossBuilder.toString());
				break;
			}
		} else
			crossBuilder.append(("MessageCommand "+ "Maze with name " + name + " doesn't exist"));
		
	}
	
	/**
	 * <h1>PrintMaze2d</h1> Help to print the 2d maze from cross by section
	 * methods
	 * 
	 * @param maze
	 *            - 2d maze that create by cross by section method
	 * @param end1
	 *            - Total size of argument1 of 2d maze
	 * @param end2
	 *            - Total size of argument2 of 2d maze
	 * @return String of a 2d maze that created by "cross by section" method
	 */
	public static String PrintMaze2d(int[][] maze, int end1, int end2) {
		StringBuilder sb = new StringBuilder();
		for (int start1 = 0; start1 < end1; start1++) {
			for (int start2 = 0; start2 < end2; start2++) {
				sb.append(maze[start1][start2]);
				//sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
