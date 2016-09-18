package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class GameCharacter {
	private Image character;
	private Image flipedCharacter;
	
	private ImageData characterImgData;
	private ImageData characterFlipImgData;
	
	private int lastXPoisition;
	private String lastImg;

	public GameCharacter(Display display, String imgPath, String flipImgPath) {
		character = new Image(display, imgPath);
		characterImgData = character.getImageData();
		
		flipedCharacter = new Image(display, flipImgPath);
		characterFlipImgData = flipedCharacter.getImageData();
		
		lastXPoisition = -1;
		lastImg = "character";
	}

	public void paint(PaintEvent e, int x, int y, int w, int h) {
		if (lastXPoisition == -1 || lastXPoisition < x || (lastXPoisition == x && lastImg == "character")) {
			e.gc.drawImage(character, 0, 0, characterImgData.width, characterImgData.height, x, y, w, h);
			lastImg = "character";
		} else {
			e.gc.drawImage(flipedCharacter, 0, 0, characterFlipImgData.width, characterFlipImgData.height, x, y, w, h);
			lastImg = "flipedCharacter";
		}
	
		lastXPoisition = x;
	}
}
