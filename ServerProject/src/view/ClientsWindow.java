package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;

import server.Maze3dClientHandler;

public class ClientsWindow extends BasicWindow {
	private Group clientsGroup;
	private Group statusGroup;
	private Group disconnectGroup;
	
	public ClientsWindow(int width, int height) {
		super(width, height);

		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				closeServer();
			}
		});

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(3, true));
		shell.setBackground(new Color(null, 255, 255, 255));
		
		Label clients = new Label(shell, SWT.BORDER);
		clients.setText("Clients ID:");
		clients.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		Label status = new Label(shell, SWT.BORDER);
		status.setText("Status:");
		status.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		Label dissconect = new Label(shell, SWT.BORDER);
		dissconect.setText("Disconnect:");
		dissconect.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		clientsGroup = new Group(shell, SWT.BORDER);
		clientsGroup.setLayout(new GridLayout(1, true));
		clientsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		statusGroup = new Group(shell, SWT.BORDER);
		statusGroup.setLayout(new GridLayout(1, true));
		statusGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		disconnectGroup = new Group(shell, SWT.BORDER);
		disconnectGroup.setLayout(new GridLayout(1, true));
		disconnectGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	public void addClient(Maze3dClientHandler client){
		Label clientID = new Label(clientsGroup, SWT.BORDER);
		clientID.setText(client.getID());
		clientID.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Button disconnect = new Button(disconnectGroup, SWT.BORDER);
		disconnect.setText("disconnect");
		disconnect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public void closeServer() {

	}

}
