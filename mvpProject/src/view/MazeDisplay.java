package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class MazeDisplay extends Canvas {
	protected String mazeName;

	protected GameCharacter character;

	protected Position goalPosition;
	protected Position currentPosition;

	protected int[][] displayed;

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		initKeyEvents();
	}

	protected abstract void initMaze(Maze3d maze, String mazeName, String charcterImgPath);

	protected abstract void drawMaze();

	protected abstract void displayCrossSection(int[][] displayed);

	protected abstract void moveUp();

	protected abstract void moveDown();

	protected abstract void moveLeft();

	protected abstract void moveRight();

	protected abstract void moveForward();

	protected abstract void moveBackward();

	private void initKeyEvents() {	
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.SHIFT)
					moveUp();
				
				else if (e.keyCode == SWT.CONTROL)
					moveDown();
					
				else if(e.keyCode == SWT.ARROW_UP)
					moveBackward();
				
				else if(e.keyCode == SWT.ARROW_DOWN)
					moveForward();
				
				else if(e.keyCode == SWT.ARROW_LEFT)
					moveLeft();
				
				else if(e.keyCode == SWT.ARROW_RIGHT)
					moveRight();
			}
		});
	}
}
