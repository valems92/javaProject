package controller;

import java.io.File;

import model.Model;
import view.View;

public class Maze3dCommands extends CommonCommandsManager {

	@Override
	public void setCommands() {
		commands.put("dir", new dirCommand());
		commands.put("generate_maze", new generate_mazeCommand());
		commands.put("display", new displayCommand());
		commands.put("display_cross_section", new display_cross_sectionCommand());
		commands.put("save_maze", new save_mazeCommand());
		commands.put("load_maze", new load_mazeCommand());
		commands.put("solve", new solveCommand());
		commands.put("display_solution", new display_solutionCommand());
		commands.put("exit", new exitCommand());

	}

	class dirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			File path=new File(args[1]);
			File[] files=path.listFiles();
			PrintDIRhelp(files);
		}
		
		public void PrintDIRhelp(File[] files){
			for (File file : files) {
				if(!file.isFile()){
					PrintDIRhelp(file.listFiles());
					view.print(file.getName());
				}
				else
				{
					view.print(file.getName());	
				}
			}
		}

	}
	
	
	class generate_mazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			
			String zs=args[2];
			String ys=args[3];
			String xs=args[4];
			
			int z = Integer.parseInt(zs);
			int y = Integer.parseInt(ys);
			int x = Integer.parseInt(xs);

		}
	}

	class displayCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class display_cross_sectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class save_mazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class load_mazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class solveCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class display_solutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}

	class exitCommand implements Command {

		@Override
		public void doCommand(String[] args) {

		}
	}



}
