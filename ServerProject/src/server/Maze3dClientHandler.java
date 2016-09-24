package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.GameMaze3dModel;
import model.Model;
import presenter.CommandsManager;
import presenter.Maze3dCommands;

public class Maze3dClientHandler implements ClinetHandler{

	Model model;
	CommandsManager commandManager;
	public Maze3dClientHandler() {

	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try{
			System.out.println("start");
			commandManager = new Maze3dCommands(outToClient);
			commandManager.setCommands();
			Model model=new GameMaze3dModel(outToClient);
			commandManager.setModelView(model);
			ObjectInputStream in=new ObjectInputStream(inFromClient);
			//PrintWriter out=new PrintWriter(outToClient);
			String[] line=(String[]) in.readObject();
			while(!(line[0].equals("exit"))){
				if(line[0].equals("generate_maze")){
						commandManager.executeCommand(line);
						line[0]="1";
				}
			}
			in.close();
			//out.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
