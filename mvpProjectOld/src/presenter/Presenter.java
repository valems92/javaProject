package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {
	private View ui;
	private Model model;
	
	public Presenter(View ui, Model model) {
		this.ui = ui;
		this.model = model;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
