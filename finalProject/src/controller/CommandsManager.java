package controller;

import java.io.IOException;

import model.Model;
import view.View;

public interface CommandsManager {
	public void executeCommand(String commandLine) throws IOException;
	public void setCommands();
	public void setModelView(Model model, View view);

}
