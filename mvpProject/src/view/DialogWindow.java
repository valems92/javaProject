package view;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1>DialogWindow</h1>
 * Create a new shell in display 
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public abstract class DialogWindow {
	protected Shell shell;	
	
	protected abstract void initWidgets();
	
	public void start(Display display) {		
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
	}
}
