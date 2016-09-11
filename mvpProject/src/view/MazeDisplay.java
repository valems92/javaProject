package view;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class MazeDisplay extends Canvas {
	Maze3dGameWindow gameView;
	
	public MazeDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;
		this.setLayout(new GridLayout(5,true));
		
		setBackground(new Color(null, 255, 255, 255));
	}
	
	
}
