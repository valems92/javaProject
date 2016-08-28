package model;

public interface Model {
	public void generateMaze(String name, int z, int y, int x);  // ALGORITHM
	public void displayMazeByName(String name);
	public void displayCrossSection(String name, int index, String section);
	public void saveMazeByName(String name, String fileName);
	public void loadMaze(String fileName, String name);
	public void solveMaze(String name, String algorithmClassName, String comperatorClassName); // ALGORITHM
	public void displaySolutionByName(String name); 
}
