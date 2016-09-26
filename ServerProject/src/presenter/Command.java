package presenter;

/**
 * <h1>Command</h1> Responsible for perform the command that receive from Input
 * Source (console or gui)
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface Command {
    public void doCommand(Object[] args);
}
