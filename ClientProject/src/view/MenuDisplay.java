package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Position;
import presenter.Properties;

/**
 * <h1>MenuDisplay</h1> A custom widget for menu disaply.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
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

    public ArrowDisplay upArrow;
    public ArrowDisplay downArrow;

    public MenuDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
	super(parent, style);
	this.gameView = gameView;

	this.setLayout(new GridLayout(2, false));
	this.setBackground(new Color(null, 116, 73, 38));
	this.setBackgroundMode(SWT.INHERIT_DEFAULT);

	initWidgets();
    }

    /**
     * <h1>initWidgets</h1> Initialize the widget with the start, load-save and
     * exit groups.
     * <p>
     */
    private void initWidgets() {
	displayStartGame();

	loadSaveGame();
	endExitGame();
    }

    /**
     * <h1>startGame</h1> Create a new group that contains labels, texts and a
     * button to generate a new game. Add to button mouse up and enter key
     * event. Both events calls the same function: onStartBtnPressed.
     * <p>
     */
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
		if (e.keyCode == SWT.CR)
		    onStartBtnPressed(nameInput, zInput, yInput, xInput);
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
		onStartBtnPressed(nameInput, zInput, yInput, xInput);
	    }

	    @Override
	    public void mouseDown(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseDoubleClick(MouseEvent arg0) {
	    }
	});
    }

    /**
     * <h1>onStartBtnPressed</h1> When start button pressed, check if all needed
     * data was entered. If no maze name was entered, the program disaply a
     * massage. Else, throws a notification to presenter with all data.
     * <p>
     * 
     * @param nameInput
     *            name Text widget
     * @param zInput
     *            floors Text widget
     * @param yInput
     *            columns Text widget
     * @param xInput
     *            rows Text widget
     */
    private void onStartBtnPressed(Text nameInput, Text zInput, Text yInput, Text xInput) {
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

    /**
     * <h1>loadSaveGame</h1> Create a new group that contains two buttons to
     * save or load a maze.
     * <p>
     * The save button is available only when a maze is dislpayed. it opens a
     * file dialog to select a path where to save the maze.
     * <p>
     * The load button is available only when the welcome window is dislpayed.
     * it opens a file dialog to select the file wanted.
     * <p>
     * Both buttons throw the according notifications to the presenter.
     * <p>
     */
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

    /**
     * <h1>goalGuide</h1> Create a new group. The group contains labels that
     * represent the goal and current positions, and two buttons: Hint and
     * Solve. Also contains two arrows to move up or down floors.
     * <p>
     * Both buttons throw a solve notification to the presenter with different
     * types.
     * <p>
     */
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

	addArrows(goalGroup);
    }

    /**
     * <h1>addArrows</h1> Add to goal group two arrows: up and down. The arrows
     * represent if up or down moves are possible from the character position.
     * Also, add to it events of move up or down if enable.
     * <p>
     * 
     * @param parent
     *            parent to add it the arrows to
     */
    private void addArrows(Composite parent) {
	upArrow = new ArrowDisplay(parent, "resources/arrowUp.png", "resources/arrowUpOpacity.png");
	upArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
	upArrow.setMouseListener(new MouseListener() {
	    @Override
	    public void mouseUp(MouseEvent arg0) {
		gameView.maze.addArrowsEvents("Up");
	    }

	    @Override
	    public void mouseDown(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseDoubleClick(MouseEvent arg0) {
	    }
	});
	setArrowEnabled(upArrow, false);

	downArrow = new ArrowDisplay(parent, "resources/arrowDown.png", "resources/arrowDownOpacity.png");
	downArrow.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
	downArrow.setMouseListener(new MouseListener() {
	    @Override
	    public void mouseUp(MouseEvent arg0) {
		gameView.maze.addArrowsEvents("Down");
	    }

	    @Override
	    public void mouseDown(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseDoubleClick(MouseEvent arg0) {
	    }
	});
	setArrowEnabled(downArrow, false);
    }

    /**
     * <h1>setArrowEnabled</h1> Change arrow received availability.
     * <p>
     * 
     * @param arrow
     *            arrow to change it availability
     * @param enable
     *            true/false
     */
    public void setArrowEnabled(ArrowDisplay arrow, boolean enable) {
	if (arrow != null) {
	    arrow.setEnabled(enable);
	    arrow.redraw();
	}
    }

    /**
     * <h1>endExitGame</h1> Create a new group that contains and three buttons:
     * settings, end game and exit game.
     * <p>
     * The settings button opens a dialog with all the properties of the
     * program, to create a new object of it.
     * <p>
     * The end game button is available only when a maze is dislpayed. On
     * pressing on it, it display a massage box, and if the YES button is
     * pressed, the game ends and welcome window in created instead of maze.
     * <p>
     * The exit game button, close game shell and exit game.
     * <p>
     */
    private void endExitGame() {
	Group group = new Group(this, SWT.NONE);
	group.setLayout(new GridLayout(2, true));
	group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
	group.setBackground(new Color(null, 212, 169, 127));

	ButtonDisplay settingBtn = new ButtonDisplay(group, "Settings ");
	settingBtn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
	settingBtn.setMouseListener(new MouseListener() {

	    @Override
	    public void mouseUp(MouseEvent arg0) {
		PropertiesFormWindow properties = new PropertiesFormWindow(400, 700, Properties.class, gameView);
		properties.start(gameView.display);
	    }

	    @Override
	    public void mouseDown(MouseEvent arg0) {
	    }

	    @Override
	    public void mouseDoubleClick(MouseEvent arg0) {
	    }
	});

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

    /**
     * <h1>setCurrentFloorText</h1> Set the text that represent the current
     * floor in the goal group.
     * <p>
     * 
     * @param z
     *            current floor
     */
    public void setCurrentFloorText(int z) {
	String txt = "" + (z + 1);
	currentFloor.setText(txt);
	goalFloor.getParent().layout();
    }

    /**
     * <h1>setGoalFloorText</h1> Set the text that represent the goal floor in
     * the goal group.
     * <p>
     * 
     * @param z
     *            goal floor
     */
    public void setGoalFloorText(int z) {
	String txt = "" + (z + 1);
	goalFloor.setText(txt);
	goalFloor.getParent().layout();
    }

    /**
     * <h1>displayGoalGuide</h1> Create the goal group, and place it instead of
     * the start group. Dispose the start group.
     * <p>
     * Change load button, end button and save button availability.
     * <p>
     */
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

    /**
     * <h1>displayStartGame</h1> Create the start group, and place it instead of
     * the goal group. Dispose the goal group.
     * <p>
     * Change load button, end button and save button availability.
     * <p>
     */
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

    /**
     * <h1>setButtonEnabled</h1> Change button received availability.
     * <p>
     * 
     * @param btn
     *            button to change it availability
     * @param enable
     *            true/false
     */
    private void setButtonEnabled(ButtonDisplay btn, boolean enable) {
	if (btn != null) {
	    btn.setEnabled(enable);
	    btn.redraw();
	}
    }

    /**
     * <h1>createLabel</h1> Create a new label. Change it font size.
     * <p>
     * 
     * @param parent
     *            label parent
     * @param txt
     *            label text
     * @return the new label
     */
    private Label createLabel(Composite parent, String txt) {
	Label label = new Label(parent, SWT.NONE);
	label.setText(txt);
	label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

	FontData[] fontData = label.getFont().getFontData();
	fontData[0].setHeight(FONT_SIZE);
	label.setFont(new Font(getDisplay(), fontData[0]));

	return label;
    }

    /**
     * <h1>createText</h1> Create a new text. Change it font size.
     * <p>
     * 
     * @param parent
     *            text parent
     * @param txt
     *            text content
     * @return the new Text
     */
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
