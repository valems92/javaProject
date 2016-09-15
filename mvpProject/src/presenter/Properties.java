package presenter;

public class Properties {
	public static Properties properites = new Properties();
	private String viewType;
	private int viewWidth;
	private int viewHeight;
	private double hintLen;
	
	private String generateAlgorithm;
	private String selectCellMethod;
	private String solveAlgorithm;
	private String comparator;
	
	private int numberOfThreads;
	private Boolean MySQL;
	
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

	/**
	 * @return the mySQL
	 */
	public Boolean getMySQL() {
		return MySQL;
	}

	/**
	 * @param mySQL the mySQL to set
	 */
	public void setMySQL(Boolean mySQL) {
		MySQL = mySQL;
	}

	/**
	 * @return the hintLen
	 */
	public double getHintLen() {
		return hintLen;
	}

	/**
	 * @param hintLen the hintLen to set
	 */
	public void setHintLen(double hintLen) {
		this.hintLen = hintLen;
	}
	
	
	
}
