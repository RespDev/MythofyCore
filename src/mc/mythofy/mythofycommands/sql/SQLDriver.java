package mc.mythofy.mythofycommands.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import mc.mythofy.mythofycommands.MythofyCommands;

public class SQLDriver {

	private FileConfiguration config = MythofyCommands.getInstance().getConfig();
	private String host = config.getString("host");
	private String port = config.getString("port");
	private String username = config.getString("username");
	private String password = config.getString("password");
	private String database = config.getString("database");

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
