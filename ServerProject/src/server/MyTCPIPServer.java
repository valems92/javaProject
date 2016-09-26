package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import view.ClientsView;

public class MyTCPIPServer {
	ClientsView clientsView;
	int port;
	ServerSocket server;

	int numOfClients;

	volatile boolean stop;

	Thread mainServerThread;
	ExecutorService threadpool;

	public MyTCPIPServer(int port, int numOfClients) {
		this.port = port;
		this.numOfClients = numOfClients;
		
		//clientsView = new ClientsView();
		//clientsView.start();
	}

	public void start() throws Exception {
		server = new ServerSocket(port);
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
										Maze3dClientHandler clinetHandler = new Maze3dClientHandler();
										clientsView.addClient(clinetHandler);
										
										clinetHandler.handleClient(someClient.getInputStream(),
												someClient.getOutputStream());

										someClient.close();

									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}
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