package algorithms.search;

import java.util.ArrayList;

/**
 * 
 * The Solution class for search algorithm
 * This class include the solution path
 *
 * @param <T>
 */
public class Solution<T> {
	
	private ArrayList<State<T>> results=new ArrayList<State<T>>();

	/**
	 * @return the results
	 */
	public ArrayList<State<T>> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(ArrayList<State<T>> results) {
		this.results = results;
	}


	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(State<T> s : results){
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}
	
	
	

}
