
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Presenter;

public class GameMaze3dModel extends Observable implements Model {
	
	private Presenter presenter;
	public ConcurrentHashMap<String, Maze3d> generatedMazes;
	private ConcurrentHashMap<String, ArrayList<Position>> solutions;

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;
	
	//AtomicBoolean done=new AtomicBoolean(false);

	public GameMaze3dModel(/*Presenter presenter*/) {
		//this.presenter = presenter;

		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, ArrayList<Position>>();

		executorGenerate = Executors.newSingleThreadExecutor();
		executorSolve = Executors.newSingleThreadExecutor();
	}
	
	

	/**
	 * @return the presenter
	 */
	public Presenter getPresenter() {
		return presenter;
	}



	/**
	 * @param presenter the presenter to set
	 */
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	public void notify(String args){
		this.notifyObservers();
	}



	@Override
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {
		executorGenerate.execute(new Runnable() {
			public void run() {
				if (generatedMazes.containsKey(name)) {
					////presenter.println("Maze " + name + " already exist");
				} else {
					Maze3d maze = mg.generate(z, y, x);
					generatedMazes.put(name, maze);
//					done.set(true);
					//presenter.println("Maze " + name + " is ready");
					setChanged();
					notifyObservers("generateResult " + name);
					
				}
			}
		});
//		if(done.get()){
//			setChanged();
//			this.notifyObservers("generateResult " + name);
//		}

	}

	@Override
	public void displayMazeByName(String name) {
		Maze3d maze = generatedMazes.get(name);
		if (maze != null){}
			this.notifyObservers("DISPLAY_MSG "+maze.toString());
		//else{}
			//presenter.println("Maze with name " + name + " doesn't exist");
	}

	public void displayCrossSection(String name, int index, String section) {
		if (index < 0){}
			////presenter.println("Invalid Index!");

		else if (generatedMazes.containsKey(name)) {
			Maze3d maze = generatedMazes.get(name);

			switch (section) {
			case "x":
			case "X":
				////presenter.println(PrintMaze2d(maze.getCrossSectionByX(index), maze.getZ(), maze.getY()));

				break;

			case "y":
			case "Y":
				////presenter.println(PrintMaze2d(maze.getCrossSectionByY(index), maze.getZ(), maze.getX()));
				break;

			case "z":
			case "Z":
				////presenter.println(PrintMaze2d(maze.getCrossSectionByZ(index), maze.getY(), maze.getX()));
				break;

			default:
				////presenter.println("Invalid Section!");
				break;
			}
		} else{}
			//presenter.println("Maze with name " + name + " doesn't exist");
	}

	@Override
	public void saveMazeByName(String name, String fileName) {
		Maze3d maze = generatedMazes.get(name);
		if (maze != null) {
			try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
				out.write(maze.toByteArray());
				out.flush();
				out.close();
				//presenter.println("The " + name + " saved seccessfully!");
			} catch (FileNotFoundException e) {
				//presenter.println("Error occured while creating file");
			} catch (IOException e) {
				//presenter.println("Error occured while writing to file");
			}
		} else{}
			//presenter.println("Maze with name " + name + " doesn't exist");
	}

	@Override
	public void loadMaze(String fileName, String name) {
		if (!generatedMazes.containsKey(name)) {
			try {
				InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));

				File file = new File(fileName);
				FileInputStream reader = new FileInputStream(file);
				byte b[] = new byte[reader.read()]; // need the size
				reader.close();

				in.read(b);
				in.close();
				Maze3d mazeLoaded = new Maze3d(b);
				generatedMazes.put(name, mazeLoaded);
				//presenter.println("The " + name + " loaded seccessfully!");

			} catch (FileNotFoundException e) {
				//presenter.println("Error occured while finding file");
			} catch (IOException e) {
				//presenter.println("Error occured while reading to file");
			}
		} else{}
			//presenter.println("This name exist already");

	}

	@Override
	public void solveMaze(String name, Searcher<Position> searcher) {
		if (solutions.containsKey(name)) {
			//presenter.println("Solution for " + name + " already exist");
			return;
		}

		executorSolve.execute(new Runnable() {
			public void run() {
				Maze3d maze = generatedMazes.get(name);
				if (maze != null) {
					Searchable<Position> mazeDomain = new Maze3dDomain(maze);
					ArrayList<Position> solution = searcher.search(mazeDomain);

					solutions.put(name, solution);
					//presenter.println("Solution for " + name + " is ready");
				} else{}
					//presenter.println("Maze with name " + name + " doesn't exist");
			}
		});
	}

	@Override
	public void displaySolutionByName(String name) {
		ArrayList<Position> solution = solutions.get(name);
		if (solution != null){}
			//presenter.println(solution.toString());
		else{}
			//presenter.println("Maze " + name + " wasn't solved yet");
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
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
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
	public void getGenerateResult(String name) {
		Maze3d maze=generatedMazes.get(name);
		presenter.SendMazeToView(maze.toString());
	}




}
