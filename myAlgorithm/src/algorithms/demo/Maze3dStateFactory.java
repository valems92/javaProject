package algorithms.demo;

import java.util.HashMap;

import algorithms.mazeGenerators.Position;
import algorithms.search.State;

/**
 * <h1>Maze3dStateFactory</h1> A factory that create and return 3D maze states
 * according to the direction received.
 * <p>
 * 
 * @author Valentina Munoz
 *
 */
public class Maze3dStateFactory {
	HashMap<String, Creator> stateCreators;
	public static final int COST2D = 10;
	public static final int COST3D = 15;

	public Maze3dStateFactory() {
		stateCreators = new HashMap<String, Creator>();

		stateCreators.put("Up", new UpCreator());
		stateCreators.put("Down", new DownCreator());
		stateCreators.put("Backward", new BackwardCreator());
		stateCreators.put("Forward", new ForwardCreator());
		stateCreators.put("Left", new LeftCreator());
		stateCreators.put("Right", new RightCreator());
	}

	public State<Position> createState(String dir, Position p) {
		Creator c = stateCreators.get(dir);
		if (c != null)
			return c.create(p);
		return null;
	}

	private interface Creator {
		public State<Position> create(Position p);
	}

	private class UpCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z + 1, p.y, p.x));
			state.setCost(COST3D);
			return state;
		}
	}

	private class DownCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z - 1, p.y, p.x));
			state.setCost(COST3D);
			return state;
		}
	}

	private class BackwardCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z, p.y - 1, p.x));
			state.setCost(COST2D);
			return state;
		}
	}

	private class ForwardCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z, p.y + 1, p.x));
			state.setCost(COST2D);
			return state;
		}
	}

	private class LeftCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z, p.y, p.x - 1));
			state.setCost(COST2D);
			return state;
		}
	}

	private class RightCreator implements Creator {
		@Override
		public State<Position> create(Position p) {
			State<Position> state = new State<Position>(new Position(p.z, p.y, p.x + 1));
			state.setCost(COST2D);
			return state;
		}
	}
}
