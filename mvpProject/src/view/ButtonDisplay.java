package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <h1>ButtonDisplay</h1>
 * A custom widget for buttons in program. Contains an image and text.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class ButtonDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	
	private Image bg;
	private Image bgOpacity;
	
	private Label bgLabel;
	private Label text;

	/**
	 * Initialize data and change cursor of widget on mouse over
	 * <p>
	 * @param parent widget parent
	 * @param txt text of button
	 */
	public ButtonDisplay(Composite parent, String txt) {
		super(parent, SWT.NONE);
		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
		this.setBackgroundMode(SWT.INHERIT_NONE);
		this.setLayout(null);
		
		bg = new Image(this.getDisplay(), "resources/button.png");
		bgOpacity = new Image(this.getDisplay(), "resources/buttonOpacity.png");

		initWidgets(txt);
	}
	
	/**
	 * <h1>initWidgets</h1>
	 * Create the widgets inside de button. The button contains two labels, one for
	 * the image and one for the text. 
	 * <p>
	 * @param txt text of button
	 */
	private void initWidgets(String txt){
		Rectangle rect = bg.getBounds();
		int width = rect.width;
		int height = rect.height;

		setLabel(txt);

		bgLabel = new Label(this, SWT.NONE);
		bgLabel.setImage(bg);
		bgLabel.setSize(width, height);

		GC gc = new GC(text);
		Point size = gc.textExtent(txt);
		gc.dispose();

		
		text.setBounds(size.x, size.y, size.x, size.y + 3);
		drawBtn(width, height, size);
	}

	/**
	 * <h1>drawBtn</h1>
	 * Change button (labels) position to be at the center. Also, if button should be disable,
	 * change image and text color.
	 * <p>
	 * @param width bg image width
	 * @param height bg image height
	 * @param size text size
	 */
	private void drawBtn(int width, int height, Point size){
		Color enable = new Color(null, 139, 207, 130);
		Color disable = new Color(null, 170, 185, 129);
		int shadow = 5;
		
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				bgLabel.setLocation(getSize().x / 2 - width / 2 + shadow, getSize().y / 2 - height / 2);
				text.setLocation(getSize().x / 2 - size.x / 2, getSize().y / 2 - size.y / 2 - shadow);
				
				if (!isEnabled()) {
					if (bgLabel.getImage() != bgOpacity)
						bgLabel.setImage(bgOpacity);
					if (text.getBackground() != disable)
						text.setBackground(disable);
				} else {
					if (bgLabel.getImage() != bg)
						bgLabel.setImage(bg);
					if(text.getBackground() != enable)
						text.setBackground(enable);
				}

			}
		});
	}
	
	/**
	 * <h1>setLabel</h1>
	 * Create the button text label and change it font size.
	 * <p>
	 * @param txt text of button
	 */
	private void setLabel(String txt) {
		text = new Label(this, SWT.NONE);
		text.setText(txt);

		FontData[] fontData = text.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		text.setFont(new Font(getDisplay(), fontData[0]));
	}

	/**
	 * <h1>setMouseListener</h1>
	 * Get a mouse listener, and add it to each label in button
	 * <p>
	 * @param mouseListener mouse listener to add to button
	 */
	public void setMouseListener(MouseListener mouseListener) {
		bgLabel.addMouseListener(mouseListener);
		text.addMouseListener(mouseListener);
	}
	
	/**
	 * <h1>setKeyListener</h1>
	 * Get a key event, and add it to button
	 * <p>
	 * @param keyEvent key event to add to button
	 */
	public void setKeyListener(KeyAdapter keyEvent) {
		bgLabel.addKeyListener(keyEvent);
	}
}
