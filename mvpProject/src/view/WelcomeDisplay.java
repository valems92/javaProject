package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * <h1>WelcomeDisplay</h1>
 * A custom widget for welcome disaply.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class WelcomeDisplay extends Canvas {
	/**
	 * Create widget and add to it an image.
	 * <p>
	 * @param parent widget parent
	 * @param style widget style
	 */
	public WelcomeDisplay(Composite parent, int style) {
		super(parent, style);

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
