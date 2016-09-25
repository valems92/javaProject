package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import Common.Common;
import model.GameMaze3dModel;
import model.Model;
import presenter.CommandsManager;
import presenter.Maze3dCommands;

public class Maze3dClientHandler implements ClinetHandler {
    private CommandsManager commandManager;
    
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public Maze3dClientHandler() {
	Model model = new GameMaze3dModel(this);
	
	commandManager = new Maze3dCommands(this);
	commandManager.setCommands();
	commandManager.setModel(model);
    }

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
	try {
	    this.inFromClient = new ObjectInputStream(inFromClient);
	    this.outToClient = new ObjectOutputStream(outToClient);

	    read();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void read() {
	try {
	    Common cmd = (Common) inFromClient.readObject();
	    String command = (String) cmd.data[0];
	    
	    while (!command.equals("exit")) {
		System.out.println(cmd);

		commandManager.executeCommand(cmd);
		
		cmd = (Common) inFromClient.readObject();
		command = (String) cmd.data[0];
	    }
	    write(cmd);

	    inFromClient.close();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void write(Common o) {
	try {
	    outToClient.writeObject(o);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
