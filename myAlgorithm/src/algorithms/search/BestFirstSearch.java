package algorithms.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * <h1>BestFirstSearch</h1> Search for the better solution in a searchable
 * object using the BFS algorithm. This script allows you to specify how to
 * choose the better solution. like by best cost (cost comparator).
 * <p>
 * 
 * @author Valentina Munoz
 * @extends CommonSearcher
 */
public class BestFirstSearch<T> extends CommonSearcher<T> {
	private PriorityQueue<State<T>> openList;

	/**
	 * Initialize the openList data member.
	 * 
	 * @param comp
	 *            The way to comparate between states.
	 */
	public BestFirstSearch(Comparator<State<T>> comp) {
		super();
		openList = new PriorityQueue<State<T>>(comp);
	}

	@Override
	public ArrayList<T> search(Searchable<T> s) {
		openList.add(s.getInitialState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();

		while (openList.size() > 0) {
			// get the best node from openList
			State<T> n = popOpenList();
			closedSet.add(n);

			// if node is the goal state, finish the algorithm and return the
			// solution
			if (n.equals(s.getGoalState())) {
				s.getGoalState().setCameFrom(n.getCameFrom());
				return backTrace(s.getInitialState(), n);
			}

			// get all possible states from the current node
			ArrayList<State<T>> successors = s.getAllPossibleStates(n);
			for (State<T> state : successors) {
				// if the state wasn't evalated at all before, add it to the
				// openList
				if (!closedSet.contains(state) && !containsState(openList, state)) {
					state.setCameFrom(n);
					state.setCost(state.getCost() + n.getCost());
					openList.add(state);
				}
				// else if it's already in openList, fix it's data if needed
				else
					checkNewPath(state, n, n.getCost() + state.getCost());
			}
		}
		return null;
	}

	/**
	 * <h1>popOpenList</h1> Increase the evaluated Nodes counter. Also, Remove
	 * and return the best node in openList according to the comperator we
	 * wanted
	 * <p>
	 * 
	 * @return The selected State
	 */
	protected State<T> popOpenList() {
		increaseEvaluatedNodes();
		return openList.poll();
	}

	/**
	 * <h1>checkNewPath</h1> Get a state, and if it exist in the openList, check
	 * if the new cost is better than previous one.
	 * <p>
	 * 
	 * @param s
	 *            State to check if exist in openList
	 * @param camefrom
	 *            The state to set as the came from of s if the cost is better
	 * @param newCost
	 *            The new cost
	 */
	private void checkNewPath(State<T> s, State<T> camefrom, double newCost) {
		for (State<T> state : openList) {
			if (state.equals(s)) {
				if (state.getCost() > newCost) {
					state.setCost(newCost);
					state.setCameFrom(camefrom);
				}
			}
		}
	}
}
