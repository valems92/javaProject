package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;

import presenter.Properties;

/**
 * <h1>WinWindow</h1> Create a new shell, representing the win window.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class WinWindow extends DialogWindow {
	private final int FONT_SIZE = 36;
	private final String txt = "Congratulation \nYou win! ";
	Maze3dGameWindow gameView;

	private Canvas winPage;
	private Label text;
	private Point size;

	public WinWindow(Maze3dGameWindow gameView) {
		this.gameView = gameView;
	}

	/**
	 * <h1>initWidgets</h1> Locate the new shell in monitor's center. Set it's
	 * size and background color. Also, add the text to the window and a image.
	 * <p>
	 * Add to the shell a close event. When shell is closed, the welocme window
	 * is displayed instead of the maze window.
	 * <p>
	 * <p>
	 */
	@Override
	protected void initWidgets() {
		winPage = new Canvas(this.shell, SWT.NONE);

		shell.setLayout(new GridLayout(1, false));
		winPage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		shell.setBackground(new Color(null, 254, 188, 0));
		winPage.setBackground(new Color(null, 254, 207, 166));

		int width = (int) (Properties.properites.getViewWidth() * 0.6);
		int height = (int) (Properties.properites.getViewHeight() * 0.4);

		shell.setSize(width, height);

		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		winPage.setBackgroundMode(SWT.DM_FILL_NONE);

		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				gameView.displayWelcome();
			}
		});

		initText();

		GC gc = new GC(text);
		size = gc.textExtent(txt);
		gc.dispose();
		
		text.setBounds(0, 0, size.x, size.y * 2);

		Image img = new Image(shell.getDisplay(), "resources/win2.png");
		ImageData imgData = img.getImageData();

		winPage.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int min = winPage.getSize().y / 2;

				text.setLocation(shell.getSize().x / 2 - size.x / 2, 0);

				e.gc.drawImage(img, 0, 0, imgData.width, imgData.height, 0, winPage.getSize().y - min, min, min);
			}
		});
	}

	/**
	 * <h1>initText</h1> Create a new label with congratulation text. Change the
	 * style and calcualte it's size.
	 * <p>
	 */
	private void initText() {
		text = new Label(winPage, SWT.CENTER);
		text.setText(txt);

		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		FontData[] fontData = text.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		fontData[0].setStyle(SWT.BOLD);
		text.setFont(new Font(shell.getDisplay(), fontData[0]));
	}
}