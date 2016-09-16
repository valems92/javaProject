package view;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class Maze3dDisplay extends MazeDisplay {
	private Maze3dGameWindow gameView;

	private Maze3d maze;
	private boolean win = false;

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
		gameView.getMenu().setCurrentFloorText(currentPosition.z);
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
	public void displaySolution(Solution<Position> solution, String type) {
		ArrayList<Position> solve = solution.getResults();
		if (type.equals("Hint")) {
			double len = solve.size() * presenter.Properties.properites.getHintLen();
			if (len >= 2) {
				for (int i = 1; i < (int) len; i++) {
					if (solve.get(i).z != currentPosition.z){
						gameView.view.update("generate_cross_section " + mazeName + " " + solve.get(i).z + " z");
						gameView.menu.setCurrentFloorText(solve.get(i).z);
					}

					this.currentPosition = solve.get(i);
					redraw();
				}
			}

			else
				gameView.view.update("display_message " + "You are so closed!");

		} else {

			for (int i = 1; i < solve.size(); i++) {
				if (solve.get(i).z != currentPosition.z){
					gameView.view.update("generate_cross_section " + mazeName + " " + solve.get(i).z + " z");
					gameView.menu.setCurrentFloorText(solve.get(i).z);
				}

				this.currentPosition = solve.get(i);
				redraw();

			}
		}

	}
	

	private void ShowWinWindows() {

		WinWindow winWindow = new WinWindow();
		winWindow.start(gameView.display);

	}

	@Override
	protected void drawMaze() {
		Image wall = new Image(this.getDisplay(), "images/wall.jpg");
		ImageData wallImgData = wall.getImageData();

		Image goal = new Image(this.getDisplay(), "images/honey.png");
		ImageData goalImgData = goal.getImageData();

		Image up = new Image(this.getDisplay(), "images/up.png");
		ImageData upImgData = up.getImageData();

		Image down = new Image(this.getDisplay(), "images/down.png");
		ImageData downImgData = down.getImageData();

		Image downUp = new Image(this.getDisplay(), "images/upDown.png");
		ImageData downUpImgData = downUp.getImageData();

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
					ArrayList<String> possibleMoves;
					for (int i = 0; i < cross.length; i++) {
						for (int j = 0; j < cross[0].length; j++) {
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
								if (possibleMoves.contains("Down") && possibleMoves.contains("Up")) {
									int width = (int) (downUpImgData.width
											* ((double) (possitionHeight / 2) / downUpImgData.height));
									e.gc.drawImage(downUp, 0, 0, downUpImgData.width, downUpImgData.height, x, y, width,
											possitionHeight / 2);
								} else if (possibleMoves.contains("Up")) {
									int width = (int) (upImgData.width
											* ((double) (possitionHeight / 2) / upImgData.height));
									e.gc.drawImage(up, 0, 0, upImgData.width, upImgData.height, x, y, width,
											possitionHeight / 2);
								} else if (possibleMoves.contains("Down")) {
									int width = (int) (downImgData.width
											* ((double) (possitionHeight / 2) / downImgData.height));
									e.gc.drawImage(down, 0, 0, downImgData.width, downImgData.height, x, y, width,
											possitionHeight / 2);
								}

								// Character
								if (currentPosition.y == i && currentPosition.x == j)
									character.paint(e, x + (widthDimensionDiff / 2), y + (heightDimensionDiff / 2),
											minDimension, minDimension);
								// Goal
								else if (currentPosition.z == goalPosition.z && goalPosition.y == i
										&& goalPosition.x == j)
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
