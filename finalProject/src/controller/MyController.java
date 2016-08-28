package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import model.Model;
import view.View;

public class MyController implements Controller {

	private Model model;
	private View view;
	private CommandsManager commandsManager;

	public MyController(CommandsManager commandsManager){
		this.commandsManager = commandsManager;
	}

	public void setModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;
		commandsManager.setModelView(model, view);
	}
	
	public void setViewCLI(BufferedReader in, PrintWriter out) {
		view.setCLI(in, out, this.commandsManager);
	}

	@Override
	public void executeCommand(String commandLine) throws IOException{
		commandsManager.executeCommand(commandLine);
	}

	@Override
	public void print(String str) {
		view.print(str);
	}

	@Override
	public void println(String str) {
		view.println(str);
	}
}
