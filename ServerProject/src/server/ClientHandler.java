package server;

import java.io.InputStream;
import java.io.OutputStream;

import CommonData.CommonData;

public interface ClientHandler {
    public void handleClient(InputStream inFromClient, OutputStream outToClient);

    public void write(CommonData o);
}
