package presenter;

import java.io.IOException;

import CommonData.CommonData;
import model.Model;
import view.View;

/**
 * <h1>CommandsManager</h1> Responsible for defines the commands list, convert
 * string to class command and operate the model and view.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface CommandsManager {
    /**
     * <h1>executeCommand</h1> Responsible for convert string to class command
     * and operate the model and view.
     * <p>
     * 
     * @param commandLine
     *            - The string that include the relevant command
     * @throws IOException
     *             - Exception throws when the invalid command inserts
     */
    public void executeCommand(String commandLine) throws IOException;

    public void executeCommand(CommonData o);

    /**
     * <h1>setCommands</h1> Responsible for define the all commands for a
     * command manager
     * <p>
     */
    public void setCommands();

    /**
     * <h1>setModelView</h1> Responsible for link the command manager to view
     * and model
     * <p>
     * 
     * @param model
     *            - Type of model
     * @param view
     *            - Type of view
     */
    public void setModelView(Model model, View view);
}
