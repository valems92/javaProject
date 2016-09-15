package view;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;

public class Maze3dDisplay extends MazeDisplay {
	private Maze3dGameWindow gameView;

	private Maze3d maze;

	public Maze3dDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.setBackground(new Color(null, 233, 232, 233));
		this.gameView = gameView;
	}

	@Override
	protected void initMaze(Maze3d maze, String mazeName, String charcterImgPath) {
		this.maze = maze;
		this.mazeName = mazeName;

		this.currentPosition = maze.getStartPosition();
		this.goalPosition = maze.getGoalPosition();

		int floor = currentPosition.z;
		gameView.view.update("generate_cross_section " + mazeName + " " + floor + " z");

		this.character = new GameCharacter(this.getDisplay(), charcterImgPath);

		drawMaze();
	}

	@Override
	protected void displayCrossSection(int[][] displayed) {
		gameView.getMenu().setCurrentFloorText(currentPosition.z + 1);
		this.displayed = displayed;
		redraw();
	}

	@Override
	protected void moveUp() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Up")) {
			currentPosition.z++;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	@Override
	protected void moveDown() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Down")) {
			currentPosition.z--;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	@Override
	protected void moveLeft() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Left")) {
			currentPosition.x--;
			redraw();
		}
	}

	@Override
	protected void moveRight() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Right")) {
			currentPosition.x++;
			redraw();
		}
	}

	@Override
	protected void moveForward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Forward")) {
			currentPosition.y++;
			redraw();
		}
	}

	@Override
	protected void moveBackward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Backward")) {
			currentPosition.y--;
			redraw();
		}
	}

	@Override
	protected void drawMaze() {
		Image wall = new Image(this.getDisplay(), "images/wall.jpg");
		ImageData wallImgData = wall.getImageData();

		Image goal = new Image(this.getDisplay(), "images/honey.png");
		ImageData goalImgData = goal.getImageData();

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int[][] cross = displayed;
				if (cross != null) {
					int possitionWidth = getSize().x / cross[0].length;
					int possitionHeight = getSize().y / cross.length;

					int minDimension, widthDimensionDiff = 0, heightDimensionDiff = 0;
					if (possitionHeight < possitionWidth) {
						minDimension = possitionHeight;
						widthDimensionDiff = (possitionWidth - possitionHeight) / 2;
					} else {
						minDimension = possitionWidth;
						heightDimensionDiff = (possitionHeight - possitionWidth) / 2;
					}

					for (int i = 0; i < cross.length; i++) {
						for (int j = 0; j < cross[0].length; j++) {
							int x = j * possitionWidth;
							int y = i * possitionHeight;
							// Walls
							if (cross[i][j] == maze.WALL)
								e.gc.drawImage(wall, 0, 0, wallImgData.width, wallImgData.height, x, y, possitionWidth,
										possitionHeight);
							// Character
							else if (currentPosition.y == i && currentPosition.x == j)
								character.paint(e, x + widthDimensionDiff, y + heightDimensionDiff, minDimension,
										minDimension);

							// Goal
							else if (goalPosition.y == i && goalPosition.x == j)
								e.gc.drawImage(goal, 0, 0, goalImgData.width, goalImgData.height,
										x + widthDimensionDiff, y + heightDimensionDiff, minDimension, minDimension);
						}
					}
				}
			}
		});
	}
}
