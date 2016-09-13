package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ButtonDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	private Label text;
	private Label bgLabel;
	
	public ButtonDisplay(Composite parent, String txt) {
		super(parent, SWT.NONE);
		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
		
		Image bg = new Image(this.getDisplay(), "images/button.png");
		Rectangle rect = bg.getBounds();
		int width = rect.width, height = rect.height;
		
		setLabel(txt, width, height);
		
		bgLabel = new Label(this, SWT.NONE);
		bgLabel.setImage(bg);
		bgLabel.setEnabled(true);
		
		bgLabel.setSize(width, height);
		int textWidth = txt.length() * FONT_SIZE;
		text.setSize(textWidth, height);
		
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				// add int to x position because of the image shadow
				int shadow = 5;
				bgLabel.setLocation(getSize().x / 2 - width / 2 + shadow, getSize().y / 2 - height / 2);
				text.setLocation(getSize().x / 2 - textWidth / 4 - shadow, getSize().y / 2 - height / 4);
			}
		});
	}

	private void setLabel(String txt, int width, int height) {
		text = new Label(this, SWT.NONE);
		text.setText(txt);
		text.setEnabled(true);

		FontData[] fontData = text.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		text.setFont(new Font(getDisplay(), fontData[0]));
	}
	
	public void setMouseListener(MouseListener mouseListener){
		bgLabel.addMouseListener(mouseListener);
		text.addMouseListener(mouseListener);
	}
}
