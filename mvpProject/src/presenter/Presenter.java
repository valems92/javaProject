package presenter;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {
	private View ui;
	private Model model;
	private CommandsManager commandsManager;

	public Presenter(View ui, Model model, CommandsManager commandsManager) {
		this.ui = ui;
		this.model = model;
		this.commandsManager = commandsManager;
		
		try {
			model.loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object commandLine) {
		String cmd = (String) commandLine;
		
		if (o == ui) 
			updateUi(cmd);
		
		else if (o == model) 
			updateModel(cmd);
	}

	private void updateUi(String commandLine) {
		try {
			commandsManager.executeCommand(commandLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateModel(String commandLine) {
		try {
			commandsManager.executeCommand(commandLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
