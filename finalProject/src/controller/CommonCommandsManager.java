package controller;

import java.io.IOException;
import java.util.HashMap;

import model.Model;
import view.View;

public abstract class CommonCommandsManager implements CommandsManager{
	
	protected Model model;
	protected View view;
	protected HashMap<String, Command> commands;
	
	public CommonCommandsManager() {
		super();
		this.commands = new HashMap<String, Command>();
		setCommands();
	}

	public void executeCommand(String commandLine) throws IOException {
		String[] values = commandLine.split(" ");		
		Command cmd = commands.get(values[0]);
		if (cmd == null)
			throw new IOException("Invalid command");
		cmd.doCommand(values);
	}
	
	@Override
	public void setModelView(Model model, View view) {
		this.model = model;
		this.view = view;
	}

}
