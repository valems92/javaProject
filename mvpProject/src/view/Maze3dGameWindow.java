package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

public class Maze3dGameWindow extends BasicWindow {
	protected GameMaze3dGuiView view;

	public Maze3dGameWindow(int width, int height, GameMaze3dGuiView view) {
		super(width, height);
		this.view = view;

		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				exitGame();
			}
		});
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		createFileMenu();

		Label title = new Label(shell, SWT.NONE);
		title.setText("3D maze game!");
		title.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));

		MenuDisplay menu = new MenuDisplay(shell, SWT.BORDER, this);
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		MazeDisplay maze = new MazeDisplay(shell, SWT.BORDER, this);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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

	public void displayMessage(String msg) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setMessage(msg);

		int buttonID = messageBox.open();
		switch (buttonID) {
		case SWT.YES:
			
			break;
		}
	}

	protected void exitGame() {
		view.update("exit");
	}
}
