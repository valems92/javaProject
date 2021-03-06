package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

public class Maze3dDisplay extends MazeDisplay {

	public Maze3dDisplay(Composite parent, int style, Maze3dGameWindow gameView) {
		super(parent, style);
		this.gameView = gameView;
	}

	/**
	 * <h1>paintCube</h1>
	 * Draws polygons to crate a cube effect.
	 * <p>
	 * @param p
	 * @param h
	 * @param e paintEvent of the maze widget
	 */
	private void paintCube(double[] p, double h, PaintEvent e) {
		int[] f = new int[p.length];
		for (int k = 0; k < f.length; f[k] = (int) Math.round(p[k]), k++);

		e.gc.drawPolygon(f);

		int[] r = f.clone();
		for (int k = 1; k < r.length; r[k] = f[k] - (int) (h), k += 2);

		int[] b = { r[0], r[1], r[2], r[3], f[2], f[3], f[0], f[1] };
		e.gc.drawPolygon(b);

		int[] fr = { r[6], r[7], r[4], r[5], f[4], f[5], f[6], f[7] };
		e.gc.drawPolygon(fr);

		e.gc.fillPolygon(r);
	}

	@Override
	protected void drawMaze() {
		Image goal = new Image(this.getDisplay(), "resources/honey.png");
		ImageData goalImgData = goal.getImageData();

		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int[][] cross = displayed;

				e.gc.setForeground(new Color(null, 138, 79, 47));
				e.gc.setBackground(new Color(null, 154, 95, 61));

				int width = getSize().x;
				int height = getSize().y;

				int mx = width / 2;

				double w = (width / cross[0].length) * scale;
				double h = (height / cross.length) * scale;

				for (int i = 0; i < cross.length; i++) {
					double w0 = 0.7 * w + 0.3 * w * i / cross.length;
					double w1 = 0.7 * w + 0.3 * w * (i + 1) / cross.length;
					double start = mx - w0 * cross[i].length / 2;
					double start1 = mx - w1 * cross[i].length / 2;

					int minDimension;
					if ((int) Math.round(h) < (int) Math.round((w0 + w1) / 2))
						minDimension = (int) Math.round(h);
					else
						minDimension = (int) Math.round((w0 + w1) / 2);

					for (int j = 0; j < cross[i].length; j++) {
						double[] dpoints = { start + j * w0, i * h, start + j * w0 + w0, i * h, start1 + j * w1 + w1,
								i * h + h, start1 + j * w1, i * h + h };
						double cheight = h / 2;

						if (!win) {
							if (currentPosition.z == goalPosition.z && currentPosition.y == goalPosition.y
									&& currentPosition.x == goalPosition.x) {
								ShowWinWindows();
								win = true;
							}
						}

						if (cross[i][j] != 0)
							paintCube(dpoints, cheight, e);
						else {	
							if (currentPosition.z == goalPosition.z && goalPosition.y == i && goalPosition.x == j)
								e.gc.drawImage(goal, 0, 0, goalImgData.width, goalImgData.height,
										(int) Math.round(dpoints[0]), (int) Math.round(dpoints[1] - cheight / 2),
										minDimension, minDimension);
							
							if (i == currentPosition.y && j == currentPosition.x) 
								character.paint(e, (int) Math.round(dpoints[0]),
										(int) Math.round(dpoints[1] - cheight / 2), minDimension, minDimension);
						}
					}
				}
			}
		});
	}
}