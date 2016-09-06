package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import controller.CommandsManager;

/**
 * <h1>CLI</h1> Responsible for the read command, by thread channel, from Input Source,
 * update the controller and write to Output Source the relevant messages.
 * * <p>
 * @author Valentina Munoz & Moris Amon
 */

public class CLI {

	private BufferedReader in;
	private PrintWriter out;
	private CommandsManager cmd;

	public CLI(BufferedReader in, PrintWriter out, CommandsManager cmd) {
		super();
		this.in = in;
		this.out = out;
		this.cmd = cmd;
	}
	/**
	 * <h1>exit</h1> Responsible to exit the game, this method close the Input Source, Output Source
	 * and the Thread channel.
	 * <p>
	 */
	public void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
					String input = in.readLine();
					while (input != "exit") {
						cmd.executeCommand(input);
						input = in.readLine();
					}
					cmd.executeCommand(input);
				} catch (IOException e) {
					println(e.getMessage());
				}
			}
		}).start();
	}
	/**
	 * <h1>print</h1> Responsible to receive the data for write to CLI
	 * <p>
	 * @param str - String for write to Output Source
	 */
	public void exit() {
		try {
			this.in.close();
		} catch (IOException e) {
			println(e.getMessage());
		}
		System.out.println("The game closed");
		this.out.close();
		Thread.interrupted();	
		return;
	}

	/**
	 * <h1>print</h1> Responsible to write the data that receive from view to Output Output Source
	 * <p>
	 * @param str - String for write to Output Source
	 */
	
	public void println(String str) {
		out.println(str);
		out.flush();
	}

	/**
	 * <h1>print</h1> Responsible to write the data that receive from view to Output Output Source
	 * <p>
	 * @param str - String for write to Output Source
	 */
	public void print(String str) {
		out.print(str);
		out.flush();
	}
}
