package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * <h1>Maze3dGameWindow</h1>
 * Create the maze 3D game window. Initialize all widgets and manager all changes in gui.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dGameWindow extends BasicWindow {
	protected GameMaze3dGuiView view;
	protected String mazeName;

	protected MenuDisplay menu;
	protected WelcomeDisplay welcome;
	protected MazeDisplay maze;

	/**
	 * Initialize window size and background color. 
	 * Also, center shell in monitor and add a close event to it.
	 * <p>
	 * @param width shell width
	 * @param height shell height
	 * @param view the program view
	 */
	public Maze3dGameWindow(int width, int height, GameMaze3dGuiView view) {
		super(width, height);
		this.view = view;

		shell.setBackground(new Color(null, 116, 73, 38));
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				exitGame();
			}
		});

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	public MenuDisplay getMenu() {
		return menu;
	}

	/**
	 * <h1>initWidgets</h1>
	 * Create the menu bar, the menu on left size of screen and the welcome screen.
	 * <p>
	 */
	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		createFileMenu();

		menu = new MenuDisplay(shell, SWT.NONE, this);
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		displayWelcome();
	}

	/**
	 * <h1>createFileMenu</h1>
	 * Add to shell a menu bar and create in the file tab. 
	 * Also, create two items in file tab: open properties and exit. 
	 * <p>
	 */
	private void createFileMenu() {
		Menu menuBar = new Menu(shell, SWT.BAR);
		Menu fileMenu = new Menu(menuBar);

		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);

		MenuItem propItem = new MenuItem(fileMenu, SWT.NONE);
		propItem.setText("Open properties");
		propItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onOpenProperties();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		shell.setMenuBar(menuBar);
	}

	/**
	 * <h1>onOpenProperties</h1>
	 * When open properties item is pressed, open a file dialog to select the file wanted.
	 * Once a file was selected, send a notification to presenter.
	 * <p>
	 */
	private void onOpenProperties() {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText("Open");
		fileDialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml" };
		fileDialog.setFilterExtensions(filterExt);

		String selected = fileDialog.open();
		if(selected != null)
			view.update("load_properties " + selected);
	}

	/**
	 * <h1>displayMaze</h1>
	 * Get a maze and it's name. Create a maze according to property "maze display" in properties file (2d or 3d).
	 * Change welcome window to maze window, turn the start group in menu to the goal group and initialize maze.
	 * Also, it set the maze focus so key events on it will works immediately.
	 * <p>
	 * @param m maze to create
	 * @param name maze name
	 */
	public void displayMaze(Maze3d m, String name) {
		maze = (Properties.properites.getMazeDisplay().equals("3d")) ? new Maze3dDisplay(shell, SWT.BORDER, this)
				: new Maze2dDisplay(shell, SWT.BORDER, this);

		menu.displayGoalGuide();
		menu.setGoalFloorText(m.getGoalPosition().z);

		maze.initMaze(m, name, "images/fly.png", "images/flyFlip.png");

		maze.moveAbove(welcome);
		welcome.dispose();

		maze.setFocus();
		shell.layout(true);
	}

	/**
	 * <h1>displayWelcome</h1>
	 * Create the walcome image. If exist, change maze window to welcome window and turn the goal group in menu to the strat group.
	 * <p>
	 */
	public void displayWelcome() {
		welcome = new WelcomeDisplay(shell, SWT.NONE);
		welcome.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		if (maze != null) {
			welcome.moveAbove(maze);
			maze.dispose();
			menu.displayStartGame();
		}

		shell.layout(true);
	}

	/**
	 * <h1>displayMessage</h1>
	 * Display a massage box with string and style received. If response in massage box is YES, 
	 * then the massage "open an existing maze" was disaplyed, so display the maze received. 
	 * <p>
	 * @param msg
	 * @param style
	 * @param maze
	 * @param name
	 */
	public void displayMessage(String msg, int style, Maze3d maze, String name) {
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setMessage(msg);

		int response = messageBox.open();
		if (response == SWT.YES)
			displayMaze(maze, name);
	}

	/**
	 * <h1>displayCrossSection</h1>
	 * Get a cross section of maze and send it to display corss section in maze window
	 * <p>
	 * @param crossSection
	 */
	public void displayCrossSection(int[][] crossSection) {
		maze.displayCrossSection(crossSection);
	}

	/**
	 * <h1>exitGame</h1>
	 * send a exit notification to presenter.
	 * <p>
	 */
	protected void exitGame() {
		view.update("exit");
	}

	/**
	 * <h1>displaySolution</h1>
	 * Get a solution and a type, initialize the maze scale to 1 and send to disaply solution in maze winsow
	 * <p>
	 * @param solution
	 * @param type
	 */
	public void displaySolution(Solution<Position> solution, String type) {
		maze.scale = 1;
		maze.displaySolution(solution, type);
	}
}
