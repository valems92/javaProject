package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	private Maze3dGameWindow gameView;
	private Maze3d maze;

	public MazeDisplay(Composite parent, int style, Maze3dGameWindow gameView, Maze3d maze) {
		super(parent, style);
		this.gameView = gameView;
		this.maze = maze;

		this.setBackground(new Color(null, 255, 255, 255));
		
		Position startPos = maze.getStartPosition();
		Position goalPos = maze.getGoalPosition();
		
		Image wall = new Image(this.getDisplay(), "images/wall.jpg");
		ImageData wallImgData = wall.getImageData();	
		
		Image character = new Image(this.getDisplay(), "images/flyBee.png");
		ImageData characterImgData = character.getImageData();	
		
		int floor = startPos.z;

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				// todo: get cross from command!
				int[][] cross = maze.getCrossSectionByZ(floor);

				int possitionWidth = getSize().x / cross[0].length;
				int possitionHeight = getSize().y / cross.length;

				for (int i = 0; i < cross.length; i++) {
					for (int j = 0; j < cross[0].length; j++) {
						int x = j * possitionWidth;
						int y = i * possitionHeight;
						if (cross[i][j] == maze.WALL) {
							e.gc.drawImage(wall, 0, 0, wallImgData.width, wallImgData.height, x, y, possitionWidth, possitionHeight);
						}
						else if(startPos.y == i && startPos.x == j) {
							e.gc.drawImage(character, 0, 0, characterImgData.width, characterImgData.height, x, y, possitionWidth, possitionHeight);
						}
					}
				}
			}
		});
	}
}
