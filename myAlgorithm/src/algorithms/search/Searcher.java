package algorithms.search;

import java.util.ArrayList;

/**
 * <h1>Searcher</h1> An interface that looks for a solution in a searchable
 * object.
 * <p>
 * 
 * @author Valentina Munoz
 *
 * @param <T>
 *            What the searchable object contains (for example, a 3D maze
 *            containts positions)
 */
public interface Searcher<T> {
	/**
	 * <h1>search</h1> Get a searchable object (for example, maze3dDomain) and
	 * create a list of the solution path.
	 * <p>
	 * 
	 * @param s
	 *            A searchable object to look for it's solution
	 * @return A list of the solution path
	 */
	public ArrayList<T> search(Searchable<T> s);

	/**
	 * <h1>getNumberOfNodesEvaluated</h1> When a new node is evaluated, a
	 * counter incresae. i.e, check how many nodes were evaluated for getting
	 * the solution.
	 * <p>
	 * 
	 * @return The number of the total nodes evaluated
	 */
	public int getNumberOfNodesEvaluated();
}
