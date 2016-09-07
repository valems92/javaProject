package algorithms.mazeGenerators;

import java.util.Random;

/**
 * <h1>SimpleMaze3dGenerator</h1> Create a 3D maze using a simple algorithm. The
 * algorithm choose a start position, and while the number of open cells is
 * smaller then the sum of floors, rows and columns, it open a random neighbor.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 * @extends CommonMaze3dGenerator
 *
 */

public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	@Override
	public Maze3d generate(int z, int y, int x) {
		Maze3d maze3d = new Maze3d(z, y, x);
		Random rand = new Random();

		for (int floor = 0; floor < z; floor++) 
			for (int row = 0; row < y; row++) 
				for (int columns = 0; columns < x; columns++) 
					maze3d.setCellValue(new Position(floor, row, columns), rand.nextInt(2));

		Position startPos = chooseRandomPosition(z, y, x);
		maze3d.setStartPosition(startPos);
		maze3d.setCellValue(startPos, maze3d.FREE);
						
		Position goalPos = chooseRandomPosition(z, y, x);
		while (startPos.equals(goalPos))
			goalPos = chooseRandomPosition(z, y, x);
		
		maze3d.setGoalPosition(goalPos);
		maze3d.setCellValue(goalPos, maze3d.FREE); 

		Position currentCell = new Position(startPos.z, startPos.y, startPos.x);
		
		while (!currentCell.equals(goalPos)) {
			if (currentCell.z < goalPos.z) currentCell.z++;
			else if(currentCell.z > goalPos.z) currentCell.z--;
			maze3d.setCellValue(currentCell, maze3d.FREE);
			
			if (currentCell.y < goalPos.y) currentCell.y++;
			else if (currentCell.y > goalPos.y) currentCell.y--;
			maze3d.setCellValue(currentCell, maze3d.FREE);
			
			if (currentCell.x < goalPos.x) currentCell.x++;
			else if (currentCell.x > goalPos.x) currentCell.x--;
			maze3d.setCellValue(currentCell, maze3d.FREE);
		}

		return maze3d; 
	}
}
