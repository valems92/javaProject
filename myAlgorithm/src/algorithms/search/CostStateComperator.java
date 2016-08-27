package algorithms.search;

import java.util.Comparator;

/**
 * <h1>CostStateComperator</h1> Compare two states by their cost.
 * <p>
 * 
 * @author Valentina Munoz
 */
public class CostStateComperator<T> implements Comparator<State<T>> {

	@Override
	public int compare(State<T> s1, State<T> s2) {
		return (int) (s1.getCost() - s2.getCost());
	}

}
