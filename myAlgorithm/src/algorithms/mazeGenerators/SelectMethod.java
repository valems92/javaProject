package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * <h1>SelectMethod</h1> An interface that contains all must mathods to select a
 * cell from a list
 * <p>
 * 
 * @author Valentina Munoz
 */
public interface SelectMethod {
	/**
	 * <h1>getSelectedCell</h1> Return a cell position from a list according to
	 * the selected mode chosen
	 * 
	 * @param list
	 *            A list to choose a cell from it
	 * @return A position chosen from the received list
	 */
	public Position getSelectedCell(ArrayList<Position> list);
}
