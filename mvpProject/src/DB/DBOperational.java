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
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class DBOperational {

	public Object javaObject = null;
	public Connection conn=null;
	
	public DBOperational(){
		String url = "jdbc:mysql://localhost:3306/maze";
		String username = "root";
		String password = "1234";
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//			String url = "jdbc:mysql://localhost:3306/maze";
//			String username = "root";
//			String password = "1234";
//			Connection conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Database connected!");
			

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
			String sql = null;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(o);
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

	public Object getObject(int id) throws Exception {

//		String url = "jdbc:mysql://localhost:3306/maze";
//		String username = "root";
//		String password = "1234";
//		Connection conn = DriverManager.getConnection(url, username, password);
//		System.out.println("Database connected!");

		Object rmObj = null;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
		ResultSet rs = null;
		String sql = null;

		sql = "select * from maze where id="+id;

		ps = conn.prepareStatement(sql);

		rs = ps.executeQuery();

		if (rs.next()) {
			ByteArrayInputStream bais;

			ObjectInputStream ins;

			try {

				bais = new ByteArrayInputStream(rs.getBytes("javaObject"));

				ins = new ObjectInputStream(bais);
				Object object=ins.readObject();

				ins.close();
				
				return object;
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return rmObj;
	}
	
	public void clearDB() throws SQLException{
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
		String sql = null;
		sql = "TRUNCATE maze";
		ps = conn.prepareStatement(sql);
		ps.executeUpdate();
	}
	
}
