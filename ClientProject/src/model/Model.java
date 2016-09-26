package model;

/**
 * <h1>Model</h1> Responsible for all the maze3D logic.
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public interface Model {
    /**
     * <h1>generateMaze</h1> Generate a 3d maze in a thread phool, according to
     * arguments received. When finish, it throws a notification to the
     * presenter to display it.
     * <p>
     * 
     * @param name
     *            The maze name
     * @param z
     *            Total floors of maze
     * @param y
     *            Total rows of maze
     * @param x
     *            Total columns of maze
     * @param mg
     *            The generate algorithm class
     */
    public void generateMaze(String[] args);

    /**
     * <h1>solveMaze</h1> Solve maze wanted according to the type ("hint" or
     * "solve") and algorithm received. If state is not null, it change the
     * initial position of the maze to it, so it calculte the solution from that
     * position. When finish, it throws a notification to the presenter to
     * display it.
     * <p>
     * 
     * @param searcher
     *            The searcher algorithm class
     * @param name
     *            The maze name
     * @param type
     *            solution wanted. "hint" for half solution and back or "solve"
     *            for entaire solution
     * @param state
     *            a state from which the solution should be calculated
     */
    public void solveMaze(String[] args);

    /**
     * <h1>displayCrossSection</h1> Get a maze name, and if exist, get a 2D
     * array of the cross section wanted. When finish, it throws a notification
     * to the presenter to display it.
     * <p>
     * 
     * @param name
     *            The maze name
     * @param index
     *            The index of the wanted section
     * @param section
     *            The wanted section
     */
    public void displayCrossSection(Object[] data);

    /**
     * <h1>saveMaze</h1> Get a maze name, and if exist, save it compressed to a
     * file.
     * <p>
     * 
     * @param name
     *            The maze name
     * @param fileName
     *            The name of the new file
     */
    public void saveMaze(String[] args);

    /**
     * <h1>loadMaze</h1> Get a file name, and if exist, read it, decompress it
     * and create a maze with the received name.
     * <p>
     * 
     * @param fileName
     *            The file wanted to load
     * @param name
     *            The name of the new maze
     */
    public void loadMaze(String[] args);

    /**
     * <h1>loadGameProperties</h1> Get the path of the properties file, load it
     * and set the file in Properties class.
     * <p>
     * 
     * @param path
     *            The properties file path
     */
    public void loadGameProperties(String path);

    public void saveData(Object[] data);

    /**
     * <h1>loadData</h1> Load user data from SQL or zip file. If zip was
     * required (in properties file) and there wasn't any last data, it create a
     * new empty file.
     * <p>
     * 
     * @throws Exception
     *             throws an exaption if the MySQL property value is invalid
     */
    public void loadData() throws Exception;

    /**
     * <h1>generateClass</h1> Get a class type and generate an object of it. It
     * has to receive a string that contains all class fields value separated by
     * space.
     * <p>
     * 
     * @param fieldsValues
     *            string that contains fields value
     */
    public void generateClass(String fieldsValues);

    /**
     * <h1>update</h1> Get massage and send a notification of it.
     * <p>
     * 
     * @param createFieldLabel
     *            massage of notification
     */
    public void update(String str);

    /**
     * <h1>setGeneratedObject</h1> Set the last properties object generated to
     * the object received
     * <p>
     * 
     * @param o
     *            new object
     */
    public void setGeneratedObject(Object o);

    /**
     * <h1>getGeneratedObject</h1>
     * <p>
     * 
     * @return The last object class generated
     */
    public Object getGeneratedObject();

    /**
     * <h1>exit</h1> Shutdown all executors. i.e, executors won't accept new
     * task. Also waits until all the task that have already been submitted
     * finish what they are doing (or until the timeout is reached = which
     * problably won't happen)
     */
    public void exit();

	public void saveSettingToServer();
}
