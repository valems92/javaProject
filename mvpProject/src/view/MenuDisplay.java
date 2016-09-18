package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Position;

public class MenuDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	private Maze3dGameWindow gameView;

	private Group startGameGroup;
	private Group goalGroup;

	private Label currentFloor;
	private Label goalFloor;

	private ButtonDisplay loadBtn;
	private ButtonDisplay endBtn;
	private ButtonDisplay saveBtn;

	public MenuDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;

		this.setLayout(new GridLayout(2, false));
		this.setBackground(new Color(null, 116, 73, 38));
		this.setBackgroundMode(SWT.INHERIT_DEFAULT);

		initWidgets();
	}

	private void initWidgets() {
		displayStartGame();

		loadSaveGame();
		endExitGame();
	}

	protected void startGame() {
		startGameGroup = new Group(this, SWT.NONE);
		startGameGroup.setLayout(new GridLayout(2, true));
		startGameGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		startGameGroup.setBackground(new Color(null, 212, 169, 127));
		
		createLabel(startGameGroup, "Name:");
		Text nameInput = createText(startGameGroup);

		createLabel(startGameGroup, "Floors:");
		Text zInput = createText(startGameGroup);

		createLabel(startGameGroup, "Rows:");
		Text yInput = createText(startGameGroup);

		createLabel(startGameGroup, "Columns:");
		Text xInput = createText(startGameGroup);

		KeyAdapter ketAdapter = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.CR)
					startBtnPressed(nameInput, zInput, yInput, xInput);
			}
		};
		
		nameInput.addKeyListener(ketAdapter);
		zInput.addKeyListener(ketAdapter);
		yInput.addKeyListener(ketAdapter);
		xInput.addKeyListener(ketAdapter);

		ButtonDisplay startBtn = new ButtonDisplay(startGameGroup, "Start Game");
		startBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		startBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				startBtnPressed(nameInput, zInput, yInput, xInput);
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	private void startBtnPressed(Text nameInput, Text zInput, Text yInput, Text xInput){
		String name = nameInput.getText();
		if (name.length() == 0) {
			gameView.view.displayMessage("You have to enter the maze name.");
			return;
		}

		String z = (zInput.getText().length() == 0) ? "0" : zInput.getText();
		String y = (yInput.getText().length() == 0) ? "0" : yInput.getText();
		String x = (xInput.getText().length() == 0) ? "0" : xInput.getText();

		gameView.view.update("generate_maze " + name + " " + z + " " + y + " " + x);
	}
	
	private void loadSaveGame() {
		String[] filterExt = { "*.bit" };

		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		group.setBackground(new Color(null, 212, 169, 127));

		FileDialog dialogSave = new FileDialog(getShell(), SWT.SAVE);
		dialogSave.setText("Save your game!");
		dialogSave.setFilterPath("C:/");
		dialogSave.setFilterExtensions(filterExt);

		saveBtn = new ButtonDisplay(group, "Save Game");
		saveBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		setButtonEnabled(saveBtn, false);
		saveBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				String savePath = dialogSave.open();
				if (savePath != null)
					gameView.view.update("save_maze " + gameView.maze.mazeName + " " + savePath);
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});

		FileDialog dialogOpen = new FileDialog(getShell(), SWT.OPEN);
		dialogOpen.setText("Load your game!");
		dialogOpen.setFilterPath("C:/");
		dialogOpen.setFilterExtensions(filterExt);

		loadBtn = new ButtonDisplay(group, "Load Game");
		loadBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		loadBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				String path = dialogOpen.open();
				if (path != null) {
					String name = dialogOpen.getFileNames()[0];
					name = name.substring(0, name.length() - 4);

					gameView.view.update("load_maze " + path + " " + name);
				}
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	private void goalGuide() {
		goalGroup = new Group(this, SWT.NONE);
		goalGroup.setLayout(new GridLayout(2, false));
		goalGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		goalGroup.setBackground(new Color(null, 212, 169, 127));

		createLabel(goalGroup, "Current Floor:");
		currentFloor = createLabel(goalGroup, "");

		createLabel(goalGroup, "Goal Floor:");
		goalFloor = createLabel(goalGroup, "");

		ButtonDisplay hintBtn = new ButtonDisplay(goalGroup, "HINT!");
		hintBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		hintBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Position position = gameView.maze.currentPosition;
				gameView.view.update("solve " + gameView.maze.mazeName + " Hint " + position.z + " " + position.y + " "
						+ position.x);
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});

		ButtonDisplay solveBtn = new ButtonDisplay(goalGroup, "Solve ");
		solveBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		solveBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Position position = gameView.maze.currentPosition;
				gameView.view.update("solve " + gameView.maze.mazeName + " Solve " + position.z + " " + position.y + " "
						+ position.x);
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	private void endExitGame() {
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		group.setBackground(new Color(null, 212, 169, 127));

		endBtn = new ButtonDisplay(group, "End Game");
		endBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		setButtonEnabled(endBtn, false);
		endBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Are you sure?");

				int response = messageBox.open();
				if (response == SWT.YES)
					gameView.displayWelcome();
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});

		ButtonDisplay exitBtn = new ButtonDisplay(group, "Exit Game");
		exitBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		exitBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				gameView.shell.close();
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	public void setCurrentFloorText(int z) {
		String txt = "" + (z + 1);
		currentFloor.setText(txt);
		goalFloor.getParent().layout();
	}

	public void setGoalFloorText(int z) {
		String txt = "" + (z + 1);
		goalFloor.setText(txt);
		goalFloor.getParent().layout();
	}

	public void displayGoalGuide() {
		goalGuide();
		if (startGameGroup != null) {
			goalGroup.moveAbove(startGameGroup);
			startGameGroup.dispose();
		}

		setButtonEnabled(loadBtn, false);
		setButtonEnabled(endBtn, true);
		setButtonEnabled(saveBtn, true);
		this.layout(true);
	}

	public void displayStartGame() {
		startGame();
		if (goalGroup != null) {
			startGameGroup.moveAbove(goalGroup);
			goalGroup.dispose();
		}

		setButtonEnabled(loadBtn, true);
		setButtonEnabled(endBtn, false);
		setButtonEnabled(saveBtn, false);
		this.layout(true);
	}

	private void setButtonEnabled(ButtonDisplay btn, boolean enable) {
		if (btn != null) {
			btn.setEnabled(enable);
			btn.redraw();
		}
	}

	private Label createLabel(Composite parent, String txt) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(txt);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		FontData[] fontData = label.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		label.setFont(new Font(getDisplay(), fontData[0]));

		return label;
	}

	private Text createText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		;
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		FontData[] fontData = text.getFont().getFontData();
		fontData[0].setHeight(FONT_SIZE);
		text.setFont(new Font(getDisplay(), fontData[0]));
		
		return text;
	}
}
