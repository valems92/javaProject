package view;

import org.eclipse.swt.SWT;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * <h1>GameMaze3dGuiView</h1> Implements all View interface functoins for gui
 * view.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class GameMaze3dGuiView extends CommonMaze3dView {
    private Maze3dGameWindow window;
    protected Object settings;

    @Override
    public void start() {
	window = new Maze3dGameWindow(Properties.properites.getViewWidth(), Properties.properites.getViewHeight(),
		this);
	window.run();
    }

    @Override
    public void displayMaze(Maze3d maze, String name) {
	window.displayMaze(maze, name);
    }

    @Override
    public void displaySolution(Solution<Position> solution, String type) {
	window.displaySolution(solution, type);
    }

    @Override
    public void displayMessage(String msg) {
	window.displayMessage(msg, SWT.ICON_INFORMATION | SWT.OK, null, "");
    }

    @Override
    public void displayExistingMaze(Maze3d maze, String name, String msg) {
	window.displayMessage(msg + "\n Do you want to load it?", SWT.ICON_QUESTION | SWT.YES | SWT.NO, maze, name);
    }

    @Override
    public void displayCrossSection(int[][] crossSection) {
	window.displayCrossSection(crossSection);
    }

    @Override
    public void exit() {

    }

    /**
     * <h1>update</h1> Get a command and send it via notification to the
     * presenter.
     * <p>
     * 
     * @param command
     *            command to send to presenter
     */
    public void update(String command) {
	setChanged();
	notifyObservers(command);
    }

    @Override
    public Object getSettings() {
	return window.getSettings();
    }

}
