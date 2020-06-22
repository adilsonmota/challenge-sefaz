package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	
	private static final String DRIVER_CLASS = "org.postgresql.Driver";
	private static Connection connection = null;
	private static String user = "tsofrfptwhkxsd";
	private static String password = "58d81749ac6f14bba9f601089d700133ca16ad431f26abc40f53d7cbdb720468";
	private static String host="ec2-18-232-143-90.compute-1.amazonaws.com";
	private static String database="d56ooj6au7ltsf";
	
	private static final String URL = "jdbc:postgresql://" + host + ":5432/" + database + "?sslmode=require";
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName(DRIVER_CLASS);
				connection = DriverManager.getConnection(URL, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return connection;
	}
}
