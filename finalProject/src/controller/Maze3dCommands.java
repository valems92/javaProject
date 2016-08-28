package controller;

import java.io.File;

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
			if (args.length > 1) {
				try {
					File path = new File(args[1]);
					File[] files = path.listFiles();
					PrintDIRhelp(files, 0);
				} catch (NullPointerException e) {
					view.println("Path does not exist");
				}
			} else
				view.println("No path was received");
		}

		public void PrintDIRhelp(File[] files, int tabs) {
			for (File file : files) {
				for (int i = 0; i < tabs; i++)
					view.print("\t");
				view.println(file.getName());

				if (!file.isFile())
					PrintDIRhelp(file.listFiles(), tabs + 1);
			}
		}

	}

	class generate_mazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 5) {
				String name = args[1];
				try {
					int z = Integer.parseInt(args[2]);
					int y = Integer.parseInt(args[3]);
					int x = Integer.parseInt(args[4]);
					model.generateMaze(name, z, y, x);
				} catch (NumberFormatException e) {
					view.println("There are at least one improper dimension. Maze name, number of floors, columns and rows are needed");
				}
			} else
				view.println("Missing parameters. Maze name, number of floors, columns and rows are needed");
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
