package DB;

public class TestRun {
	public static void main(String[] args){
	MyClass mc=new MyClass();
	mc.setSName("This is setting object");

	DBOperational so=new DBOperational();

	so.setJavaObject(mc);
	try {
	//	so.saveObject();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		DBOperational so2=new DBOperational();
	    try {
			so2.getObject(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

