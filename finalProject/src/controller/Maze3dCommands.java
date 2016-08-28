package controller;

import java.io.File;
import java.util.ArrayList;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;

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
			if (args.length > 1) {
				String name = args[1];
				model.displayMazeByName(name);
			} else
				view.println("No maze name was received");
		}
	}

	class display_cross_sectionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if(args.length >= 4){
			String name=args[1];
			int index=Integer.parseInt(args[2]);
			String section=args[3];
			model.displayCrossSection(name,index,section);
			}
			else
				view.println("Missing parameters. Maze name, Index value and Section name are needed");

		}
	}

	class save_mazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 3) {
				String mazeName = args[1];
				String fileName = args[2];
				model.saveMazeByName(mazeName, fileName);
			} else
				view.println("Missing parameters. Maze name and file name are needed");
		}
	}

	class load_mazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 3) {
				String fileName = args[1];
				String mazeName = args[2];
				model.loadMaze(fileName, mazeName);
			} else
				view.println("Missing parameters. File name and maze name are needed");
		}
	}

	class solveCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 3) {
				String mazeName = args[1];
				String algorithm = args[2];
				
				String comperator = null;
				if(args.length >= 4)
					 comperator = args[3];
				
				model.solveMaze(mazeName, algorithm, comperator);
			} else
				view.println("Missing parameters. Maze name and searcher algorithm are needed");
		}
	}

	class display_solutionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length > 1) {
				String name = args[1];
				model.displaySolutionByName(name);
			} else
				view.println("No maze name was received");
		}
	}

	class exitCommand implements Command {
		@Override
		public void doCommand(String[] args) {

		}
	}

}
