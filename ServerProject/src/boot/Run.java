package boot;

import server.MyTCPIPServer;

public class Run {

	public static void main(String[] args) {
		System.out.println("Server Side");
		
		try {
			MyTCPIPServer server = new MyTCPIPServer(5400, 10);
			server.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}