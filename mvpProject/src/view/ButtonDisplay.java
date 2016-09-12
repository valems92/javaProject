package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ButtonDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	
	public ButtonDisplay(Composite parent, String txt) {
		super(parent, SWT.NONE);
		
		this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
		
		Image bg = new Image(this.getDisplay(), "images/button.png");
		ImageData imgData = bg.getImageData();	
		
		Label label = new Label(this, SWT.NONE);
		label.setText(txt);
		
		FontData[] fontData = label.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		label.setFont(new Font(getDisplay(), fontData[0]));
		
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {	
				e.gc.drawImage(bg, 0, 0, imgData.width, imgData.height, 5, 10, getSize().x, getSize().y - 10);
				
				label.setSize(imgData.width, imgData.height);
				label.setLocation(imgData.width / 2 - 20, imgData.height / 2 + 5);
			}
		});
	}
}
