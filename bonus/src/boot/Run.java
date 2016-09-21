package boot;

import Bonus.Form;

public class Run {

	public static void main(String[] args) {
		myClass myclass = new myClass();
		new Form(myclass.getClass());
	}
}
