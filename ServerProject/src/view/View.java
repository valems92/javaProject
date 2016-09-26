package view;

import server.Maze3dClientHandler;

public interface View {
	/**
	 * <h1>start</h1> 
	 * Responsible to start the program.
	 * <p>
	 */
	public void start();
	
	public void addClient(Maze3dClientHandler client);
}
