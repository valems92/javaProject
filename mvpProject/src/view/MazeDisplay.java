package view;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class MazeDisplay extends Canvas {

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		setBackground(new Color(null, 255, 255, 255));
	}
}
