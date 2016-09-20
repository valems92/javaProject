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

/**
 * <h1>MazeDisplay</h1>
 * A custom widget for maze disaply.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
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
	
	/**
	 * <h1>initMaze</h1>
	 * Get a maze. Initalize some data and display the maze start position floor - 
	 * throws the generate cross section notification to the presenter. 
	 * <p>
	 * Finally, create the game character according to images pathes received and draw the maze.
	 * <p>
	 * @param maze the displayed maze
	 * @param mazeName the displayed maze name
	 * @param charcterImgPath the path of the character (left)
	 * @param charcterFlipImgPath the path of the character (right)
	 */
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

	/**
	 * <h1>displayCrossSection</h1>
	 * Get a cross section to display and redraw the maze.
	 * <p>
	 * @param displayed new cross section to display
	 */
	public void displayCrossSection(int[][] displayed) {
		gameView.getMenu().setCurrentFloorText(currentPosition.z);
		this.displayed = displayed;
		redraw();
	}

	/**
	 * <h1>moveUp</h1>
	 * Get current position possible moves. 
	 * If up is possible, update data and throws the generate cross section notification to presenter.
	 * <p>
	 */
	private void moveUp() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Up")) {
			currentPosition.z++;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	/**
	 * <h1>moveDown</h1>
	 * Get current position possible moves. 
	 * If down is possible, update data and throws the generate cross section notification to presenter.
	 * <p>
	 */
	private void moveDown() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Down")) {
			currentPosition.z--;
			gameView.view.update("generate_cross_section " + mazeName + " " + currentPosition.z + " z");
		}
	}

	/**
	 * <h1>moveLeft</h1>
	 * Get current position possible moves. 
	 * If left is possible, update data and redraw the maze.
	 * <p>
	 */
	private void moveLeft() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Left")) {
			currentPosition.x--;
			redraw();
		}
	}

	/**
	 * <h1>moveRight</h1>
	 * Get current position possible moves. 
	 * If right is possible, update data and redraw the maze.
	 * <p>
	 */
	private void moveRight() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Right")) {
			currentPosition.x++;
			redraw();
		}
	}

	/**
	 * <h1>moveForward</h1>
	 * Get current position possible moves. 
	 * If forward is possible, update data and redraw the maze.
	 * <p>
	 */
	private void moveForward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Forward")) {
			currentPosition.y++;
			redraw();
		}
	}

	/**
	 * <h1>moveBackward</h1>
	 * Get current position possible moves. 
	 * If backward is possible, update data and redraw the maze.
	 * <p>
	 */
	private void moveBackward() {
		ArrayList<String> possibleMoves = maze.getPossibleMoves(currentPosition);
		if (possibleMoves.contains("Backward")) {
			currentPosition.y--;
			redraw();
		}
	}

	/**
	 * <h1>displaySolution</h1>
	 * Get the maze solution.<p>
	 * If the type wanted is hint, it animate the character half way of the solution.
	 * However, if length of half of the solution is smaller than 2, it shows a massage that 
	 * the character is too close. 
	 * <p>
	 * If the type wanted is solve, it animate the character all way to the goal.
	 * <p>
	 * @param solution maze solution
	 * @param type Hint/Solve
	 */
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

	/**
	 * <h1>onAnimationEnded</h1>
	 * When character animation ends, if the type wanted was hint, it animate the characte back
	 * to his last position.
	 * <p>
	 * @param solve the solve path
	 * @param type hint/solve
	 */
	public void onAnimationEnded(ArrayList<Position> solve, String type) {
		if (type.equals("Hint")) {
			Collections.reverse(solve);
			initCharacterAnimation(solve, "");
		}
	}
	
	/**
	 * <h1>initCharacterAnimation</h1>
	 * Get a path of the solution and animate the character through it.
	 * It init a a timer task, that is schedule to ocurre at a fixed rate, taken from the properties file.
	 * When the path comes to an end, the timer is ended.
	 * <p>
	 * @param solve the solve path
	 * @param type hint/solve
	 */
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
							onAnimationEnded(solve, type);
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
	
	/**
	 * <h1>ShowWinWindows</h1>
	 * When character rich the goal, show the win windows.
	 * <p>
	 */
	protected void ShowWinWindows() {
		WinWindow winWindow = new WinWindow(gameView);
		winWindow.start(gameView.display);
	}
	
	/**
	 * <h1>initKeyEvents</h1>
	 * Init key listener and mouse wheel listener.
	 * <p>
	 * The key listener is for the character moves. 
	 * When keyboard arrows are pressed, the character moves left/right/forward/backward.
	 * When PgDn or PgUp is pressed, the character change maze floor.
	 * <p>
	 * The mouse wheel listenet is for zoom in/ zoom out the maze.
	 * When ctrl is pressed and mouse is scrolled, zoom in or zoom out the maze.
	 * <p>
	 */
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

	/**
	 * <h1>drawMaze</h1>
	 * Create images with all grpahic needed.
	 * Add paint listener, that draw the maze.
	 * <p>
	 * To draw the cross ssection, it calculate the position dimension.
	 * <p>
	 * For each possition, if it's a wall, on 2d maze the wall image is drawn and on 3d maze, a cube is painted.
	 * <p>
	 * On the current position, it draws the character.
	 * <p>
	 * On the goal position, it draws the goal image.
	 * <p>
	 * For each possition, if up or down moves are possible, it draws the right image.
	 * <p>
	 */
	protected abstract void drawMaze();
}
