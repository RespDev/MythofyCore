package mc.mythofy.mythofycommands.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import mc.mythofy.mythofycommands.MythofyCommands;

/**
 * 
 * SQL Driver
 * @author Res
 * 
 */

public class SQLDriver {

	private FileConfiguration config = MythofyCommands.getCoreConfig();
	private String host = config.getString("mysql.host");
	private String port = config.getString("mysql.port");
	private String username = config.getString("mysql.username");
	private String password = config.getString("mysql.password");
	private String database = config.getString("mysql.database");

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
				MythofyCommands.logMessage("Core", "Now disconnected from the database");
			} catch (SQLException e) {
				MythofyCommands.logMessage("Core", ChatColor.DARK_RED + "Failed to disconnect from the database");
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
