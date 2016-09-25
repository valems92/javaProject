package boot;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Common.Common;
import presenter.CommandsManager;

public class ServerHandler {
    private Socket server;
    private ObjectInputStream inFromServer;
    private ObjectOutputStream outToServer;

    private CommandsManager commandManager;

    public ServerHandler(CommandsManager commandManager)
	    throws UnknownHostException, IOException, ClassNotFoundException {
	this.commandManager = commandManager;

	server = new Socket("localhost", 5400);

	OutputStream out = server.getOutputStream();
	outToServer = new ObjectOutputStream(out);

	InputStream in = server.getInputStream();
	inFromServer = new ObjectInputStream(in);
    }

    public void write(Common o) {
	try {
	    outToServer.writeObject(o);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void read() {
	Common cmd;
	try {
	    cmd = (Common) inFromServer.readObject();
	    commandManager.executeCommand(cmd);

	} catch (ClassNotFoundException | IOException e) {
	    e.printStackTrace();
	}
    }

    public void close() {
	try {
	    inFromServer.close();
	    outToServer.close();

	    server.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
