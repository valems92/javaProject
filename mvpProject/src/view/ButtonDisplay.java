package view;

import org.eclipse.swt.SWT;
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

public class ButtonDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	private Label bgLabel;
	private Label text;

	public ButtonDisplay(Composite parent, String txt) {
		super(parent, SWT.NONE);
		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
		this.setBackgroundMode(SWT.INHERIT_NONE);
		this.setLayout(null);

		Image bg = new Image(this.getDisplay(), "images/button.png");
		Image bgOpacity = new Image(this.getDisplay(), "images/buttonOpacity.png");

		Color enable = new Color(null, 139, 207, 130);
		Color disable = new Color(null, 170, 185, 129);

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

		text.setSize(size.x, size.y);
		text.setBounds(size.x, size.y, size.x, size.y);

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

	private void setLabel(String txt) {
		text = new Label(this, SWT.NONE);
		text.setText(txt);

		FontData[] fontData = text.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		text.setFont(new Font(getDisplay(), fontData[0]));
	}

	public void setMouseListener(MouseListener mouseListener) {
		bgLabel.addMouseListener(mouseListener);
		text.addMouseListener(mouseListener);
	}
}
