package algorithms.search;

import java.util.ArrayList;

/**
 * <h1>Searchable</h1> An interface that reprecent a searchable object (for
 * example, a maze). contains all must mathods to be able to search on that
 * object.
 * <p>
 * 
 * @author Valentina Munoz
 *
 * @param <T>
 *            What the searchable object contains (for example, a 3D maze
 *            containts positions)
 */
public interface Searchable<T> {
	/**
	 * <h1>getInitialState</h1>
	 * <p>
	 * 
	 * @return The searchable object initial state (for example, the 3D maze
	 *         will return the start position)
	 */
	public State<T> getInitialState();

	/**
	 * <h1>getGoalState</h1>
	 * <p>
	 * 
	 * @return The searchable object goal state (for example, the 3D maze will
	 *         return the goal position)
	 */
	public State<T> getGoalState();

	/**
	 * <h1>getAllPossibleStates</h1> Cteare a list of all possibles states
	 * (moves) form the state received
	 * <p>
	 * 
	 * @param s
	 *            The state to check it's neighbors
	 * @return The list of all possible moves
	 */
	public ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
