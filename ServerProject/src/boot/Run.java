package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.Maze3dClientHandler;
import server.MyTCPIPServer;

public class Run {

    public static void main(String[] args) {
	System.out.println("Server Side");

	try {
	    MyTCPIPServer server = new MyTCPIPServer(5400, new Maze3dClientHandler(), 10);
	    server.start();

	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    while (!(in.readLine()).equals("close the server"));
	    server.close();

	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}