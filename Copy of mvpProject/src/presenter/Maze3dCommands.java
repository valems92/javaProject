package presenter;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;

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
		//Model commands
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("solve", new SolveMazeCommand());

		//Ui commands
		commands.put("display_maze", new DisplayMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
	}

	class GenerateMazeCommand implements Command {
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

					model.generateMaze(name, z, y, x, mg);

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
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
			if (args.length >= 3) {
				String mazeName = args[1];

				String alg = args[2];
				String arg = (args.length >= 4) ? args[3] : "";
				Searcher<Position> searcher = algorithms.createSeacherAlgorithm(alg, arg);

				model.solveMaze(mazeName, searcher);
			}
		}
	}

	class DisplaySolutionCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			ArrayList<Position> solution = model.getSolutionByMazeName(name);
			ui.displaySolution(solution);
		}
	}
}
