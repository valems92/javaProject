package algorithms.mazeGenerators;

/**
 * <h1>Maze3dGenerator</h1> An interface that contains all must mathods to
 * generate a 3D maze.
 * <p>
 * 
 * @author Valentina Munoz
 */
public interface Maze3dGenerator {
	/**
	 * <h1>generate</h1> Create a new 3D maze according to a choosen algorithm
	 * and return it.
	 * 
	 * @param z
	 *            Total floors wanted in the 3D maze
	 * @param y
	 *            Total rows wanted in the 3D maze
	 * @param x
	 *            Total columns wanted in the 3D maze
	 * @return A 3D maze with the desired dimmension
	 */
	public Maze3d generate(int z, int y, int x);

	/**
	 * <h1>measureAlgorithmTime</h1> Calculate the time it take to crate a 3D
	 * maze with the received dimension and return it (in seconds).
	 * <p>
	 * 
	 * @param z
	 *            Total floors in the 3D maze
	 * @param y
	 *            Total rows in the 3D maze
	 * @param x
	 *            Total columns in the 3D maze
	 * @return Seconds it take to crate the 3D maze
	 */
	public String measureAlgorithmTime(int z, int y, int x);
}