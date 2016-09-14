package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class WelcomeDisplay extends Canvas {
	Maze3dGameWindow gameView;

	public WelcomeDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;

		Image bgImg = new Image(this.getDisplay(), "images/welcome.jpg");
		ImageData imgData = bgImg.getImageData();		
		
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {		
				e.gc.drawImage(bgImg, 0, 0, imgData.width, imgData.height, 0, 0, getSize().x, getSize().y);
			}
		});
	}
}
