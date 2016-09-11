package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;

public class MenuDisplay extends Canvas {
	Maze3dGameWindow gameView;

	public MenuDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;

		initWidgets();
	}

	private void initWidgets() {
		this.setLayout(new GridLayout(2, false));

		startGame();

		loadSaveGame();

		Button exitButton = new Button(this, SWT.PUSH);
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		exitButton.setText("Exit");

		exitButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				gameView.shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void startGame() {
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		group.setBackground(new Color(null, 84, 178, 93));
		
		Label nameLabel = new Label(group, SWT.NONE);
		nameLabel.setText("Name:");
		nameLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Text nameInput = new Text(group, SWT.BORDER);
		nameInput.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		Label zLabel = new Label(group, SWT.NONE);
		zLabel.setText("Floors:");
		zLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Text zInput = new Text(group, SWT.BORDER);
		zInput.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		Label yLabel = new Label(group, SWT.NONE);
		yLabel.setText("Rows:");
		yLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Text yInput = new Text(group, SWT.BORDER);
		yInput.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		Label xLabel = new Label(group, SWT.NONE);
		xLabel.setText("Columns:");
		xLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Text xInput = new Text(group, SWT.BORDER);
		xInput.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		Button playButton = new Button(group, SWT.PUSH);
		playButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		playButton.setText("Start Game");
		playButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				gameView.view.update("generate_maze " + nameInput.getText() + " " + zInput.getText() + " "
						+ yInput.getText() + " " + xInput.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void loadSaveGame() {
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name:");
		nameLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Text nameInput = new Text(this, SWT.BORDER);
		nameInput.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1));

		Button saveButton = new Button(this, SWT.PUSH);
		saveButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		saveButton.setText("Save Game");
		saveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				gameView.view.update("save_maze " + nameInput.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button loadButton = new Button(this, SWT.PUSH);
		loadButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		loadButton.setText("Load Game");
		loadButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// gameView.view.update("generate_maze " + nameInput.getText() +
				// " " + zInput.getText() + " " + yInput.getText() + " " +
				// xInput.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}
}
