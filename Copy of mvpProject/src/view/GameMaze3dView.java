package view;

import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import presenter.Presenter;

public class GameMaze3dView extends Observable implements View {

	private Presenter presenter;
	private ExecutorService executorInput;
	
	/**
	 * @return the presenter
	 */
	public Presenter getPresenter() {
		return presenter;
		//executorInput = ExecutorService.newSingleThreadExecutor();
	}

	/**
	 * @param presenter the presenter to set
	 */
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void DisplayMaze(String currCMD) {
		System.out.println(currCMD);
	}

	@Override
	public void start() {
		
	
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please insert the generate command to test: ");
		String commandLine = scanner.nextLine();
		setChanged();
		notifyObservers(commandLine);
				
	
	}
		
		
	

	
}
