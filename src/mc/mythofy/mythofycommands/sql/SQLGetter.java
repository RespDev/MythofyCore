package mc.mythofy.mythofycommands.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;

public class SQLGetter {

	private MythofyCommands plugin;

	public SQLGetter(MythofyCommands plugin) {
		this.plugin = plugin;
	}

	public void createTable() {
		PreparedStatement statement;
		try {
			statement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS profile "
					+ "(USERNAME VARCHAR(100),UUID VARCHAR(100),RANK VARCHAR(100),GEMS INT(100),PRIMARY KEY (UUID))");

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createPlayer(Player p) {
		try {
			UUID uuid = p.getUniqueId();
			if (!doesExist(uuid)) {
				PreparedStatement statement2 = plugin.SQL.getConnection()
						.prepareStatement("INSERT IGNORE profile " + "(UUID,USERNAME,RANK,MONEY) VALUES (?,?,?,?)");
				statement2.setString(1, uuid.toString());
				statement2.setString(2, p.getName());
				statement2.setString(3, "MEMBER");
				statement2.setInt(4, 0);
				statement2.executeUpdate();

				return;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean doesExist(UUID uuid) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection()
					.prepareStatement("SELECT * FROM profile WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			if (results.next())
				return true;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setUserRank(UUID uuid, Rank rank) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection()
					.prepareStatement("UPDATE profile SET RANK=? WHERE UUID=?");
			statement.setString(1, rank.name());
			statement.setString(2, uuid.toString());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Rank getUserRank(UUID uuid) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection()
					.prepareStatement("SELECT RANK FROM profile WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();
			String rankString;

			if (results.next()) {
				rankString = results.getString("RANK");
				return Rank.valueOf(rankString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Rank.MEMBER;
	}

	public Integer getUserGems(UUID uuid) {
		try {
			PreparedStatement statement = plugin.SQL.getConnection()
					.prepareStatement("SELECT GEMS FROM profile WHERE UUID=?");
			statement.setString(1, uuid.toString());
			ResultSet results = statement.executeQuery();

			if (results.next()) {
				Integer gems = results.getInt("GEMS");
				if (gems != null)
					return gems;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
