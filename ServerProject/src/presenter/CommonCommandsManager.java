package presenter;

import java.util.concurrent.ConcurrentHashMap;

import CommonData.CommonData;
import model.Model;

/**
 * <h1>CommonCommandsManager</h1> Implements all CommandsManager interface
 * functoins.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public abstract class CommonCommandsManager implements CommandsManager {
    protected Model model;
    protected ConcurrentHashMap<String, Command> commands;

    public CommonCommandsManager() {
	super();
	this.commands = new ConcurrentHashMap<String, Command>();
	setCommands();
    }

    public void executeCommand(CommonData o) {
	Command cmd = commands.get((String) o.data[0]);
	cmd.doCommand(o.data);
    }

    public void setModel(Model model) {
	this.model = model;
    }
}
