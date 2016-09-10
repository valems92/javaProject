package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class MenuDisplay extends Canvas{
	
	Maze3dGameWindow gameView;
	
	public MenuDisplay(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout(1,false));
		Button playButton = new Button(this,SWT.PUSH);
		playButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		playButton.setText("Play Game");
		
		Button saveButton = new Button(this,SWT.PUSH);
		saveButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		saveButton.setText("Save Game");
		
		Button loadButton = new Button(this,SWT.PUSH);
		loadButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		loadButton.setText("Load Game");
		
		Button exitButton = new Button(this,SWT.PUSH);
		exitButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
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


	public void setGameView(Maze3dGameWindow gameView2) {
		this.gameView=gameView2;
		
	}
	
	
}
