package mc.mythofycommands.player.impl;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.RankManager;

public class Quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent quit) {
		quit.setQuitMessage("");
		Player player = (Player) quit.getPlayer();
		UUID uuid = player.getUniqueId();
		RankManager.removePlayer(uuid);
	}
}