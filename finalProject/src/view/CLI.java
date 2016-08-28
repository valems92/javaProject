package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import controller.CommandsManager;

public class CLI {
	
	private BufferedReader in;
	private PrintWriter out;
	private CommandsManager cmd;
	
	public CLI(BufferedReader in, PrintWriter out, CommandsManager cmd){
		super();
		this.in = in;
		this.out = out;
		this.cmd=cmd;
	}
	
	public void start(){
	new Thread(new Runnable() {
		   public void run() {
		     try {
		    	  String input = in.readLine();
		    	 	while( input != "exit"){
		    	 		cmd.executeCommand(input);
		    	 		input = in.readLine();
				 }
			} catch (IOException e) {
				println(e.getMessage());
			}
		   }
		 }).start();
	}
	
	public void println(String str){
		out.println(str);
		out.flush();
	}
	
	public void print(String str){
		out.print(str);
		out.flush();
	}
}
