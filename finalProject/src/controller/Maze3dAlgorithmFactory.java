package controller;

import java.util.Comparator;
import java.util.HashMap;

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

public class Maze3dAlgorithmFactory {
	HashMap<String, solveCreator> solve;
	HashMap<String, comperatorCreator> comperator;
	
	HashMap<String, generateCreator> generate;
	HashMap<String, selectCellCreator> selectCell;
	
	public Maze3dAlgorithmFactory() {
		solve = new HashMap<String, solveCreator>();
		comperator = new HashMap<String, comperatorCreator>();

		generate = new HashMap<String, generateCreator>();
		selectCell = new HashMap<String, selectCellCreator>();
		
		solve.put("DFS", new DfsCreator());
		solve.put("BFS", new BfsCreator());
		
		comperator.put("Cost", new CostCreator());
		
		generate.put("Simple", new SimpleCreator());
		generate.put("GrowingTree", new GrowingTreeCreator());
		
		selectCell.put("Last", new LastCellCreator());
		selectCell.put("Random", new RandomCellCreator());
	}
	
	public  Searcher<Position> createSeacherAlgorithm(String alg, String arg){
		solveCreator s = solve.get(alg);
		if(s != null)
			return s.create(arg);
		return null;
	}
	
	public  Comparator<State<Position>> createComperatorAlgorithm(String alg){
		comperatorCreator c = comperator.get(alg);
		if(c != null)
			return c.create();
		return null;
	}
	
	public  Maze3dGenerator createGenerateAlgorithm(String alg, String arg){
		generateCreator g = generate.get(alg);
		if(g != null)
			return g.create(arg);
		return null;
	}
	
	public  SelectMethod createSelectCellAlgorithm(String alg){
		selectCellCreator s = selectCell.get(alg);
		if(s != null)
			return s.create();
		return null;
	}
	
	/**
	 * <h1>generateCreator</h1>
	 * @author 
	 *
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
			if(s != null)
				return new GrowingTreeGenerator(s);
			return null;
		}
	}
	
	/**
	 * <h1>selectCellCreator</h1>
	 * @author 
	 *
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
	 * <h1>solveCreator</h1>
	 * @author 
	 *
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
			if(c != null)
				return new BestFirstSearch<Position>(c);
			return null;
		}
	}
	
	/**
	 * <h1>comperatorCreator</h1>
	 * @author 
	 *
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
