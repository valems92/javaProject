package boot;

import java.io.IOException;

import algorithms.demo.Demo;

public class Run {

	public static void main(String[] args) {
		Demo demo=new Demo();
		try {
			demo.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
