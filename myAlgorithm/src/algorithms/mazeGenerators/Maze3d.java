package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Maze3d</h1> Reprecent a 3D maze. Contains it's dimmension, and a 3D
 * array. The maze has a start position and goal position. Must recive the maze
 * dimmension in order to create an instance of this class.
 * <p>
 * 
 * @author Valentina Munoz
 */
public class Maze3d {
	private int[][][] maze;
	private int z;
	private int y;
	private int x;
	private Position startPosition;
	private Position goalPosition;

	public static final int FREE = 0;
	public static final int WALL = 1;

	/**
	 * Create the 3D array according to the dimmension received.
	 * 
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows in the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 */
	public Maze3d(int z, int y, int x) {
		this.z = z;
		this.y = y;
		this.x = x;
		maze = new int[z][y][x];
		this.initMazeValue();
	}

	/**
	 * Create a new maze by data received.
	 * 
	 * @param b
	 *            Byte array with all maze data
	 */
	public Maze3d(byte[] b) {
		// size
		this.z = (int) b[0];
		this.y = (int) b[1];
		this.x = (int) b[2];
		maze = new int[z][y][x];

		startPosition = new Position((int) b[3], (int) b[4], (int) b[5]);
		goalPosition = new Position((int) b[6], (int) b[7], (int) b[8]);

		// values
		int index = 9;
		for (int i = 0; i < z; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < x; k++) {
					maze[i][j][k] = (int) b[index];
					index++;
				}
			}
		}
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	public Position getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}

	/**
	 * Change the value of the position received.
	 * 
	 * @param p
	 *            The position to change the value of it
	 * @param value
	 *            The new position value
	 */
	public void setCellValue(Position p, int value) {
		maze[p.z][p.y][p.x] = value;
	}

	/**
	 * @param p
	 *            Position in t he 3D maze
	 * @return The value of the position received
	 */
	public int getCellvalue(Position p) {
		return maze[p.z][p.y][p.x];
	}

	/**
	 * <h1>getPossibleMoves</h1> Create a list of all possible moved from the
	 * recived position.
	 * <p>
	 * 
	 * @param p
	 *            Position to check possoble moves
	 * @return A string list of possible moves
	 */
	public ArrayList<String> getPossibleMoves(Position p) {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		if (p.z > 0)
			if (maze[p.z - 1][p.y][p.x] == FREE)
				possibleMoves.add("Down");

		if (p.z < (this.z - 1))
			if (maze[p.z + 1][p.y][p.x] == FREE)
				possibleMoves.add("Up");

		if (p.y > 0)
			if (maze[p.z][p.y - 1][p.x] == FREE)
				possibleMoves.add("Backward");

		if (p.y < (this.y - 1))
			if (maze[p.z][p.y + 1][p.x] == FREE)
				possibleMoves.add("Forward");

		if (p.x > 0)
			if (maze[p.z][p.y][p.x - 1] == FREE)
				possibleMoves.add("Left");

		if (p.x < (this.x - 1))
			if (maze[p.z][p.y][p.x + 1] == FREE)
				possibleMoves.add("Right");

		return possibleMoves;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < z; i++) {
			for (int w = 0; w < y; w++) {
				for (int r = 0; r < x; r++) {
					if (this.startPosition.equals(i, w, r))
						sb.append("E");
					else if (this.goalPosition.equals(i, w, r))
						sb.append("X");
					else
						sb.append(maze[i][w][r]);
					sb.append(" ");
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * <h1>getNeighborsByValue</h1> Create a list of all neighbors with the
	 * value received (FREE/WALL)
	 * <p>
	 * 
	 * @param allNeighbors
	 *            A list of all existing nighbors
	 * @param value
	 *            : The value wanted in the cells (FREE/WALL)
	 * @return A list of all nighbors width thhe value received
	 */
	public ArrayList<Position> getNeighborsByValue(ArrayList<Position> allNeighbors, int value) {
		ArrayList<Position> closedNeighbors = new ArrayList<Position>();

		for (Position n : allNeighbors) {
			if (getCellvalue(n) == value)
				closedNeighbors.add(n);
		}

		return closedNeighbors;
	}

	/**
	 * <h1>getCrossSectionByX</h1> Get a column in the 3D maze and build a 2D
	 * array that contains the received column of each floor.
	 * <p>
	 * 
	 * @param x
	 *            Column wanted
	 * @return A 2D array of cross section by the x received
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException {
		int[][] arr = new int[z][y];
		for (int i = 0; i < z; i++) {
			for (int w = 0; w < y; w++) {
				arr[i][w] = maze[i][w][x];
			}
		}
		return arr;
	}

	/**
	 * <h1>getCrossSectionByY</h1> Get a row in the 3D maze and build a 2D array
	 * that contains the received row of each floor.
	 * <p>
	 * 
	 * @param y
	 *            Row wanted
	 * @return A 2D array of cross section by the y received
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByY(int y) throws IndexOutOfBoundsException {
		int[][] arr = new int[z][x];
		for (int i = 0; i < z; i++) {
			for (int w = 0; w < x; w++) {
				arr[i][w] = maze[i][y][w];
			}
		}
		return arr;
	}

	/**
	 * <h1>getCrossSectionByZ</h1> Get a floor in the 3D maze and build a 2D
	 * array that contains the floor recevied.
	 * <p>
	 * 
	 * @param z
	 *            Floor wanted
	 * @return A 2D array of cross section by the z received
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByZ(int z) throws IndexOutOfBoundsException {
		int[][] arr = new int[y][x];
		for (int i = 0; i < y; i++) {
			for (int w = 0; w < x; w++) {
				arr[i][w] = maze[z][i][w];
			}
		}
		return arr;
	}

	/**
	 * <h1>toByteArray</h1> Create an array of bytes that contains all 3D maze
	 * data.
	 * 
	 * @return The array
	 */
	public byte[] toByteArray() {
		byte[] byteMaze = new byte[z * y * x + 9];

		// size
		byteMaze[0] = (byte) z;
		byteMaze[1] = (byte) y;
		byteMaze[2] = (byte) x;

		// start position
		byteMaze[3] = (byte) startPosition.z;
		byteMaze[4] = (byte) startPosition.y;
		byteMaze[5] = (byte) startPosition.x;

		// goal position
		byteMaze[6] = (byte) goalPosition.z;
		byteMaze[7] = (byte) goalPosition.y;
		byteMaze[8] = (byte) goalPosition.x;

		// values
		int index = 9;
		for (int i = 0; i < z; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < x; k++) {
					byteMaze[index] = (byte) (maze[i][j][k]);
					index++;
				}
			}
		}

		return byteMaze;
	}

	/**
	 * <h1>initMazeValue</h1> Initialize all the cells in maze to be WALLS.
	 */
	private void initMazeValue() {
		for (int i = 0; i < z; i++) {
			for (int w = 0; w < y; w++) {
				for (int r = 0; r < x; r++) {
					maze[i][w][r] = WALL;
				}
			}
		}
	}
}
