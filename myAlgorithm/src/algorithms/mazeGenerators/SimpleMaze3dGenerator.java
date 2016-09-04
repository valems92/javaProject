package algorithms.mazeGenerators;

import java.util.ArrayList;
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
	public Maze3d generate(int z,int y,int x) {
		
	
		//new maze
		Maze3d maze3d=new Maze3d(z,y,x);
		this.InitMaze(maze3d);
		Random rand=new Random();
		
		//set 0 and 1 values in the maze
		int randnum=0;
		for(int floor=0;floor<z;floor++){
			for(int row=0;row<y;row++){
				for(int columns=0;columns<x;columns++){
					randnum=rand.nextInt(2);
					maze3d.setCellValue(new Position(floor, row, columns), randnum);
				}
			}
		}
		
		//random for start position
		int floor=rand.nextInt(z);
		int row=rand.nextInt(y);
		int columns=rand.nextInt(x);
		
		maze3d.setStartPosition(new Position(floor,row,columns));
		maze3d.setCellValue(new Position(floor, row, columns), 0); //set the cell to 0
		
		//random for goal position
		floor=rand.nextInt(z);
		row=rand.nextInt(y);
		columns=rand.nextInt(x);
		
		maze3d.setGoalPosition(new Position(floor,row,columns));
		
		
		//if the start and goal equals - fix it
		while(maze3d.getStartPosition().equals(maze3d.getGoalPosition())==true){
			floor=rand.nextInt(z);
			row=rand.nextInt(y);
			columns=rand.nextInt(x);
			maze3d.setGoalPosition(new Position(floor,row,columns));
		}
		maze3d.setCellValue(new Position(floor, row, columns), 0); //set the cell's value to 0
		
		//temp for start and goal positions
		Position current=new Position(maze3d.getStartPosition().z,maze3d.getStartPosition().y,maze3d.getStartPosition().x);
		Position goaltemp=new Position(maze3d.getGoalPosition().z,maze3d.getGoalPosition().y,maze3d.getGoalPosition().x);
		
		//go from start to end and set zero in the cells
		while(current.equals(goaltemp)==false){
			if(current.z<goaltemp.z){
				current.z++;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
			else if(current.z>goaltemp.z){
				current.z--;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
			
			if(current.y<goaltemp.y){
				current.y++;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
			else if(current.y>goaltemp.y){
				current.y--;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
			if(current.x<goaltemp.x){
				current.x++;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
			else if(current.x>goaltemp.x){
				current.x--;
				maze3d.setCellValue(new Position(current.z,current.y,current.x), 0);
			}
		}
	
		return maze3d; //return the simple maze
	}

	/**
	 * Initiation the maze to 1
	 * @param maze
	 */
			public void InitMaze(Maze3d maze){
				for(int floor=0;floor<maze.getZ();floor++){
					for(int row=0;row<maze.getY();row++){
						for(int columns=0;columns<maze.getZ();columns++){
							maze.setCellValue(new Position(floor,row,columns), 1);
						}
					}
				}
			}

}



