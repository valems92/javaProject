package DB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <h1>DBOperational</h1>
 * <p>
 *  @author Valentina Munoz & Moris Amon
 */
public class DBOperational {
	public Object javaObject = null;
	public Connection conn = null;

	public DBOperational() {
		String url = "jdbc:mysql://localhost:3306/maze";
		String username = "root";
		String password = "1234";
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("There was a problem trying to coonect to Database");
		}
		System.out.println("Database connected!");
	}

	public Object getJavaObject() {
		return javaObject;
	}

	public void setJavaObject(Object javaObject) {
		this.javaObject = javaObject;
	}

	public void saveObject(Object o) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(o);
			oos.flush();
			oos.close();
			bos.close();

			byte[] data = bos.toByteArray();

			ps = conn.prepareStatement("insert into maze (javaObject) values(?)");
			ps.setObject(1, data);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getObject(int id) throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
		ResultSet rs = null;

		ps = conn.prepareStatement("select * from maze where id=" + id);
		rs = ps.executeQuery();

		if (rs.next()) {
			ByteArrayInputStream bais;
			ObjectInputStream ins;
			try {
				bais = new ByteArrayInputStream(rs.getBytes("javaObject"));
				ins = new ObjectInputStream(bais);
				Object object = ins.readObject();

				ins.close();
				return object;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void clearDB() throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
		ps = conn.prepareStatement("TRUNCATE maze");
		ps.executeUpdate();
	}

}
