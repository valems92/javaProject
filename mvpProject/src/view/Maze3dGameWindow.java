package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import presenter.Properties;

public class Maze3dGameWindow extends BasicWindow {
	private GameMaze3dGuiView view;

	public Maze3dGameWindow(int width, int height, GameMaze3dGuiView view) {
		super(width, height);
		this.view = view;
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		createFileMenu();

		Label title = new Label(shell, SWT.NONE);
		title.setText("3D maze game!");
		title.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));

		MenuDisplay menu = new MenuDisplay(shell, SWT.BORDER);
		menu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		MazeDisplay maze = new MazeDisplay(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	private void createFileMenu() {
		Menu menuBar = new Menu(shell, SWT.BAR);
		Menu fileMenu = new Menu(menuBar);

		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);

		MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
		newItem.setText("Open properties");
		newItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openProperties();
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
		String[] filterExt = {"*.xml"};
		fileDialog.setFilterExtensions(filterExt);
		
		String selected = fileDialog.open();
		view.update("load_properties " + selected);
		
		System.out.println(Properties.properites.getViewWidth());
	}
}
