package algorithms.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <h1>CommonSearcher</h1> A class that contains common methods of searchers
 * <p>
 * 
 * @author Valentina Munoz
 * @implements Searcher
 *
 */
public abstract class CommonSearcher<T> implements Searcher<T> {
	private int evaluatedNodes;

	/**
	 * Initalize the evaluated nodes to zero.
	 */
	public CommonSearcher() {
		evaluatedNodes = 0;
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}

	/**
	 * <h1>increaseEvaluatedNodes</h1> Increase the evaluated nodes when a new
	 * node is visited.
	 */
	protected void increaseEvaluatedNodes() {
		evaluatedNodes++;
	}

	/**
	 * <h1>containsState</h1> Check if the state received exist in the
	 * collection (for example, an ArrayList or a PriorityQueue).
	 * 
	 * @param col
	 *            Collection to look the state in it
	 * @param state
	 *            The state to check if exist in the collection received
	 * @return If the state exist
	 */
	protected boolean containsState(Collection<State<T>> col, State<T> state) {
		for (State<T> s : col) {
			if (state.equals(s))
				return true;
		}
		return false;
	}

	/**
	 * <h1>backTrace</h1> Get the inital and goal states and return a list of T
	 * from the init to the goal
	 * 
	 * @param initState
	 *            The initial state of the searchable object
	 * @param goalState
	 *            The goal state of the searchable object
	 * @return A list of the path solution
	 */
	protected ArrayList<T> backTrace(State<T> initState, State<T> goalState) {
		ArrayList<T> solution = new ArrayList<T>();
		State<T> state = goalState;

		while (state != null) {
			solution.add(state.getState());
			state = state.getCameFrom();
		}

		Collections.reverse(solution);
		return solution;
	}
}
