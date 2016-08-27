package algorithms.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.search.BestFirstSearch;
import algorithms.search.CostStateComperator;
import algorithms.search.DepthFirstSearch;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class Demo {
	public void run() throws IOException {
		Maze3dGenerator mg = new GrowingTreeGenerator(new RandomSelectMethod());
		Maze3d maze = mg.generate(4, 6, 7);
		System.out.println("Maze generated: \n" + maze);

		Searchable<Position> mazeDomain = new Maze3dDomain(maze);

		Searcher<Position> algorithm = new DepthFirstSearch<Position>();
		ArrayList<Position> solution = algorithm.search(mazeDomain);

		System.out.println("DepthFirstSearch: " + solution.toString());
		System.out.println("evaluated nodes: " + algorithm.getNumberOfNodesEvaluated() + "\n");

		algorithm = new BestFirstSearch<Position>(new CostStateComperator<Position>());
		solution = algorithm.search(mazeDomain);

		System.out.println("BestFirstSearch: " + solution.toString());
		System.out.println("evaluated nodes: " + algorithm.getNumberOfNodesEvaluated() + "\n");

		// add test for compress and decompress the maze

		OutputStream out = new MyCompressorOutputStream(new FileOutputStream("1.bit"));
		out.write(maze.toByteArray());// to compress
		out.flush();
		out.close();
		
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream("1.bit"));
			byte b[] = new byte[maze.toByteArray().length];
			in.read(b);
			in.close();
			
			Maze3d loaded = new Maze3d(b);
			System.out.println(loaded.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
