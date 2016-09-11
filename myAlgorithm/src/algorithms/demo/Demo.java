package algorithms.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.CostStateComperator;
import algorithms.search.DepthFirstSearch;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * <h1>Demo</h1> A demo class of maze generator algorithm, search algorithm and
 * compress/decompress classes.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 *
 */
public class Demo {
	public void run() throws Exception {
		Maze3dGenerator mg = new GrowingTreeGenerator(new RandomSelectMethod());
		Maze3d maze = mg.generate(5, 5, 5);
		System.out.println("Maze generated: \n" + maze);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("test.zip")));
			System.out.println(maze);
			out.writeObject(maze);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Searchable<Position> mazeDomain = new Maze3dDomain(maze);

		Searcher<Position> algorithm = new DepthFirstSearch<Position>();
		Solution<Position> solution = algorithm.search(mazeDomain);

		System.out.println("DepthFirstSearch: " + solution.toString());
		System.out.println("evaluated nodes: " + algorithm.getNumberOfNodesEvaluated() + "\n");

		try {
			algorithm = new BestFirstSearch<Position>(new CostStateComperator<Position>());
			solution = algorithm.search(mazeDomain);

			System.out.println("BestFirstSearch: " + solution.toString());
			System.out.println("evaluated nodes: " + algorithm.getNumberOfNodesEvaluated() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		OutputStream out = new MyCompressorOutputStream(new FileOutputStream("1.bit"));
		out.write(maze.toByteArray());
		out.flush();
		out.close();

		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream("1.bit"));

			File file = new File("1.bit");
			FileInputStream reader = new FileInputStream(file);
			byte b[] = new byte[(reader.read() * Byte.MAX_VALUE) + reader.read()*2];
			reader.close();

			in.read(b);
			in.close();

			Maze3d loaded = new Maze3d(b);
			System.out.println(loaded.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
