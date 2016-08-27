package controller;

import java.io.IOException;

public interface Controller {
	
	void executeCommand(String commandLine) throws IOException;

}
