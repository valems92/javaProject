package presenter;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1>Maze3dCommands</h1> This class includes the Maze3d commands and implanted
 * all command according the protocol
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dCommands extends CommonCommandsManager {
	private final static String FILE_NAME = "properties.xml";

	public Maze3dCommands() {
		super();
	}

	@Override
	public void setCommands() {
		commands.put("dir", new DirCommand());

		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display_maze", new DisplayMazeCommand());

		commands.put("solve", new SolveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());

		commands.put("generate_cross_section", new GenerateCrossSectionCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());

		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());

		commands.put("display_message", new DisplayMessageCommand());

		commands.put("load_properties", new LoadPropertiesCommand());
		commands.put("change_settings", new ChangeSettingsCommand());
		commands.put("save_settings", new SaveSettingsCommand());

		commands.put("exit", new ExitCommand());
		commands.put("save_data", new SaveDataCommand());
	}

	/**
	 * <h1>DirCommand</h1> Get a path and display a list of all files in it.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class DirCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			StringBuilder dirResult = new StringBuilder();
			if (args.length > 1) {
				try {
					File path = new File((String) args[1]);
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
		 * <h1>PrintDIRhelp</h1> Get a files array and display each file. If the
		 * file is a direcotry, recursively, display it files too.
		 * 
		 * @param files
		 *            an array with all files in directory
		 * @param tabs
		 *            numbers of tabs to display before file name is displayed
		 * @param dirResult
		 *            the string builder that contains all displayed data
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
	 * <h1>GenerateMazeCommand</h1> Get all data needed to generate a maze and
	 * send it to generateMaze method in model.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class GenerateMazeCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.generateMaze((String[]) args);
		}
	}

	/**
	 * <h1>DisplayMazeCommand</h1> Get a maze name and if exist, send it to
	 * displayMaze or displayExistingMaze (display message before) method in UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplayMazeCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			String name = (String) args[1];
			Maze3d maze = (Maze3d) args[2];

			if (args.length > 3) {
				String msg = "";
				for (int i = 3; i < args.length; i++)
					msg += (String) args[i] + " ";
				ui.displayExistingMaze(maze, name, msg);
			} else {
				ui.displayMaze(maze, name);
			}
		}
	}

	/**
	 * <h1>SolveMazeCommand</h1> Get all data needed to solve a maze and send it
	 * to solveMaze method in model.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class SolveMazeCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.solveMaze((String[]) args);
		}
	}

	/**
	 * <h1>DisplaySolutionCommand</h1> Get the last solution calculated and send
	 * it to displaySolution method in UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplaySolutionCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			String type = (String) args[1];
			Solution<Position> solution = (Solution<Position>) args[2];
			ui.displaySolution(solution, type);
		}
	}

	/**
	 * <h1>SaveMazeCommand</h1> Get the maze name and a file path. Send it to
	 * saveMaze method in model.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class SaveMazeCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.saveMaze((String[]) args);
		}
	}

	/**
	 * <h1>LoadMazeCommand</h1> Get the maze name and a file path. send it to
	 * loadMaze method in model. name
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class LoadMazeCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.loadMaze((String[]) args);
		}
	}

	/**
	 * <h1>GenerateCrossSectionCommand</h1> Get all data needed to generate a
	 * cross section of a 3D maze a maze and send it to displayCrossSection
	 * method in model. section
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class GenerateCrossSectionCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.displayCrossSection(args);
		}
	}

	/**
	 * <h1>DisplayCrossSectionCommand</h1> Get the last cross section generated
	 * and send it to displayCrossSection method in UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplayCrossSectionCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			int[][] crossSection = (int[][]) args[1];
			ui.displayCrossSection(crossSection);
		}
	}

	/**
	 * <h1>DisplayMessageCommand</h1> Get a message (string) from model and
	 * display it in UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class DisplayMessageCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			String msg = "";
			for (int i = 1; i < args.length; i++)
				msg += (String) args[i] + " ";

			ui.displayMessage(msg);
		}
	}

	/**
	 * <h1>LoadPropertiesCommand</h1> Get the proerties file path and send it to
	 * loadGameProperties in model.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class LoadPropertiesCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			String path = (String) args[1];
			model.loadGameProperties(path);
		}
	}

	/**
	 * <h1>ChangeSettingsCommand</h1> Get a string with an object fields values
	 * and send it to generate class method in UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class ChangeSettingsCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			String fieldsValues = "";
			for (int i = 1; i < args.length; i++)
				fieldsValues += (String) args[i] + " ";

			model.generateClass(fieldsValues);
		}
	}

	/**
	 * <h1>SaveSettingsCommand</h1> Get the object generated form model, set the
	 * properties object and save it.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class SaveSettingsCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			Properties.properites = (Properties) model.getGeneratedObject();

			try {
				XMLEncoder encoder;
				encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
				encoder.writeObject(Properties.properites);
				encoder.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <h1>ExitCommand</h1> Exit game. Call exit method in model and UI.
	 * <p>
	 * 
	 * @author Valentina Munoz & Moris Amon
	 */
	class ExitCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.exit();
			ui.exit();
		}
	}

	class SaveDataCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.saveData(args);
		}
	}
}
