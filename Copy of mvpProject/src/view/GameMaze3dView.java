package view;

import java.util.Observable;
import java.util.Scanner;

import presenter.Presenter;

public class GameMaze3dView extends Observable implements View {

	private Presenter presenter;
	
	/**
	 * @return the presenter
	 */
	public Presenter getPresenter() {
		return presenter;
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
	public void run() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please insert the generate command to test: ");
		String commandLine = scanner.nextLine();
		setChanged();
		notifyObservers(commandLine);
		
	}

	
}
