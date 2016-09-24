package presenter;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * <h1>Maze3dCommands</h1> This class includes the Maze3d commands and implanted
 * all command according the protocol
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dCommands extends CommonCommandsManager {
	private Maze3dAlgorithmFactory algorithms;
	private final static String FILE_NAME = "properties.xml";
	private ObjectOutputStream outToClient;

	public Maze3dCommands(OutputStream outToClient) {
		super();
		algorithms = new Maze3dAlgorithmFactory();
		try {
			this.outToClient=new ObjectOutputStream(outToClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setCommands() {
		commands.put("generate_maze", new GenerateMazeCommand());
		//commands.put("solve", new SolveMazeCommand());
		//commands.put("generate_cross_section", new GenerateCrossSectionCommand());
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
		public void doCommand(String[] args) {
			String name = args[1];

			int z = 0, y = 0, x = 0;
			if (args.length > 2) {
				try {
					z = Integer.parseInt(args[2]);
					y = Integer.parseInt(args[3]);
					x = Integer.parseInt(args[4]);

					Maze3dGenerator mg = algorithms.createGenerateAlgorithm(
							"GrowingTree", "Random");
					model.generateMaze(name, z, y, x, mg);
					Maze3d maze=model.getMazeByName(name);
					outToClient.writeObject(maze);

				} catch (NumberFormatException e) {
					//ui.displayMessage("The maze dimension should be in number format.");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				//ui.displayMessage("Missing parameters.");
				return;
		}

	}


}
