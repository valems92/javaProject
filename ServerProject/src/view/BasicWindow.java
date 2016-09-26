package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1>BasicWindow</h1> Create a window and shell for gui view. Responsable to
 * dispise all when window close.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public abstract class BasicWindow implements Runnable {
	protected Display display;
	protected Shell shell;

	/**
	 * Create the display and shell. Set shell size to the recieved dimendion.
	 * 
	 * @param width
	 *            shell width
	 * @param height
	 *            shell height
	 */
	public BasicWindow(int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
	}

	public abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}