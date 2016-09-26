package presenter;

import CommonData.CommonData;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.State;
import server.ClientHandler;

/**
 * <h1>Maze3dCommands</h1> This class includes the Maze3d commands and implanted
 * all command according the protocol
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dCommands extends CommonCommandsManager {
	private ClientHandler client;
	private Maze3dAlgorithmFactory algorithms;

	public Maze3dCommands(ClientHandler client) {
		super();
		this.client = client;
		algorithms = new Maze3dAlgorithmFactory();
	}

	@Override
	public void setCommands() {
		commands.put("load_data", new LoadDataCommand());
		commands.put("set_properties", new SetPropertiesCommand());

		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("solve", new SolveMazeCommand());
		commands.put("generate_cross_section", new GenerateCrossSectionCommand());

		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());

		commands.put("exit", new ExitCommand());
	}

	class LoadDataCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			model.loadData(args);
		}
	}

	class SetPropertiesCommand implements Command {
		@Override
		public void doCommand(Object[] args) {
			Properties.properites = (Properties) args[1];
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
			String name = (String) args[1];

			int z = 0, y = 0, x = 0;
			if (args.length > 2) {
				try {
					z = Integer.parseInt((String) args[2]);
					y = Integer.parseInt((String) args[3]);
					x = Integer.parseInt((String) args[4]);

					Maze3dGenerator mg = algorithms.createGenerateAlgorithm(
							Properties.properites.getGenerateAlgorithm(), Properties.properites.getSelectCellMethod());
					
					model.generateMaze(name, z, y, x, mg);
				} catch (NumberFormatException e) {
					Object[] data = { "display_message", "The maze dimension should be in number format." };
					CommonData o = new CommonData(data);
					client.write(o);
					return;
				}
			} else {
				Object[] data = { "display_message", "Missing parameters." };
				CommonData o = new CommonData(data);
				client.write(o);

				return;
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
			Searcher<Position> searcher = algorithms.createSeacherAlgorithm(Properties.properites.getSolveAlgorithm(),
					Properties.properites.getComparator());

			String mazeName = (String) args[1];

			String type = null;
			State<Position> state = null;

			if (args.length > 2) {
				type = (String) args[2];
				String z = (String) args[3];
				String y = (String) args[4];
				String x = (String) args[5];

				int iz = Integer.parseInt(z);
				int iy = Integer.parseInt(y);
				int ix = Integer.parseInt(x);

				state = new State<Position>(new Position(iz, iy, ix));
			}

			model.solveMaze(searcher, mazeName, type, state);
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
			if (args.length >= 4) {
				String name = (String) args[1];
				int index = Integer.parseInt((String) args[2]);
				String section = (String) args[3];

				model.displayCrossSection(name, index, section);
			} else {
				Object[] data = { "display_message",
						"Missing parameters. Maze name, Index value and Section name are needed" };
				CommonData o = new CommonData(data);
				client.write(o);
			}
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
			String name = (String) args[1];
			String fileName = (String) args[2];

			model.saveMaze(name, fileName);
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
			String fileName = (String) args[1];
			String name = (String) args[2];

			model.loadMaze(fileName, name);

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
		}
	}
}
