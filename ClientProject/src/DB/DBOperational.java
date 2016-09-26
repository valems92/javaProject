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
 * <h1>DBOperational</h1> This class allows connection between a Java code and
 * mySQL database type In addition, this class allows to save and read data from
 * the database by saveObject and getObject Also, there is an option to delete
 * the all database records
 * <p>
 * 
 * @author Valentina Munoz & Moris Amon
 */
public class DBOperational {
    public Object javaObject = null;
    public Connection conn = null;

    /**
     * <h1>DBOperational</h1> The constructor makes the connection to the server
     * <p>
     */
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

    /**
     * <h1>saveObject</h1> This method is responsible to save any object to the
     * database
     * <p>
     * 
     * @param Object
     *            o - An object which is required to save in the database
     * @throws Exception
     */
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

    /**
     * <h1>getObject</h1> This method is responsible to get object by id from
     * the database
     * <p>
     * 
     * @param id
     *            - Record number which is required to pull from the database
     * @return Object o - The saved object in the database by relevant id
     * @throws Exception
     */
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

    /**
     * <h1>getObject</h1> This method is responsible to clear all record from
     * the database
     * <p>
     * 
     * @throws SQLException
     */
    public void clearDB() throws SQLException {
	PreparedStatement ps = conn.prepareStatement("SELECT * FROM maze");
	ps = conn.prepareStatement("TRUNCATE maze");
	ps.executeUpdate();
    }

}
