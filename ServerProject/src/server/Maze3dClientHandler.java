package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import CommonData.CommonData;
import model.GameMaze3dModel;
import model.Model;
import presenter.CommandsManager;
import presenter.Maze3dCommands;

public class Maze3dClientHandler implements ClientHandler {
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
			CommonData cmd;
			String command = "";

			while (!command.equals("exit")) {
				cmd = (CommonData) inFromClient.readObject();
				command = (String) cmd.data[0];
				System.out.println("cmd " + command);
				commandManager.executeCommand(cmd);
			}

			close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(CommonData o) {
		try {
			outToClient.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			inFromClient.close();
			outToClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
