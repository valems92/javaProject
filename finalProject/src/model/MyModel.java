package model;

import java.util.HashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.RandomSelectMethod;
import controller.Controller;

public class MyModel implements Model {
	private Controller controller;
	private HashMap<String, Maze3d> generatedMazes = new HashMap<String, Maze3d>();

	public MyModel(Controller controller) {
		this.controller = controller;
	}

	public void generateMaze(String name, int z, int y, int x) {
		new Thread(new Runnable() {
			public void run() {
				if (generatedMazes.containsKey(name)) {
					controller.println("Maze " + name + " already exist");
				} else {
					Maze3dGenerator mg = new GrowingTreeGenerator(new RandomSelectMethod());
					Maze3d maze = mg.generate(z, y, x);
					generatedMazes.put(name, maze);
					controller.println("Maze " + name + " is ready");
				}
			}
		}).start();
	}

}
