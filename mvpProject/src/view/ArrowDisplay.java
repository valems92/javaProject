package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <h1>ArrowDisplay</h1> A custom widget for arrows in program. Arrows are
 * enable when from character position in maze, up and down moves are possible.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class ArrowDisplay extends Canvas {
	private Image arrow;
	private Image arrowOpacity;
	private Label arrowLabel;

	/**
	 * Initialize data and change cursor of widget on mouse over
	 * <p>
	 * 
	 * @param parent
	 *            widget parent
	 * @param imagePath
	 *            iamge path of regular arrow
	 * @param imageOpacityPath
	 *            image path of transparent arrow
	 */
	public ArrowDisplay(Composite parent, String imagePath, String imageOpacityPath) {
		super(parent, SWT.NONE);
		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
		this.setBackgroundMode(SWT.INHERIT_NONE);
		this.setLayout(null);

		arrow = new Image(this.getDisplay(), imagePath);
		arrowOpacity = new Image(this.getDisplay(), imageOpacityPath);

		initWidgets();
	}

	/**
	 * <h1>initWidgets</h1> Create the widgets of arrow. The arrow contains a
	 * label with image backgruund.
	 * <p>
	 */
	private void initWidgets() {
		Rectangle rect = arrow.getBounds();
		int width = rect.width;
		int height = rect.height;

		arrowLabel = new Label(this, SWT.NONE);
		arrowLabel.setImage(arrow);
		arrowLabel.setSize(width, height + 10);

		drawArrow(width, height);
	}

	/**
	 * <h1>drawArrow</h1> Change arrow position to be at the center. Also, if
	 * arrow should be disable, change image.
	 * <p>
	 * @param width
	 *            arrow image width
	 * @param height
	 *            arrow image height
	 */
	private void drawArrow(int width, int height) {
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				arrowLabel.setLocation(getSize().x / 2 - width / 2, getSize().y / 2 - height / 2);

				if (!isEnabled()) {
					if (arrowLabel.getImage() != arrowOpacity)
						arrowLabel.setImage(arrowOpacity);
				} else {
					if (arrowLabel.getImage() != arrow)
						arrowLabel.setImage(arrow);
				}
			}
		});
	}
	
	/**
	 * <h1>setMouseListener</h1>
	 * Get a mouse listener, and add it to the arrow
	 * <p>
	 * @param mouseListener mouse listener to add to the arrow
	 */
	public void setMouseListener(MouseListener mouseListener) {
		arrowLabel.addMouseListener(mouseListener);
	}
}
