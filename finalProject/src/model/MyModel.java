package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel implements Model {
	private Controller controller;
	public HashMap<String, Maze3d> generatedMazes;
	private HashMap<String, ArrayList<Position>> solutions;

	public MyModel(Controller controller) {
		this.controller = controller;
		generatedMazes = new HashMap<String, Maze3d>();
		solutions = new HashMap<String, ArrayList<Position>>();
	}
	
	public HashMap<String, Maze3d> getArrays(){
		return generatedMazes;
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

	public void displayCrossSection(String name, int index, String section) {
		if(index<0){
			controller.println("Invalid Index!");
		}
		

		else if(generatedMazes.containsKey(name)){
			Maze3d maze=generatedMazes.get(name);
			
			switch (section) {
			case "x":
			case "X":  controller.println(PrintMaze2d(maze.getCrossSectionByX(index),maze.getZ(),maze.getY()));
		
				break;
				
			case "y":
			case "Y":  controller.println(PrintMaze2d(maze.getCrossSectionByY(index),maze.getZ(),maze.getX()));
				break;
				
			case "z":
			case "Z":  controller.println(PrintMaze2d(maze.getCrossSectionByZ(index),maze.getY(),maze.getX()));
				break;

			default: controller.println("Invalid Section!");
				break;
			}
		}
		else
			controller.println("Maze with name " + name + " doesn't exist");
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
				controller.println("The " + name + " saved seccessfully!");
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
				controller.println("The " + name + " loaded seccessfully!");

			} catch (FileNotFoundException e) {
				controller.println("Error occured while finding file");
			} catch (IOException e) {
				controller.println("Error occured while reading to file");
			}
		} else
			controller.println("This name exist already");

	}

	@Override
	public void solveMaze(String name, String algorithmClassName, String comperatorClassName) {
		if (solutions.containsKey(name)) 
			return;
		
		new Thread(new Runnable() {
			public void run() {
				Maze3d maze = generatedMazes.get(name);
				if (maze != null) {
					try {
						Searchable<Position> mazeDomain = new Maze3dDomain(maze);
						
						Class<?> cls = Class.forName("algorithms.search." + algorithmClassName);
						Searcher<Position> searcher;
						
						//boolean hasPrams = hasParameterConstructor(cls);
						/*if(hasPrams){
							Class<?> clsComp = Class.forName("algorithms.search." + comperatorClassName);
							searcher = (Searcher<Position>) cls.newInstance(clsComp.ne);
						} else */
						searcher = (Searcher<Position>) cls.newInstance();
						
						ArrayList<Position> solution = searcher.search(mazeDomain);
						solutions.put(name, solution);
						
						controller.println("Solution for " + algorithmClassName + " is ready");
					} catch (ClassNotFoundException e) {
						controller.println("The algorithm doesn't exist");
					} catch (InstantiationException e) {
						controller.println(e.getMessage());
					} catch (IllegalAccessException e) {
						controller.println(e.getMessage());
					}
				} else
					controller.println("Maze with name " + name + " doesn't exist");
			}
		}).start();
	}
	
	private boolean hasParameterConstructor(Class<?> cls) {
	    for (Constructor<?> constructor : cls.getConstructors()) {
	        if (constructor.getParameterCount() != 0) { 
	            return true;
	        }
	    }
	    return false;
	}

	@Override
	public void displaySolutionByName(String name) {
		ArrayList<Position> solution = solutions.get(name);
		if (solution != null)
			controller.println(solution.toString());
		else
			controller.println("Maze " + name + " wasn't solved yet");
	}
	
	public static String PrintMaze2d(int[][] maze, int end1,int end2){
		StringBuilder sb=new StringBuilder();
		for (int start1=0;start1<end1;start1++){
			for(int start2=0;start2<end2;start2++){
						sb.append(maze[start1][start2]);
				}
				sb.append("\n");
			}

		return sb.toString();
		
	}
}
