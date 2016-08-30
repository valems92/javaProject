package controller;

import java.io.File;
import java.util.ArrayList;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Searcher;

public class Maze3dCommands extends CommonCommandsManager {
	private Maze3dAlgorithmFactory algorithms;
	
	public Maze3dCommands() {
		super();
		algorithms = new Maze3dAlgorithmFactory();
	}
	
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
	
	/**
	 * 
	 * <h1>dirCommand</h1> Command for write the dir path list for specific file
	 * to Out Source.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
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

	/**
	 * 
	 * <h1>generate_mazeCommand</h1> Command for generate the maze according to specific generator algorithm
	 * (such as: GrowingTreeGenerator, SimpleMaze3dGenerator).
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
	
	class generate_mazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 6) {
				String name = args[1];
				try {
					int z = Integer.parseInt(args[2]);
					int y = Integer.parseInt(args[3]);
					int x = Integer.parseInt(args[4]);
					
					String alg = args[5];
					String arg = (args.length >= 7) ? args[6] : "";
					Maze3dGenerator mg = algorithms.createGenerateAlgorithm(alg, arg);
					
					if(mg != null)
						model.generateMaze(name, z, y, x, mg);
					else
						view.println("Wrong algorithm");
				} catch (NumberFormatException e) {
					view.println("There are at least one improper dimension. Maze name, number of floors, columns and rows are needed");
				}
			} else
				view.println("Missing parameters. Maze name, number of floors, columns and rows and algorithm are needed");
		}
	}

	/**
	 * 
	 * <h1>displayCommand</h1> Command for display a maze by the name.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
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

	/**
	 * 
	 * <h1>display_cross_sectionCommand</h1> Command for display a maze by cross section
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
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

	/**
	 * 
	 * <h1>save_mazeCommand</h1> Command for save a maze with a name to file
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
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

	/**
	 * 
	 * <h1>load_mazeCommand</h1> Command for load a maze from file with a new name
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 * 
	 */
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

	/**
	 * 
	 * <h1>solveCommand</h1> Command for solve a maze according by any search algorithm
	 * (such as: BestFirstSearch, DepthFirstSearch).
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class solveCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 3) {
				String mazeName = args[1];
				
				String alg = args[2];
				String arg = (args.length >= 4) ? args[3] : "";
				Searcher<Position> searcher = algorithms.createSeacherAlgorithm(alg, arg);
				
				if(searcher != null)
					model.solveMaze(mazeName, searcher);
				else
					view.println("Wrong algorithm");
			} else
				view.println("Missing parameters. Maze name and searcher algorithm are needed");
		}
	}

	/**
	 * 
	 * <h1>display_solutionCommand</h1> Command for display maze solve according the maze's name
	 * (such as: BestFirstSearch, DepthFirstSearch).
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
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

	/**
	 * 
	 * <h1>display_solutionCommand</h1> Command for exit from the game.
	 * 
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class exitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if(args.length==1){
				view.exit();
			}
			else
				view.println("Invalid command");
		}
	}

}
