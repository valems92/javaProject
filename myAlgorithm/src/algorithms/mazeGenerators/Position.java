package algorithms.mazeGenerators;

/**
 * <h1>Position</h1> Reprecent a cell (a location) in a 3D maze. It has z, y and
 * x coordinates.
 * <p>
 * 
 * @author Valentina Munoz
 */
public class Position {
	public int z;
	public int y;
	public int x;

	/**
	 * Initialize the position coordinates.
	 * 
	 * @param z
	 * @param y
	 * @param x
	 */
	public Position(int z, int y, int x) {
		this.z = z;
		this.y = y;
		this.x = x;
	}

	@Override
	public String toString() {
		return "(" + this.z + "," + this.y + "," + this.x + ")";
	}

	/**
	 * <h1>equals</h1> Check if the z, y, x received are the same as this
	 * positions coordinates.
	 * 
	 * @param z
	 * @param y
	 * @param x
	 * @return If are the received coordinates the same as the positions
	 *         coordinates
	 */
	// equals: check if the z,y,x received is actually this Position
	public boolean equals(int z, int y, int x) {
		return (this.z == z && this.y == y && this.x == x);
	}

	@Override
	public boolean equals(Object obj) {
		Position pos = (Position) obj;
		return (this.z == pos.z && this.y == pos.y && this.x == pos.x);
	}
}
