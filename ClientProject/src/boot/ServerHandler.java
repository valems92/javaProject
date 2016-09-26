package boot;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import CommonData.CommonData;
import presenter.CommandsManager;
import presenter.Properties;

public class ServerHandler {
	private Socket server;
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;

	private CommandsManager commandManager;

	public ServerHandler(CommandsManager commandManager)
			throws UnknownHostException, IOException, ClassNotFoundException {
		this.commandManager = commandManager;

		server = new Socket(Properties.properites.getIpAddress(), Properties.properites.getPort());

		OutputStream out = server.getOutputStream();
		outToServer = new ObjectOutputStream(out);

		InputStream in = server.getInputStream();
		inFromServer = new ObjectInputStream(in);

		Object[] data = { "set_properties", Properties.properites };
		CommonData o = new CommonData(data);
		write(o);
	}

	public void write(CommonData o) {
		try {
			outToServer.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void read() {
		CommonData cmd;
		try {
			cmd = (CommonData) inFromServer.readObject();
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
