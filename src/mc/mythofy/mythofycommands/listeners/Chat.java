package mc.mythofy.mythofycommands.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;
import me.clip.placeholderapi.PlaceholderAPI;

public class Chat implements Listener {
	
	// Chat Manager
	@EventHandler
	public void onChat(AsyncPlayerChatEvent chat) {
		chat.setCancelled(true);

		Player p = (Player) chat.getPlayer();
		Integer rankId = RankManager.getRank(p.getUniqueId()).getRankId();
		String message = chat.getMessage();
		String name = p.getName();

		// TODO Code Anti-Swear System

		chat.setFormat(getFormat(p, rankId, name, message));

		// Chat Mute
		if (MythofyCommands.chatMuted == true) {
			if (rankId >= Rank.TRAINEE.getRankId()) {
				chat.setCancelled(false);
				return;
			}
			chat.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Chat is currently muted.");
			return;
		}
		chat.setCancelled(false);
	}

	// Chat Format
	public String getFormat(Player p, Integer rankId, String name, String message) {
		if (!(rankId == 0))
			return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%"))/*Rank.fromId(rankId).getColor() + "[" + Rank.fromId(rankId).getTabName().toUpperCase()
					+ "] "*/ + name + ChatColor.WHITE + ": "
					+ message;
		return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, "%luckperms_prefix%")) + ChatColor.GRAY + name
				+ ": " + message;
	} 
	
	private String format(Integer rankId, String message) {
		if (message.contains("%")) return message;
		if (rankId < Rank.ADMIN.getRankId()) return message;
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}