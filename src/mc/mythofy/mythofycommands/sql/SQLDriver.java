package mc.mythofy.mythofycommands.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDriver {

	private String host = "na03-sql.pebblehost.com";
	private String port = "3306";
	private String username = "customer_696722_icecap";
	private String password = "Mocn5jGzsoCVHqu~@rFc";
	private String database = "customer_696722_icecap";

	private Connection connection;

	public void connect() throws ClassNotFoundException, SQLException {
		if (!isConnected()) {
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=true&autoReconnect=true", username, password);
		}
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		return (connection == null ? false : true);
	}

	public Connection getConnection() {
		return connection;
	}
}
