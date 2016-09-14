package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class GameCharacter {
	private Image character;
	private ImageData characterImgData;	
	
	public GameCharacter(Display display, String imgPath) {
		character = new Image(display, imgPath);
		characterImgData = character.getImageData();
	}
	
	public void paint(PaintEvent e, int x, int y, int w, int h) {
		e.gc.drawImage(character, 0, 0, characterImgData.width, characterImgData.height, x, y, w, h);
	}
}
