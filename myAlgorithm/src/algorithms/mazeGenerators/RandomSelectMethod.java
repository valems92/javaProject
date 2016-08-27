package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * <h1>RandomSelectMethod</h1> Pull a random position from the list.
 * <p>
 * 
 * @author Valentina Munoz
 * @implements SelectMethod
 *
 */
public class RandomSelectMethod implements SelectMethod {

	@Override
	public Position getSelectedCell(ArrayList<Position> list) {
		Random rand = new Random();
		int cellIndex = rand.nextInt(list.size());
		return list.get(cellIndex);
	}

}
