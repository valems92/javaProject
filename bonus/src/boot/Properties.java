package boot;

/**
 * <h1>Properties</h1>
 * Serializable class. Allows setting and getting of all properties needed.
 * On init program, load an xml file into a Properties object from which we get data program needs. 
 * @author Valentina Munoz & Moris Amon
 *
 */
public class Properties {
	public static Properties properites = new Properties();
	private String viewType;
	private int viewWidth;
	private int viewHeight;
	
	private String generateAlgorithm;
	private String selectCellMethod;
	private String solveAlgorithm;
	private String comparator;
	private String mazeDisplay;
	
	private int numberOfThreads;
	private Boolean MySQL;
	
	private double hintLength;
	private int animationSpeed;
	
	public int getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}

	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}

	public String getSelectCellMethod() {
		return selectCellMethod;
	}

	public void setSelectCellMethod(String selectCellMethod) {
		this.selectCellMethod = selectCellMethod;
	}

	public String getSolveAlgorithm() {
		return solveAlgorithm;
	}

	public void setSolveAlgorithm(String solveAlgorithm) {
		this.solveAlgorithm = solveAlgorithm;
	}

	public String getComparator() {
		return comparator;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}

	public Boolean getMySQL() {
		return MySQL;
	}

	public void setMySQL(Boolean mySQL) {
		MySQL = mySQL;
	}

	public double getHintLength() {
		return hintLength;
	}

	public void setHintLength(double hintLength) {
		this.hintLength = hintLength;
	}

	public String getMazeDisplay() {
		return mazeDisplay;
	}

	public void setMazeDisplay(String mazeDisplay) {
		this.mazeDisplay = mazeDisplay;
	}
	
	
	
}
