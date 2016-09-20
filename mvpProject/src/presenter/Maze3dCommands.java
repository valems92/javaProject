package presenter;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * <h1>Maze3dCommands</h1> 
 * This class includes the Maze3d commands and implanted all command according the protocol
 * <p>
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dCommands extends CommonCommandsManager {
	private Maze3dAlgorithmFactory algorithms;

	public Maze3dCommands() {
		super();
		algorithms = new Maze3dAlgorithmFactory();
	}

	@Override
	public void setCommands() {
		commands.put("dir", new DirCommand());
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("solve", new SolveMazeCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("generate_cross_section", new GenerateCrossSectionCommand());
		commands.put("load_properties", new LoadPropertiesCommand());
		commands.put("display_maze", new DisplayMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("display_message", new DisplayMessageCommand());
		commands.put("exit", new ExitCommand());
	}

	/**
	 * <h1>DirCommand</h1> 
	 * Get a path and display a list of all files in it.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class DirCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			StringBuilder dirResult = new StringBuilder();
			if (args.length > 1) {
				try {
					File path = new File(args[1]);
					File[] files = path.listFiles();

					PrintDIRhelp(files, 0, dirResult);
					ui.displayMessage(dirResult.toString());
				} catch (NullPointerException e) {
					ui.displayMessage("Path does not exist");
				}
			} else
				ui.displayMessage("No path was received");
		}

		/**
		 * <h1>PrintDIRhelp</h1>
		 * Get a files array and display each file. If the file is a direcotry, recursively, display it files too.
		 * @param files an array with all files in directory
		 * @param tabs numbers of tabs to display before file name is displayed
		 * @param dirResult the string builder that contains all displayed data
		 */
		private void PrintDIRhelp(File[] files, int tabs, StringBuilder dirResult) {
			for (File file : files) {
				for (int i = 0; i < tabs; i++)
					dirResult.append("\t");
				dirResult.append(file.getName() + "\n");

				if (!file.isFile())
					PrintDIRhelp(file.listFiles(), tabs + 1, dirResult);
			}
		}
	}

	/**
	 * <h1>GenerateMazeCommand</h1> 
	 * Get all data needed to generate a maze and send it to generateMaze method in model.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class GenerateMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];

			int z = 0, y = 0, x = 0;
			if (args.length > 2) {
				try {
					z = Integer.parseInt(args[2]);
					y = Integer.parseInt(args[3]);
					x = Integer.parseInt(args[4]);
					
					Maze3dGenerator mg = algorithms.createGenerateAlgorithm(Properties.properites.getGenerateAlgorithm(),
							Properties.properites.getSelectCellMethod());
					model.generateMaze(name, z, y, x, mg);
					
				} catch (NumberFormatException e) {
					ui.displayMessage("The maze dimension should be in number format.");
				}
			} else 
				ui.displayMessage("Missing parameters.");
		}
	}

	/**
	 * <h1>DisplayMazeCommand</h1> 
	 * Get a maze name and if exist, send it to displayMaze or displayExistingMaze (display message before) method in UI.
	 * <p> 
	 * @author Valentina Munoz & Moris Amon    
	 */
	class DisplayMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];

			Maze3d maze = model.getMazeByName(name);
			if (args.length > 2) {
				String msg = "";
				for (int i = 2; i < args.length; i++)
					msg += args[i] + " ";
				ui.displayExistingMaze(maze, name, msg);
			} else {
				ui.displayMaze(maze, name);
			}
		}
	}

	/**
	 * <h1>SolveMazeCommand</h1> 
	 * Get all data needed to solve a maze and send it to solveMaze method in model.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class SolveMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			Searcher<Position> searcher = algorithms.createSeacherAlgorithm(Properties.properites.getSolveAlgorithm(),
					Properties.properites.getComparator());

			String mazeName = args[1];

			String type = null;
			State<Position> state = null;

			if (args.length > 2) {
				type = args[2];
				String z = args[3];
				String y = args[4];
				String x = args[5];

				int iz = Integer.parseInt(z);
				int iy = Integer.parseInt(y);
				int ix = Integer.parseInt(x);

				state = new State<Position>(new Position(iz, iy, ix));
			}

			model.solveMaze(searcher, mazeName, type, state);
		}
	}

	/**
	 * <h1>DisplaySolutionCommand</h1> 
	 * Get the last solution calculated and send it to displaySolution method in UI.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplaySolutionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String type = args[1];

			Solution<Position> solution = model.getLastSolution();
			ui.displaySolution(solution, type);
		}
	}

	/**
	 * <h1>SaveMazeCommand</h1> 
	 * Get the maze name and a file path. Send it to saveMaze method in model.
	 * <p> 
	 * @author Valentina Munoz & Moris Amon
	 */
	class SaveMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			String fileName = args[2];

			model.saveMaze(name, fileName);
		}
	}

	/**
	 * <h1>LoadMazeCommand</h1> 
	 * Get the maze name and a file path. send it to loadMaze method in model.
	 * name
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class LoadMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String fileName = args[1];
			String name = args[2];

			model.loadMaze(fileName, name);
		}
	}

	/**
	 * <h1>LoadPropertiesCommand</h1> 
	 * Get the proerties file path and send it to loadGameProperties in model.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class LoadPropertiesCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String path = args[1];
			model.loadGameProperties(path);
		}
	}

	/**
	 * <h1>GenerateCrossSectionCommand</h1> 
	 * Get all data needed to generate a cross section of a 3D maze a maze and send it to displayCrossSection method in model.
	 * section
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class GenerateCrossSectionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			if (args.length >= 4) {
				String name = args[1];
				int index = Integer.parseInt(args[2]);
				String section = args[3];

				model.displayCrossSection(name, index, section);
			} else
				ui.displayMessage("Missing parameters. Maze name, Index value and Section name are needed");
		}
	}

	/**
	 * <h1>DisplayCrossSectionCommand</h1> 
	 * Get the last cross section generated and send it to displayCrossSection method in UI.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplayCrossSectionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			int[][] crossSection = model.getLastCrossSection();
			ui.displayCrossSection(crossSection);
		}
	}

	/**
	 * <h1>DisplayMessageCommand</h1> 
	 * Get a message (string) from model and display it in UI.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplayMessageCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String msg = "";
			for (int i = 1; i < args.length; i++)
				msg += args[i] + " ";

			ui.displayMessage(msg);
		}
	}

	/**
	 * <h1>ExitCommand</h1> 
	 * Exit game. Call exit method in model and UI.
	 * <p>
	 * @author Valentina Munoz & Moris Amon
	 */
	class ExitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			model.exit();
			ui.exit();
		}
	}
}
