package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class GameCharacter {
	private Image character;
	private Image flipedCharacter;
	
	private ImageData characterImgData;
	private int lastXPoisition;
	private String lastImg;
	private String lastState;

	public GameCharacter(Display display, String imgPath) {
		character = new Image(display, imgPath);
		characterImgData = character.getImageData();
		flipedCharacter = new Image(display, flip(characterImgData));
		lastXPoisition = -1;
		lastImg = "character";
	}

	static ImageData flip(ImageData srcData) {
		int bytesPerPixel = srcData.bytesPerLine / srcData.width;
		int destBytesPerLine = srcData.width * bytesPerPixel;
		byte[] newData = new byte[srcData.data.length];

		for (int srcY = 0; srcY < srcData.height; srcY++) {
			for (int srcX = 0; srcX < srcData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;

				destX = srcData.width - srcX - 1;
				destY = srcY;

				destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
				srcIndex = (srcY * srcData.bytesPerLine) + (srcX * bytesPerPixel);
				System.arraycopy(srcData.data, srcIndex, newData, destIndex, bytesPerPixel);
			}
		}
		return new ImageData(srcData.width, srcData.height, srcData.depth, srcData.palette, srcData.scanlinePad,
				newData);
	}

	public void paint(PaintEvent e, int x, int y, int w, int h) {
		if (lastXPoisition == -1 || lastXPoisition < x || (lastXPoisition == x && lastImg == "character")) {
			e.gc.drawImage(character, 0, 0, characterImgData.width, characterImgData.height, x, y, w, h);
			lastImg = "character";
		} else {
			e.gc.drawImage(flipedCharacter, 0, 0, characterImgData.width, characterImgData.height, x, y, w, h);
			lastImg = "flipedCharacter";
		}
	
		lastXPoisition = x;
	}
}
