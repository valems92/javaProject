package controller;

import java.io.IOException;

import model.Model;
import view.View;

/**
 * <h1>CommandsManager</h1>
 * 
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface CommandsManager {
	public void executeCommand(String commandLine) throws IOException;
	public void setCommands();
	public void setModelView(Model model, View view);

}
