package mc.mythofycommands.player.impl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class Chat implements Listener {
	
	// Chat Manager
	@EventHandler
	public void onChat(AsyncPlayerChatEvent chat) {
		chat.setCancelled(true);

		Player player = (Player) chat.getPlayer();
		Rank rank = RankManager.getRank(player.getUniqueId());
		Integer rankId = rank.getRankId();
		String message = chat.getMessage();
		String name = player.getName();

		// TODO Code Anti-Swear System

		chat.setFormat(getFormat(player, rank, name, message));

		// Chat Mute
		if (MythofyCommands.chatMuted == true) {
			if (rankId >= Rank.TRIALMOD.getRankId()) {
				chat.setCancelled(false);
				return;
			}
			chat.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Chat is currently muted.");
			return;
		}
		chat.setCancelled(false);
	}
	
	private String getFormat(Player player, Rank rank, String name, String message) {
		return rank.getFormattedName() + " " + rank.getTagColor() + name + rank.getChatColor() + ": " + message;
	}
}