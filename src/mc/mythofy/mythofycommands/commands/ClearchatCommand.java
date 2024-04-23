package mc.mythofy.mythofycommands.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class ClearchatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		if (name.equalsIgnoreCase("clearchat")) {
			if (RankManager.getRank(uuid).getRankId() >= Rank.TRAINEE.getRankId()) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (RankManager.getRank(uuid).getRankId() < Rank.TRAINEE.getRankId()) {
						for (int i = 0; i < 150; i++)
							all.sendMessage(" ");
					}
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + "Server chat has been cleared.");
			} else {
				p.sendMessage(ChatColor.RED + "No permission!");
				return true;
			}
		}
		return false;
	}
}