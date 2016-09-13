package DB;

public class TestRun {
	public static void main(String[] args){
	MyClass mc=new MyClass();
	mc.setSName("This is setting object");

	SaveObject so=new SaveObject();

	so.setJavaObject(mc);
	try {
		so.saveObject();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	    SaveObject so2=new SaveObject();
	    try {
			so2.getObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

