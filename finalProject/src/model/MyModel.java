package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.search.DepthFirstSearch;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel implements Model {
	private Controller controller;
	private HashMap<String, Maze3d> generatedMazes;
	private HashMap<String, ArrayList<Position>> solutions;

	public MyModel(Controller controller) {
		this.controller = controller;
		generatedMazes = new HashMap<String, Maze3d>();
		solutions = new HashMap<String, ArrayList<Position>>();
	}

	@Override
	public void generateMaze(String name, int z, int y, int x) {
		// TODO: RECEIVE AN ALGORITHM, AND SELECT METHOD IF NEEDED AND USE IT!
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

	@Override
	public void displayMazeByName(String name) {
		Maze3d maze = generatedMazes.get(name);
		if (maze != null) 
			controller.println(maze.toString());
		else
			controller.println("Maze with name " + name + " doesn't exist");
	}
	
	public void displayCrossSection() {
		
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
				controller.println("The "+name+ " saved seccessfully!");
			} catch (FileNotFoundException e) {
				controller.println("Error occured while creating file");
			} catch (IOException e) {
				controller.println("Error occured while writing to file");
			}
		} else
			controller.println("Maze with name " + name + " doesn't exist");
	}

	@Override
	public void loadMaze(String fileName, String name) {
		if (generatedMazes.containsKey(name)!=true) {
			try{
				InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
					Scanner scanner=new Scanner(in);
					int size=(int)scanner.nextByte();
					byte b[] = new byte[size]; // need the size
					in.read(b);
					in.close();
					Maze3d mazeLoaded = new Maze3d(b);
					generatedMazes.put(name, mazeLoaded);
					controller.println("The "+name+ " loaded seccessfully!");
				
			}
			catch (FileNotFoundException e) {
				controller.println("Error occured while finding file");
			} catch (IOException e) {
				controller.println("Error occured while reading to file");
				}
		}
			
		else
			controller.println("This name exist already!");
			
	}
	

	@Override
	public void solveMaze(String name, Searcher algorithm) {
		// TODO: USE ALGORITHM RECEIVED!
		new Thread(new Runnable() {
			public void run() {
				Maze3d maze = generatedMazes.get(name);
				if (maze != null) {
					Searchable<Position> mazeDomain = new Maze3dDomain(maze);
					Searcher<Position> alg = new DepthFirstSearch<Position>();
					ArrayList<Position> solution = alg.search(mazeDomain);
					
					solutions.put(name, solution);
					controller.println("Solution for " + name + " is ready");
				} else
					controller.println("Maze with name " + name + " doesn't exist");
			}
		}).start();
	}

	@Override
	public void displaySolutionByName(String name) {
		ArrayList<Position> solution = solutions.get(name);
		if(solution != null) 
			controller.println(solution.toString());
		else
			controller.println("Maze " + name + " wasn't solved yet");
	}
}
