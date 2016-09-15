package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MenuDisplay extends Canvas {
	private final int FONT_SIZE = 16;
	private Maze3dGameWindow gameView;

	private Group startGameGroup;

	private Group goalGroup;
	private Label currentFloor;
	private Label goalFloor;

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

		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		group.setBackground(new Color(null, 212, 169, 127));

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

	private void startGame() {
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

		ButtonDisplay startBtn = new ButtonDisplay(startGameGroup, "Start Game");
		startBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		startBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				if (nameInput.getText().length() == 0 || zInput.getText().length() == 0
						|| yInput.getText().length() == 0 || xInput.getText().length() == 0) {
					gameView.view.displayMessage("You need to enter the maze name and dimension.");
					return;
				}
				gameView.view.update("generate_maze " + nameInput.getText() + " " + zInput.getText() + " "
						+ yInput.getText() + " " + xInput.getText());
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	private void loadSaveGame() {
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		group.setBackground(new Color(null, 212, 169, 127));

		createLabel(group, "Name:");
		Text nameInput = createText(group);

		ButtonDisplay saveBtn = new ButtonDisplay(group, "Save Game");
		saveBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		saveBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				if (nameInput.getText().length() == 0) {
					gameView.view.displayMessage("You need to enter the maze name.");
					return;
				}
				gameView.view.update("save_maze " + nameInput.getText());
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});

		ButtonDisplay loadBtn = new ButtonDisplay(group, "Load Game");
		loadBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		loadBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				if (nameInput.getText().length() == 0) {
					gameView.view.displayMessage("You need to enter the maze name.");
					return;
				}
				// gameView.view.update("load_maze " + nameInput.getText());
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

		ButtonDisplay showGoalBtn = new ButtonDisplay(goalGroup, "Show Goal");
		showGoalBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		showGoalBtn.setMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
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

		this.layout(true);
	}

	public void displayStartGame() {
		startGame();
		if (goalGroup != null) {
			startGameGroup.moveAbove(goalGroup);
			goalGroup.dispose();
		}

		this.layout(true);
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
