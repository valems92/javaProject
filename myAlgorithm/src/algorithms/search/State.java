package algorithms.search;

/**
 * <h1>State</h1> Represent a state in the searchable object. It contains a
 * state data member that represent the state, how much it cost to reach this
 * state and a camfrom state.
 * <p>
 * 
 * @author Valentina Munoz
 *
 * @param <T>
 *            The way to represent a state (for example, in the 3D maze the
 *            state is represented by a position)
 */
public class State<T> {
	private T state;
	private double cost;
	private State<T> cameFrom;

	/**
	 * Initialize the state and the cost to zero.
	 * 
	 * @param state
	 *            The way to represent this state.
	 */
	public State(T state) {
		this.state = state;
		cost = 0;
	}

	@Override
	public boolean equals(Object obj) {
		return this.state.equals(((State<T>) obj).state);
	}

	@Override
	public int hashCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(state.toString());
		return buffer.toString().hashCode();
	}

	public T getState() {
		return state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
}
