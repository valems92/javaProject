package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * The Solution class for search algorithm
 * This class include the solution path
 *
 * @param <T>
 */
public class Solution<T> implements Serializable {
	
	protected ArrayList<T> results=new ArrayList<T>();

	/**
	 * @return the results
	 */
	public ArrayList<T> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(ArrayList<T> results) {
		this.results = results;
	}


	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for(T s : results){
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}
	
	
	

}
