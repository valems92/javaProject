package view;

import java.io.BufferedReader;
import java.io.PrintWriter;

import controller.CommandsManager;

public interface View {
	public void setCLI(BufferedReader in, PrintWriter out, CommandsManager cmd);
	public void start();

}
