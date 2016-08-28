package view;

import java.io.BufferedReader;
import java.io.PrintWriter;

import controller.CommandsManager;
import controller.Controller;

public class MyView implements View {

	private Controller controller;
	private CLI cli;

	public MyView(Controller controller) {
		this.controller = controller;
		cli = null;
	}

	public void setCLI(BufferedReader in, PrintWriter out, CommandsManager cmd) {
		cli = new CLI(in, out, cmd);
	}

	@Override
	public void start() {
		cli.start();
	}
	
	@Override
	public void print(String str){
		cli.print(str);
	}
	
	@Override
	public void println(String str){
		cli.println(str);
	}
}
