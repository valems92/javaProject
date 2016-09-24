package presenter;

import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.View;

/**
 * <h1>CommonCommandsManager</h1> 
 * Implements all CommandsManager interface functoins.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public abstract class CommonCommandsManager implements CommandsManager{
	
	protected Model model;
	protected View ui;
	protected ConcurrentHashMap<String, Command> commands;
	
	public CommonCommandsManager() {
		super();
		this.commands = new ConcurrentHashMap<String, Command>();
		setCommands();
	}

	public void executeCommand(String commandLine) {
		String[] values = commandLine.split(" ");		
		Command cmd = commands.get(values[0]);
		if (cmd == null){
			ui.displayMessage("Invalid command");
			return;
		}
		cmd.doCommand(values);
	}
	
	@Override
	public void setModelView(Model model, View ui) {
		this.model = model;
		this.ui = ui;
	}
}
