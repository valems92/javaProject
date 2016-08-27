package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * <h1>DepthFirstSearch</h1> Search for a solution in a searchable object using
 * the DFS algorithm.
 * <p>
 * 
 * @author Valentina Munoz
 * @extends CommonSearcher
 */
public class DepthFirstSearch<T> extends CommonSearcher<T> {

	@Override
	public ArrayList<T> search(Searchable<T> s) {
		Stack<State<T>> openList = new Stack<State<T>>();
		HashSet<State<T>> visited = new HashSet<State<T>>();

		openList.add(s.getInitialState());

		while (!openList.isEmpty()) {
			State<T> n = openList.pop();
			increaseEvaluatedNodes();

			visited.add(n);

			if (n.equals(s.getGoalState())) {
				s.getGoalState().setCameFrom(n.getCameFrom());
				return backTrace(s.getInitialState(), n);
			}

			ArrayList<State<T>> successors = s.getAllPossibleStates(n);
			for (State<T> state : successors) {
				boolean vis = visited.contains(state);
				if (!containsState(openList, state) && !vis) {
					state.setCameFrom(n);
					state.setCost(n.getCost() + state.getCost());
					openList.add(state);
				}
			}
		}
		return null;
	}
}
