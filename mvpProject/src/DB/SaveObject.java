package DB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SaveObject {

	public Object javaObject = null;

	public Object getJavaObject() {
		return javaObject;
	}

	public void setJavaObject(Object javaObject) {
		this.javaObject = javaObject;
	}

	public void saveObject() throws Exception {
		try {
			String url = "jdbc:mysql://localhost:3306/maze";
			String username = "root";
			String password = "1234";
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
			String sql = null;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(new MyClass());
			oos.flush();
			oos.close();
			bos.close();

			byte[] data = bos.toByteArray();

			sql = "insert into maze (javaObject) values(?)";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, data);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Object getObject() throws Exception {

		String url = "jdbc:mysql://localhost:3306/maze";
		String username = "root";
		String password = "1234";
		Connection conn = DriverManager.getConnection(url, username, password);
		System.out.println("Database connected!");

		Object rmObj = null;
		// Connection conn=null;/// get connection string;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
		ResultSet rs = null;
		String sql = null;

		sql = "select * from maze where id=5";

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		if (rs.next()) {
			ByteArrayInputStream bais;

			ObjectInputStream ins;

			try {

				bais = new ByteArrayInputStream(rs.getBytes("javaObject"));

				ins = new ObjectInputStream(bais);

				MyClass mc = (MyClass) ins.readObject();

				System.out.println("Object in value ::" + mc.getSName());
				ins.close();

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return rmObj;
	}
}
