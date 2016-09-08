package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;

public class GameMaze3dModel extends Observable implements Model {
	public ConcurrentHashMap<String, Maze3d> generatedMazes;
	private ConcurrentHashMap<String, ArrayList<Position>> solutions;

	private ExecutorService executorGenerate;
	private ExecutorService executorSolve;

	// AtomicBoolean done=new AtomicBoolean(false);

	public GameMaze3dModel() {
		generatedMazes = new ConcurrentHashMap<String, Maze3d>();
		solutions = new ConcurrentHashMap<String, ArrayList<Position>>();

		executorGenerate = Executors.newSingleThreadExecutor();
		executorSolve = Executors.newSingleThreadExecutor();
	}

	@Override
	public void generateMaze(String name, int z, int y, int x, Maze3dGenerator mg) {
		if (generatedMazes.containsKey(name)) {
			// presenter.println("Maze " + name + " already exist");
			return;
		}

		executorGenerate.execute(new Runnable() {
			public void run() {
				Maze3d maze = mg.generate(z, y, x);
				generatedMazes.put(name, maze);

				setChanged();
				notifyObservers("display_maze " + name);
			}
		});
	}

	@Override
	public Maze3d getMazeByName(String name) {
		return generatedMazes.get(name);
	}

	public void solveMaze(String name, Searcher<Position> searcher) {
		if (solutions.containsKey(name)) {
			// presenter.println("Solution for " + name + " already exist");
			return;
		}

		executorSolve.execute(new Runnable() {
			public void run() {
				Maze3d maze = generatedMazes.get(name);
				
				Searchable<Position> mazeDomain = new Maze3dDomain(maze);
				ArrayList<Position> solution = searcher.search(mazeDomain);

				solutions.put(name, solution);
				
				setChanged();
				notifyObservers("display_solution " + name);
			}
		});
	}

	public ArrayList<Position> getSolutionByMazeName(String name){
		return solutions.get(name);
	}
}
