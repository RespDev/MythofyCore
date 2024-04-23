package mc.mythofy.mythofycommands.listeners;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import mc.mythofy.mythofycommands.rank.RankManager;

public class Quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent quit) {
		Player p = (Player) quit.getPlayer();
		UUID uuid = p.getUniqueId();
		quit.setQuitMessage("");
		RankManager.removePlayer(uuid);
	}
}