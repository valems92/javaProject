package controller;

import java.io.IOException;

/**
 * <h1>Controller</h1> Responsible for communicate the model to the view and
 * vice versa
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface Controller {
	/**
	 * <h1>print</h1> Send to the view the string received si it can be
	 * displayed to user.
	 * <p>
	 * 
	 * @param str
	 *            String to print
	 */
	void print(String str);

	/**
	 * <h1>println</h1> Send to the view the string received si it can be
	 * displayed to user. After it was displayed, add a new line.
	 * <p>
	 * 
	 * @param str
	 *            String to print
	 */
	void println(String str);

	/**
	 * <h1>executeCommand</h1>
	 * Receive a command from the view and through the model, execute it.
	 * <p>
	 * 
	 * @param commandLine Command to execute
	 * @throws IOException
	 */
	void executeCommand(String commandLine) throws IOException;
}
