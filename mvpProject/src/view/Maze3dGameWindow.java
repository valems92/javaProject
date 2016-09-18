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

public class Maze3dGameWindow extends BasicWindow {
	protected GameMaze3dGuiView view;
	protected String mazeName;

	protected MenuDisplay menu;
	protected WelcomeDisplay welcome;
	protected MazeDisplay maze;

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

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		createFileMenu();

		menu = new MenuDisplay(shell, SWT.NONE, this);
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		displayWelcome();
	}

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
				openProperties();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		shell.setMenuBar(menuBar);
	}

	private void openProperties() {
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText("Open");
		fileDialog.setFilterPath("C:/");
		String[] filterExt = { "*.xml" };
		fileDialog.setFilterExtensions(filterExt);

		String selected = fileDialog.open();
		view.update("load_properties " + selected);
	}

	public void displayMaze(Maze3d m, String name) {
		maze = new Maze3dDisplay(shell, SWT.BORDER, this);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		menu.displayGoalGuide();
		menu.setGoalFloorText(m.getGoalPosition().z);

		maze.initMaze(m, name, "images/fly.png");

		maze.moveAbove(welcome);
		welcome.dispose();

		maze.setFocus();
		shell.layout(true);
	}

	public void displayWelcome() {
		welcome = new WelcomeDisplay(shell, SWT.NONE, this);
		welcome.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		if (maze != null) {
			welcome.moveAbove(maze);
			maze.dispose();
			menu.displayStartGame();
		}

		shell.layout(true);
	}

	public void displayMessage(String msg, int style, Maze3d maze, String name) {
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setMessage(msg);

		int response = messageBox.open();
		if (response == SWT.YES)
			displayMaze(maze, name);
	}

	public void displayCrossSection(int[][] crossSection) {
		maze.displayCrossSection(crossSection);
	}

	protected void exitGame() {
		view.update("exit");
	}

	public void displaySolution(Solution<Position> solution, String type) {
		maze.displaySolution(solution, type);
	}
}
