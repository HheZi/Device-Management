package sqlmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
	
	private static final ConnectionManager CONNECTION_MANAGER = new ConnectionManager();
	
	private static final String url = "db.url";
	private static final String username = "db.name";
	private static final String password = "db.password";
	private static final String driver = "db.driver";
	
	static {
		try {
			Class.forName(PropertiesUtil.get(driver));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionManager getInstance() {
		return CONNECTION_MANAGER;
	}
	
	private ConnectionManager() {}
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(PropertiesUtil.get(url), PropertiesUtil.get(username), PropertiesUtil.get(password));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}