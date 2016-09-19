package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public abstract class MazeDisplay extends Canvas {
	protected Maze3dGameWindow gameView;
	protected String mazeName;
	protected GameCharacter character;
	protected Position goalPosition;
	protected Position currentPosition;
	protected int[][] displayed;
	protected Maze3d maze;
	protected boolean win = false;
	protected float scale = 1;
	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		this.setBackground(new Color(null, 233, 232, 233));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	
		initKeyEvents();
	}
	
	public void initMaze(Maze3d maze, String mazeName, String charcterImgPath, String charcterFlipImgPath) {
		this.maze = maze;
		this.mazeName = mazeName;

		Position startPosition = maze.getStartPosition();
		this.currentPosition = new Position(startPosition.z, startPosition.y, startPosition.x);
		
		this.goalPosition = maze.getGoalPosition();

		int floor = currentPosition.z;
		gameView.view.update("generate_cross_section " + mazeName + " " + floor + " z");

		this.character = new GameCharacter(this.getDisplay(), charcterImgPath, charcterFlipImgPath);

		drawMaze();
	}

	public void displayCrossSection(int[][] displayed) {
		gameView.getMenu().setCurrentFloorText(currentPosition.z);
		this.displayed = displayed;
		redraw();
	}

	private void moveUp() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Up")) {
			currentPosition.z++;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	private void moveDown() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Down")) {
			currentPosition.z--;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	private void moveLeft() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Left")) {
			currentPosition.x--;
			redraw();
		}
	}

	private void moveRight() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Right")) {
			currentPosition.x++;
			redraw();
		}
	}

	private void moveForward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Forward")) {
			currentPosition.y++;
			redraw();
		}
	}

	private void moveBackward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Backward")) {
			currentPosition.y--;
			redraw();
		}
	}

	public void displaySolution(Solution<Position> solution, String type) {
		ArrayList<Position> solve = solution.getResults();
		if (type.equals("Hint")) {
			int length = (int) (solve.size() * Properties.properites.getHintLength());
			ArrayList<Position> hintSolve = new ArrayList<Position>();

			for (int i = 0; i < length; i++)
				hintSolve.add(solve.get(i));

			if (length >= 2) {
				initCharacterAnimation(hintSolve, type);
			} else
				gameView.view.update("display_message " + "You are so closed!");

		} else
			initCharacterAnimation(solve, type);
	}

	public void animationEnded(ArrayList<Position> solve, String type) {
		if (type.equals("Hint")) {
			Collections.reverse(solve);
			initCharacterAnimation(solve, "");
		}
	}

	private void initCharacterAnimation(ArrayList<Position> solve, String type) {
		AtomicInteger index = new AtomicInteger(1);

		Timer timer = new Timer();
		TimerTask task;

		task = new TimerTask() {
			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						if (solve.size() <= index.get()) {
							timer.cancel();
							timer.purge();
							animationEnded(solve, type);
						} else {
							Position nextPos = solve.get(index.getAndIncrement());
							if (nextPos.z != currentPosition.z) {
								gameView.view.update("generate_cross_section " + mazeName + " " + nextPos.z + " z");
								gameView.menu.setCurrentFloorText(nextPos.z);
							}
							currentPosition = nextPos;

							redraw();
						}
					}
				});
			}
		};
		timer.scheduleAtFixedRate(task, 0, Properties.properites.getAnimationSpeed());
	}
	
	protected void ShowWinWindows() {
		WinWindow winWindow = new WinWindow(gameView);
		winWindow.start(gameView.display);
	}
	
	private void initKeyEvents() {
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				int wheelCount = e.count;
				if ((e.stateMask & SWT.CONTROL) == SWT.CONTROL) {
					if (wheelCount > 0) {
						scale += .2;	
						redraw();
					} else if(wheelCount < 0 && scale >= 1.2) {
						scale -= .2;	
						redraw();
					}
				}
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.PAGE_UP || e.keyCode == SWT.SHIFT)
					moveUp();

				else if (e.keyCode == SWT.PAGE_DOWN || e.keyCode == SWT.CONTROL)
					moveDown();

				else if (e.keyCode == SWT.ARROW_UP)
					moveBackward();

				else if (e.keyCode == SWT.ARROW_DOWN)
					moveForward();

				else if (e.keyCode == SWT.ARROW_LEFT)
					moveLeft();

				else if (e.keyCode == SWT.ARROW_RIGHT)
					moveRight();
			}
		});
	}

	protected abstract void drawMaze();
}
