package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import controller.Maze3dCommands;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) throws Exception{
		MyController controller = new MyController(new Maze3dCommands());
		Model model = new MyModel(controller);
		View view = new MyView(controller);
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		controller.setModelAndView(model, view);
		controller.setViewCLI(in, out);
		
		view.println("Please start entering commands:");
		view.start();
	}

}
