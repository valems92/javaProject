package algorithms.mazeGenerators;

import java.util.Random;

/**
 * <h1>CommonMaze3dGenerator</h1> A class that contains common methods of 3D
 * maze generators.
 * <p>
 * 
 * @author Valentina Munoz
 * @implements Maze3dGenerator
 *
 */
public abstract class CommonMaze3dGenerator implements Maze3dGenerator {
	@Override
	public abstract Maze3d generate(int z, int y, int x);

	@Override
	public String measureAlgorithmTime(int z, int y, int x) {
		long startTime = System.currentTimeMillis();
		this.generate(z, y, x);
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		return String.valueOf(duration);
	}

	/**
	 * <h1>chooseRandomPosition</h1> Choose random position in maze according to
	 * it's dimensions, and return it.
	 * <p>
	 * 
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows in the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 * @return A random position in maze
	 */
	public Position chooseRandomPosition(int z, int y, int x) {
		Random rand = new Random();
		int newZ = rand.nextInt(z), newY = rand.nextInt(y), newX = rand.nextInt(x);
		return new Position(newZ, newY, newX);
	}
}