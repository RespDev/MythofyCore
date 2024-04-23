package mc.mythofy.mythofycommands.rank;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mc.mythofy.mythofycommands.MythofyCommands;

public class RankManager {

	private static Map<UUID, Rank> rankManager = new HashMap<UUID, Rank>();

	public static Rank getRank(UUID uuid) {
		if (MythofyCommands.getInstance().SQL.isConnected()) {
			if (rankManager.get(uuid) == null) {
				Rank rank = MythofyCommands.getInstance().data.getUserRank(uuid);
				return rank;
			}
			return rankManager.get(uuid);
		}
		return rankManager.get(uuid);
	}

	public static void setRank(UUID uuid, Rank rank) {
		if (MythofyCommands.getInstance().SQL.isConnected()) {
			MythofyCommands.getInstance().data.setUserRank(uuid, rank);
			rankManager.put(uuid, rank);
		}
		rankManager.put(uuid, rank);
	}

	public static void removePlayer(UUID uuid) {
		if (rankManager.get(uuid) != null)
			rankManager.remove(uuid);
	}
}