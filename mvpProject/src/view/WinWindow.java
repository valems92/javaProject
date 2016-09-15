package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class WinWindow extends DialogWindow {
	
	Maze3dGameWindow gameView;
	
	@Override
	protected void initWidgets() {
		
		Canvas winPage = new Canvas(this.shell, SWT.NONE);
		shell.setLayout(new GridLayout(2,false));
		shell.setText("You won!");
		shell.setBackground(new Color(null,255,255,255));
		winPage.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,2));
		shell.setSize(1000, 800);
		Image bgImg = new Image(shell.getDisplay(), "images/welcome.jpg");
		ImageData imgData = bgImg.getImageData();	
		winPage.setBackgroundImage(bgImg);
		winPage.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {		
				e.gc.drawImage(bgImg, 0, 0, imgData.width, imgData.height, 0, 0, shell.getSize().x, shell.getSize().y);
				
			}
		});

			
		
	}

}
