package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import presenter.Properties;
import server.MyTCPIPServer;

public class Run {
	private final static String FILE_NAME = "properties.xml";
	
	public static void main(String[] args) {
		System.out.println("Server Side");
		
		try {
			readProperties();
		} catch (Exception e) {
			try {
				generateProperties();
			} catch (Exception e1) {
				System.out.println("There was an error while generating properties file.");
				return;
			}
		}
		
		try {
			MyTCPIPServer server = new MyTCPIPServer(Properties.properites.getPort(), Properties.properites.getNumClients());
			server.start();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	public static void readProperties() throws Exception {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE_NAME)));
		Properties.properites = (Properties) decoder.readObject();
		decoder.close();
	}

	public static void writeProperties() throws Exception {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
		encoder.writeObject(Properties.properites);
		encoder.close();
	}

	private static void generateProperties() throws Exception {
		Properties.properites.setViewType("gui");
		Properties.properites.setViewHeight(820);
		Properties.properites.setViewWidth(1200);
		Properties.properites.setGenerateAlgorithm("GrowingTree");
		Properties.properites.setSelectCellMethod("Random");
		Properties.properites.setSolveAlgorithm("BFS");
		Properties.properites.setComparator("Cost");
		Properties.properites.setNumberOfThreads(5);
		Properties.properites.setMySQL(false);
		Properties.properites.setHintLength(0.5);
		Properties.properites.setAnimationSpeed(200);
		Properties.properites.setMazeDisplay("3d");
		Properties.properites.setIpAddress("localhost");
		Properties.properites.setPort(5400);
		Properties.properites.setNumClients(5);

		writeProperties();
	}
}