package mc.mythofy.mythofycommands.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mc.mythofy.mythofycommands.MythofyCommands;

public class Storage {

	private static Map<UUID, Integer> Gems = new HashMap<UUID, Integer>();

	public static Integer getGems(UUID uuid) {
		if (MythofyCommands.getInstance().SQL.isConnected()) {
			if (Gems.get(uuid) == null) {
				Integer gems = MythofyCommands.getInstance().data.getUserGems(uuid);
				return gems;
			}
			return Gems.get(uuid);
		}
		return Gems.get(uuid);
	}

	public static void removeGems(UUID uuid) {
		if (Gems.get(uuid) != null)
			Gems.remove(uuid);
	}
}