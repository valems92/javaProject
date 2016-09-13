package presenter;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.Solution;

/**
 * 
 * <h1>dirCommand</h1> This class includes the Maze3d commands and implanted all
 * command according the protocol *
 * <p>
 *
 * @author Valentina Munoz & Moris Amon
 *
 */
public class Maze3dCommands extends CommonCommandsManager {
	private Maze3dAlgorithmFactory algorithms;

	public Maze3dCommands() {
		super();
		algorithms = new Maze3dAlgorithmFactory();
	}

	@Override
	public void setCommands() {
		// Model commands
		commands.put("dir", new dirCommand());
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("solve", new SolveMazeCommand());
		commands.put("save_maze", new SaveDataCommand());
		commands.put("generate_cross_section", new GenerateCrossSectionCommand());

		commands.put("load_properties", new LoadPropertiesCommand());

		// Ui commands
		commands.put("display_maze", new DisplayMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("display_message", new DisplayMessageCommand());

		commands.put("exit", new ExitCommand());
	}

	class dirCommand implements Command {
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

	class GenerateMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];

			try {
				int z = Integer.parseInt(args[2]);
				int y = Integer.parseInt(args[3]);
				int x = Integer.parseInt(args[4]);

				if (z > 30 || y > 30 || x > 30) {
					ui.displayMessage("The maze dimension is too big. All parameters should be smaller than 30.");
					return;
				}

				Maze3dGenerator mg = algorithms.createGenerateAlgorithm(Properties.properites.getGenerateAlgorithm(),
						Properties.properites.getSelectCellMethod());
				model.generateMaze(name, z, y, x, mg);

			} catch (NumberFormatException e) {
				ui.displayMessage("The maze dimension should be on number format.");
			}
		}
	}

	class DisplayMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];

			Maze3d maze = model.getMazeByName(name);
			ui.displayMaze(maze);
		}
	}

	class SolveMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String mazeName = args[1];

			Searcher<Position> searcher = algorithms.createSeacherAlgorithm(Properties.properites.getSolveAlgorithm(),
					Properties.properites.getComparator());
			model.solveMaze(mazeName, searcher);
		}
	}

	class DisplaySolutionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];

			Solution<Position> solution = model.getSolutionByMazeName(name);
			ui.displaySolution(solution);
		}
	}

	class SaveDataCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String fileName = args[1];

			model.saveData(fileName);
		}
	}

	class LoadPropertiesCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String path = args[1];
			model.loadGameProperties(path);
		}
	}

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

	class DisplayCrossSectionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			int section = Integer.parseInt(args[2]);
			
			int[][] crossSection = model.getCrossSectionByNameBySection(name, section);
			ui.displayCrossSection(crossSection);
		}	
	}
	
	class DisplayMessageCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String msg = "";
			for (int i = 1; i < args.length; i++)
				msg += args[i] + " ";

			ui.displayMessage(msg);
		}
	}

	class ExitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			model.exit();
			ui.exit();
		}
	}
}
