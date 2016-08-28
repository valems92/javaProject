package controller;

import java.io.IOException;

public interface Controller {
	void print(String str);
	void println(String str);
	void executeCommand(String commandLine) throws IOException;

}
