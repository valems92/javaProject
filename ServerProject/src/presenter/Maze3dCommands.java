package presenter;

import Common.Common;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searcher;
import algorithms.search.State;
import server.ClinetHandler;

/**
 * <h1>Maze3dCommands</h1> This class includes the Maze3d commands and implanted
 * all command according the protocol
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class Maze3dCommands extends CommonCommandsManager {
    private ClinetHandler client;
    private Maze3dAlgorithmFactory algorithms;

    // private final static String FILE_NAME = "properties.xml";
    public Maze3dCommands(ClinetHandler client) {
	super();
	this.client = client;
	algorithms = new Maze3dAlgorithmFactory();
    }

    @Override
    public void setCommands() {
	commands.put("generate_maze", new GenerateMazeCommand());
	commands.put("solve", new SolveMazeCommand());
	commands.put("generate_cross_section", new GenerateCrossSectionCommand());
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

		    Maze3dGenerator mg = algorithms.createGenerateAlgorithm("GrowingTree", "Random");
		    model.generateMaze(name, z, y, x, mg);

		    Maze3d maze = model.getMazeByName(name);

		    Object[] data = { "display_maze", name, maze };
		    Common o = new Common(data);
		    client.write(o);

		} catch (NumberFormatException e) {
		    Object[] data = { "display_message", "The maze dimension should be in number format." };
		    Common o = new Common(data);
		    client.write(o);
		    return;
		}
	    } else {
		Object[] data = { "display_message", "Missing parameters." };
		Common o = new Common(data);
		client.write(o);

		return;
	    }
	}
    }

    class SolveMazeCommand implements Command {
	@Override
	public void doCommand(Object[] args) {
		Searcher<Position> searcher = algorithms.createSeacherAlgorithm("BFS",
				"Cost");

		String mazeName = (String) args[1];

		String type = null;
		State<Position> state = null;

		if (args.length > 2) {
			type = (String) args[2];
			String z = (String) args[3];
			String y = (String)args[4];
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
		Common o = new Common(data);
		client.write(o);
	    }
	}
    }
}
