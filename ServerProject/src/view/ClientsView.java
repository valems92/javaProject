package view;

import server.Maze3dClientHandler;

public class ClientsView implements View {
	private ClientsWindow clientsWindow;
	
	@Override
	public void start() {
		clientsWindow = new ClientsWindow(500, 500);
		clientsWindow.run();
	}
	
	public void addClient(Maze3dClientHandler client) {
		clientsWindow.addClient(client);
	}
}
