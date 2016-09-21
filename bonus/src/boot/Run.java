package boot;

import Bonus.Form;

public class Run {

	public static void main(String[] args) {
		//myClass myclass = new myClass();
		Properties myclass = new Properties();
		new Form(myclass.getClass());
	}
}
