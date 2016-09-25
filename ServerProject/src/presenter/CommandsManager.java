package presenter;

import java.io.IOException;

import Common.Common;
import model.Model;

/**
 * <h1>CommandsManager</h1> 
 * Responsible for defines the commands list, convert string to class command and operate the model and view.
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public interface CommandsManager {
	/**
	 * <h1>executeCommand</h1> 
	 * Responsible for convert string to class command and operate the model and view.
	 * <p>
	 * @param commandLine - The string that include the relevant command    
	 * @throws IOException - Exception throws when the invalid command inserts        
	 */
	public void executeCommand(Common o) throws IOException;
	
	/**
	 * <h1>setCommands</h1> Responsible for define the all commands for a command manager
	 * <p>
	 */
	public void setCommands();
	
	public void setModel(Model model);
}
