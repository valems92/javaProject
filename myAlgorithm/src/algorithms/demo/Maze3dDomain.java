package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
 * <h1>Maze3dDomain</h1> Represent a searchable object of type 3D maze.
 * <p>
 * 
 * @author Valentina Munoz
 * @implements Searchable
 *
 */
public class Maze3dDomain implements Searchable<Position> {
	private Maze3d maze;
	private Maze3dStateFactory factory;

	public Maze3dDomain(Maze3d maze) {
		this.maze = maze;
	}

	@Override
	public State<Position> getInitialState() {
		return new State<Position>(maze.getStartPosition());
	}

	@Override
	public State<Position> getGoalState() {
		return new State<Position>(maze.getGoalPosition());
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		if (factory == null)
			factory = new Maze3dStateFactory();

		Position sPos = s.getState();
		ArrayList<String> possibleMoves = maze.getPossibleMoves(sPos);
		ArrayList<State<Position>> possibleStates = new ArrayList<State<Position>>();

		for (String move : possibleMoves) {
			State<Position> state = factory.createState(move, sPos);
			possibleStates.add(state);
		}
		return possibleStates;
	}

}
