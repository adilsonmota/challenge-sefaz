package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	
	private static final String DRIVER_CLASS = "org.hsqldb.jdbcDriver";
	private static Connection connection = null;
	private static String user = "sa";
	private static String password = "sa";
	private static String PathBase = "\\chalenge-sefaz\\dataBase";
	private static final String URL = "jdbc:hsqldb:file:" + PathBase;
	
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
