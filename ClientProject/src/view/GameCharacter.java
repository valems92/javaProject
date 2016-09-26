package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/**
 * <h1>GameCharacter</h1> Represent the character in maze. Draw the character on
 * each move or screen resize.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class GameCharacter {
    private Image character;
    private Image flipedCharacter;

    private ImageData characterImgData;
    private ImageData characterFlipImgData;

    private int lastXPoisition;
    private String lastImg;

    /**
     * <h1>GameCharacter</h1> Initialize data and images.
     * <p>
     * 
     * @param display
     *            display to add the character
     * @param imgPath
     *            image path of character (left)
     * @param flipImgPath
     *            image path of character (right)
     */
    public GameCharacter(Display display, String imgPath, String flipImgPath) {
	character = new Image(display, imgPath);
	characterImgData = character.getImageData();

	flipedCharacter = new Image(display, flipImgPath);
	characterFlipImgData = flipedCharacter.getImageData();

	lastXPoisition = -1;
	lastImg = "character";
    }

    /**
     * <h1>paint</h1> On each move or screen resize, paint is called. It check
     * if the character is moving right or left and draw the correct image.
     * <p>
     * 
     * @param e
     *            paintEvent of maze widget
     * @param x
     *            x position in screen
     * @param y
     *            y position in screen
     * @param w
     *            character width
     * @param h
     *            character height
     */
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
