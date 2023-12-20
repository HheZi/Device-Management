package sqlmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String url = "db.url";
	private static String username = "db.name";
	private static String password = "db.password";
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(PropertiesUtil.get(url), PropertiesUtil.get(username), PropertiesUtil.get(password));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}