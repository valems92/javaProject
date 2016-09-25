package server;

import java.io.InputStream;
import java.io.OutputStream;

import Common.Common;

public interface ClinetHandler {
	public void handleClient(InputStream inFromClient, OutputStream outToClient);
	
	public void write(Common o);
}
