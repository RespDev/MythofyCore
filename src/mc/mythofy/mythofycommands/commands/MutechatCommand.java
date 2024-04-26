package mc.mythofy.mythofycommands.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class MutechatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		if (RankManager.getRank(uuid).getRankId() >= Rank.TRAINEE.getRankId()) {
			MythofyCommands.chatMuted = !MythofyCommands.chatMuted;
			Bukkit.broadcastMessage(
					(MythofyCommands.chatMuted ? ChatColor.RED + "Chat has been muted by " + p.getName() + "."
							: ChatColor.GREEN + "Chat has been unmuted by " + p.getName() + "."));
		} else {
			sender.sendMessage(ChatColor.RED + "No permission!");
		}
		return true;
	}
}