package mc.mythofycommands.player.impl;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.player.impl.managers.PlayerTabManager;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class Join implements Listener {

	private PlayerTabManager tab;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent join) {
		join.setJoinMessage("");
		Player player = (Player) join.getPlayer();
		// Check if still startup
		if (MythofyCommands.isStarting()) player.kickPlayer(ChatColor.AQUA + "Server is still starting up!");
		// Variables
		UUID uuid = player.getUniqueId();
		Boolean connected = MythofyCommands.getInstance().SQL.isConnected();
		Rank rank = RankManager.getRank(uuid);
		// Load Rank
		if (connected) {
			MythofyCommands.getInstance().data.createPlayer(player);
		}
		RankManager.setRank(uuid, rank);
		// Load Tab
		this.tab = new PlayerTabManager();
		tab.setHeaderFooter(player);
	}
}
