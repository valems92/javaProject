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
		commands.put("display_cross_section", new display_cross_sectionCommand());
		commands.put("MessageCommand", new messageCommand());
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
	
	class display_cross_sectionCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if (args.length >= 4) {
				String name = args[1];
				int index = Integer.parseInt(args[2]);
				String section = args[3];
				model.displayCrossSection(name, index, section);
			} else
				ui.println("Missing parameters. Maze name, Index value and Section name are needed");
		}
		
	}
	
	class messageCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			ui.println(args[1]);
			
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
	
	
	
	class ExitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			model.exit();
			ui.exit();
		}
	}
}
