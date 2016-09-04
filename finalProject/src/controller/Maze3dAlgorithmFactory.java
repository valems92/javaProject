package controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastSelectMethod;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomSelectMethod;
import algorithms.mazeGenerators.SelectMethod;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.CostStateComperator;
import algorithms.search.DepthFirstSearch;
import algorithms.search.Searcher;
import algorithms.search.State;

/**
 * <h1>Maze3dAlgorithmFactory</h1> A factory that create and return an instance
 * of a searcher or generator class, according to received algorithm. Also, if
 * this algorithm use another class (such as a comparator for the searcher or a
 * select cell method for the generator), this script also create an instance of
 * it and send it as an argument in the needed constructor.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 *
 */
public class Maze3dAlgorithmFactory {
	ConcurrentHashMap<String, solveCreator> solve;
	ConcurrentHashMap<String, comperatorCreator> comperator;

	ConcurrentHashMap<String, generateCreator> generate;
	ConcurrentHashMap<String, selectCellCreator> selectCell;

	/**
	 * <h1>Maze3dAlgorithmFactory</h1> Initialize hashmaps with all existing
	 * algorithms to generate or solve a 3D maze
	 * <p>
	 */
	public Maze3dAlgorithmFactory() {
		solve = new ConcurrentHashMap<String, solveCreator>();
		comperator = new ConcurrentHashMap<String, comperatorCreator>();

		generate = new ConcurrentHashMap<String, generateCreator>();
		selectCell = new ConcurrentHashMap<String, selectCellCreator>();

		solve.put("DFS", new DfsCreator());
		solve.put("BFS", new BfsCreator());

		comperator.put("Cost", new CostCreator());

		generate.put("Simple", new SimpleCreator());
		generate.put("GrowingTree", new GrowingTreeCreator());

		selectCell.put("Last", new LastCellCreator());
		selectCell.put("Random", new RandomCellCreator());
	}

	/**
	 * <h1>createSeacherAlgorithm</h1> Create and return a maze 3D searcher
	 * algorithm class
	 * <p>
	 * 
	 * @param alg
	 *            Searcher algorithm wanted
	 * @param arg
	 *            Argument fot the searcher if needed (a Comparator)
	 * @return A instance of the wanted searcher class.
	 */
	public Searcher<Position> createSeacherAlgorithm(String alg, String arg) {
		solveCreator s = solve.get(alg);
		if (s != null)
			return s.create(arg);
		return null;
	}

	/**
	 * <h1>createComperatorAlgorithm</h1> Create and return a state comperator
	 * algorithm class
	 * <p>
	 * 
	 * @param alg
	 *            Comperator algorithm wanted
	 * @returnA instance of the wanted comperator class.
	 */
	public Comparator<State<Position>> createComperatorAlgorithm(String alg) {
		comperatorCreator c = comperator.get(alg);
		if (c != null)
			return c.create();
		return null;
	}

	/**
	 * <h1>createGenerateAlgorithm</h1> Create and return a maze 3D generate
	 * algorithm class
	 * <p>
	 * 
	 * @param alg
	 *            Generate algorithm wanted
	 * @param arg
	 *            Argument fot the generate if needed (a select cell method)
	 * @return A instance of the wanted generate class.
	 */
	public Maze3dGenerator createGenerateAlgorithm(String alg, String arg) {
		generateCreator g = generate.get(alg);
		if (g != null)
			return g.create(arg);
		return null;
	}

	/**
	 * <h1>createSelectCellAlgorithm</h1> Create and return a select cell method
	 * algorithm class
	 * <p>
	 * 
	 * @param alg
	 *            SelectMethod algorithm wanted
	 * @returnA instance of the wanted select cell method class.
	 */
	public SelectMethod createSelectCellAlgorithm(String alg) {
		selectCellCreator s = selectCell.get(alg);
		if (s != null)
			return s.create();
		return null;
	}

	/**
	 * <h1>generateCreator</h1> Create and return a maze 3D generate class
	 * instace
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	private interface generateCreator {
		public Maze3dGenerator create(String arg);
	}

	private class SimpleCreator implements generateCreator {
		@Override
		public Maze3dGenerator create(String arg) {
			return new SimpleMaze3dGenerator();
		}
	}

	private class GrowingTreeCreator implements generateCreator {
		@Override
		public Maze3dGenerator create(String arg) {
			SelectMethod s = createSelectCellAlgorithm(arg);
			if (s != null)
				return new GrowingTreeGenerator(s);
			return null;
		}
	}

	/**
	 * <h1>selectCellCreator</h1> Create and return a select method class
	 * instace
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	private interface selectCellCreator {
		public SelectMethod create();
	}

	private class LastCellCreator implements selectCellCreator {
		@Override
		public SelectMethod create() {
			return new LastSelectMethod();
		}
	}

	private class RandomCellCreator implements selectCellCreator {
		@Override
		public SelectMethod create() {
			return new RandomSelectMethod();
		}
	}

	/**
	 * <h1>solveCreator</h1> Create and return a maze 3D searcher class instace
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	private interface solveCreator {
		public Searcher<Position> create(String arg);
	}

	private class DfsCreator implements solveCreator {
		@Override
		public Searcher<Position> create(String arg) {
			return new DepthFirstSearch<Position>();
		}
	}

	private class BfsCreator implements solveCreator {
		@Override
		public Searcher<Position> create(String arg) {
			Comparator<State<Position>> c = createComperatorAlgorithm(arg);
			if (c != null)
				return new BestFirstSearch<Position>(c);
			return null;
		}
	}

	/**
	 * <h1>comperatorCreator</h1> Create and return a comperator class instace
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	private interface comperatorCreator {
		public Comparator<State<Position>> create();
	}

	private class CostCreator implements comperatorCreator {
		@Override
		public Comparator<State<Position>> create() {
			return new CostStateComperator<Position>();
		}
	}
}
