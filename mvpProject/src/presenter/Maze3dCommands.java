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
		// Model commands
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("solve", new SolveMazeCommand());
		commands.put("save_maze", new SaveDataCommand());
		commands.put("load_properties", new LoadPropertiesCommand());
		
		// Ui commands
		commands.put("display_maze", new DisplayMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		
		commands.put("exit", new ExitCommand());
	}

	class GenerateMazeCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			
			try {
				int z = Integer.parseInt(args[2]);
				int y = Integer.parseInt(args[3]);
				int x = Integer.parseInt(args[4]);

				Maze3dGenerator mg = algorithms.createGenerateAlgorithm(Properties.properites.getGenerateAlgorithm(),
						Properties.properites.getSelectCellMethod());
				model.generateMaze(name, z, y, x, mg);

			} catch (NumberFormatException e) {
				e.printStackTrace();
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
			
			ArrayList<Position> solution = model.getSolutionByMazeName(name);
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
	
	class ExitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			model.exit();
			ui.exit();
		}
	}
}
