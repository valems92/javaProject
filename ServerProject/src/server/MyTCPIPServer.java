package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyTCPIPServer {
    int port;
    ServerSocket server;

    ClinetHandler clinetHandler;
    int numOfClients;

    volatile boolean stop;

    Thread mainServerThread;
    ExecutorService threadpool;

    int clientsHandled = 0;

    public MyTCPIPServer(int port, ClinetHandler clinetHandler, int numOfClients) {
	this.port = port;
	this.clinetHandler = clinetHandler;
	this.numOfClients = numOfClients;
	
	
    }

    public void start() throws Exception {
	server = new ServerSocket(port);
	server.setSoTimeout(10 * 1000);
	threadpool = Executors.newFixedThreadPool(numOfClients);

	mainServerThread = new Thread(new Runnable() {
	    @Override
	    public void run() {
		while (!stop) {
		    try {
			final Socket someClient = server.accept();
			if (someClient != null) {
			    threadpool.execute(new Runnable() {
				@Override
				public void run() {
				    try {
					 clientsHandled++;
					 clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
					 
					someClient.close();
					
				    } catch (IOException e) {
					e.printStackTrace();
				    }
				}
			    });
			}
		    } catch (SocketTimeoutException e) {
			System.out.println("no clinet connected...");
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		System.out.println("done accepting new clients.");
	    }
	});

	mainServerThread.start();
    }

    public void close() throws Exception {
	stop = true;

	System.out.println("shutting down");
	threadpool.shutdown();

	while (!(threadpool.awaitTermination(10, TimeUnit.SECONDS)))
	    ;

	mainServerThread.join();

	server.close();
	System.out.println("server is safely closed");
    }
}