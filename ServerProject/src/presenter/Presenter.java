package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;

/**
 * <h1>Presenter</h1> 
 * Responsible for communicate between model and the ui. 
 * The class observe the model and de ui, and when a notification is recived from them, 
 * it execute the command received through the command manager.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class Presenter implements Observer {
	//private View ui;
	private Model model;
	private CommandsManager commandsManager;

	/**
	 * Initialize the data members and try to load data from SQL / Zip.
	 * @param ui UI of the program
	 * @param model Model of the program
	 * @param commandsManager Command manager wich execute the commands received
	 */
	public Presenter(/*View ui,*/ Model model, CommandsManager commandsManager) {
		//this.ui = ui;
		this.model = model;
		this.commandsManager = commandsManager;
		
		try {
			model.loadData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(Observable o, Object commandLine) {
		String cmd = (String) commandLine;
	}
}
