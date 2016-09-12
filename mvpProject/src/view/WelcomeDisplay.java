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
		
		Image flyBee = new Image(this.getDisplay(), "images/flyBee.png");
		ImageData flyBeeImgData = flyBee.getImageData();
		
		Image happyBee = new Image(this.getDisplay(), "images/happyBee.png");
		ImageData happyBeeImgData = happyBee.getImageData();
		
		Image sadBee = new Image(this.getDisplay(), "images/sadBee.png");
		ImageData sadBeeImgData = sadBee.getImageData();
		
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {		
				e.gc.drawImage(bgImg, 0, 0, imgData.width, imgData.height, 0, 0, getSize().x, getSize().y);
				e.gc.drawImage(flyBee, 0, 0, flyBeeImgData.width, flyBeeImgData.height, 10, 10, getSize().x / 8, getSize().y / 6);
				e.gc.drawImage(happyBee, 0, 0, happyBeeImgData.width, happyBeeImgData.height, getSize().x / 5 * 3, getSize().y / 7 * 5, getSize().x / 8, getSize().y / 6);
				e.gc.drawImage(sadBee, 0, 0, sadBeeImgData.width, sadBeeImgData.height, getSize().x / 7 * 2, getSize().y / 7 * 5, getSize().x / 8, getSize().y / 6);
			}
		});
	}
}
