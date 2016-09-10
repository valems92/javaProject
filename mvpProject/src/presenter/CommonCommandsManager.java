package presenter;

import java.util.concurrent.ConcurrentHashMap;

import model.Model;
import view.View;

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
			//view.println("Invalid command");
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
