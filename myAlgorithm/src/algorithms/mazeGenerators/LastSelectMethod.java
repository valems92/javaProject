package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * <h1>LastSelectMethod</h1> Pull the last (the newest) position from the list.
 * <p>
 * 
 * @author Valentina Munoz
 * @implements SelectMethod
 *
 */
public class LastSelectMethod implements SelectMethod {

	@Override
	public Position getSelectedCell(ArrayList<Position> list) {
		return list.get(list.size() - 1);
	}

}
