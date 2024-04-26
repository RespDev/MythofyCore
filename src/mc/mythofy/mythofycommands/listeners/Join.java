package mc.mythofy.mythofycommands.listeners;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent join) {
		Player p = (Player) join.getPlayer();
		UUID uuid = p.getUniqueId();
		join.setJoinMessage("");

		// Load Rank
		if (MythofyCommands.getInstance().SQL.isConnected()) {
			MythofyCommands.getInstance().data.createPlayer(p);
		}
		Rank playerRank = RankManager.getRank(uuid);
		RankManager.setRank(uuid, playerRank);

		// Tab Rank & Overhead Nametag
		// TODO code overhead nametag
		/*p.setPlayerListName(RankManager.getRank(p.getUniqueId()).getColor() + "" + ChatColor.BOLD
				+ RankManager.getRank(p.getUniqueId()).getTabName().toUpperCase() + " " + ChatColor.RESET + ""
				+ RankManager.getRank(p.getUniqueId()).getColor() + p.getName());*/
		
		// Header & Footer
		//TODO CODE IT
	}
}
