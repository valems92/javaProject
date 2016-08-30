package view;

import java.io.BufferedReader;

import java.io.PrintWriter;

import controller.CommandsManager;

/**
 * <h1>Model</h1> Responsible for the view section.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */

public interface View {
	/**
	 * 	 <h1>setCLI</h1> Responsible for initialization the CLI process
	 * 
	 * @param in - Input Source
	 * @param out - Output Source
	 * @param cmd - Class type of CommandsManager which contains commands set
	 */
	public void setCLI(BufferedReader in, PrintWriter out, CommandsManager cmd);
	/**
	 * <h1>start</h1> Responsible to call the start method from CLI
	 * <p>
	 */
	public void start();
	/**
	 * <h1>exit</h1> Responsible to call the exit method from CLI
	 * <p>
	 */
	public void exit();
	/**
	 * <h1>print</h1> Responsible to receive the data for write to CLI
	 * <p>
	 * @param str - String for write to Output Source
	 */
	public void print(String str);
	/**
	 * <h1>print</h1> Responsible to receive the data for write to CLI
	 * <p>
	 * @param str - String for write to Output Source
	 */
	public void println(String str);
}
