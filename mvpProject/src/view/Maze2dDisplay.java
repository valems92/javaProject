package view;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;

public class Maze2dDisplay extends MazeDisplay {
	public Maze2dDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;
	}

	@Override
	protected void drawMaze() {
		Image wall = new Image(this.getDisplay(), "images/wall.jpg");
		ImageData wallImgData = wall.getImageData();

		Image goal = new Image(this.getDisplay(), "images/honey.png");
		ImageData goalImgData = goal.getImageData();

		Image up = new Image(this.getDisplay(), "images/stairs.png");
		ImageData upImgData = up.getImageData();

		Image down = new Image(this.getDisplay(), "images/snake.png");
		ImageData downImgData = down.getImageData();

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int[][] cross = displayed;
				if (cross != null) {
					int mazeX = cross[0].length;
					int mazeY = cross.length;

					int possitionWidth = (int) ((e.width / mazeX) * scale);
					int possitionHeight = (int) ((e.height / mazeY) * scale);

					int minDimension, widthDimensionDiff = 0, heightDimensionDiff = 0;
					if (possitionHeight < possitionWidth) {
						minDimension = possitionHeight;
						widthDimensionDiff = (possitionWidth - possitionHeight) / 2;
					} else {
						minDimension = possitionWidth;
						heightDimensionDiff = (possitionHeight - possitionWidth) / 2;
					}
					ArrayList<String> possibleMoves;

					for (int i = 0; i < mazeY; i++) {
						for (int j = 0; j < mazeX; j++) {
							possibleMoves = maze.getPossibleMoves(new Position(currentPosition.z, i, j));

							int x = j * possitionWidth;
							int y = i * possitionHeight;

							// Win
							if (!win) {
								if (currentPosition.z == goalPosition.z && currentPosition.y == goalPosition.y
										&& currentPosition.x == goalPosition.x) {
									ShowWinWindows();
									win = true;
								}
							}
							// Walls
							if (cross[i][j] == maze.WALL) {
								e.gc.drawImage(wall, 0, 0, wallImgData.width, wallImgData.height, x, y, possitionWidth,
										possitionHeight);
							} else {
								if (possibleMoves.contains("Up")) {
									int width = (int) (upImgData.width * ((possitionHeight / 1.5) / upImgData.height));
									e.gc.drawImage(up, 0, 0, upImgData.width, upImgData.height,
											x + possitionWidth - width, y - (possitionHeight / 10), width,
											(int) (possitionHeight / 1.5));
								}
								if (possibleMoves.contains("Down")) {
									int width = (int) (downImgData.width
											* ((possitionHeight / 1.5) / downImgData.height));
									e.gc.drawImage(down, 0, 0, downImgData.width, downImgData.height, x,
											y + (possitionHeight - (int) (possitionHeight / 1.5)), width,
											(int) (possitionHeight / 1.5));
								}

								// Character
								if (currentPosition.y == i && currentPosition.x == j)
									character.paint(e, x + (widthDimensionDiff / 2), y + (heightDimensionDiff / 2),
											minDimension, minDimension);
								// Goal
								if (currentPosition.z == goalPosition.z && goalPosition.y == i && goalPosition.x == j)
									e.gc.drawImage(goal, 0, 0, goalImgData.width, goalImgData.height,
											x + (widthDimensionDiff / 2), y + (heightDimensionDiff / 2), minDimension,
											minDimension);
							}
						}
					}
				}
			}
		});
	}
}