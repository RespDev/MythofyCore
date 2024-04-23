package mc.mythofy.mythofycommands.commands;

import java.util.UUID;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class RankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Rank isUsingUserRank = sender instanceof Player ? RankManager.getRank(((Player) sender).getUniqueId())
				: Rank.ADMIN;
		if (isUsingUserRank.getRankId() < Rank.DEVELOPER.getRankId()) {
			sender.sendMessage(ChatColor.RED + "No permission!");
			return true;
		}

		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Usage: /rank (player) (rank)");
			return true;
		}

		OfflinePlayer playerToRank = (OfflinePlayer) Bukkit.getOfflinePlayer(args[0]);
		UUID uuid = playerToRank.getUniqueId();
		Rank targetUserRank = RankManager.getRank(uuid);

		Rank rankToSet = Arrays.stream(Rank.values()).filter(rank -> rank.name().equalsIgnoreCase(args[1])).findFirst()
				.orElse(null);
		// Check if rank is valid
		if (rankToSet == null) {
			sender.sendMessage(ChatColor.RED + "That rank does not exist!");
			return true;
		}

		// Quick checks to perform
		if (!(isUsingUserRank.getRankId() == Rank.OWNER.getRankId())) {
			if (targetUserRank == Rank.OWNER) {
				sender.sendMessage(ChatColor.RED + "You may not set this users rank!");
				return true;
			}
		}
		
		if (rankToSet == Rank.OWNER) {
			if (isUsingUserRank != Rank.OWNER) {
				sender.sendMessage(ChatColor.RED + "You cannot set someone to that rank!");
				return true;
			}
		}

		RankManager.setRank(uuid, rankToSet);
		sender.sendMessage(ChatColor.GREEN + "Set " + args[0] + " rank to " + rankToSet.name() + "!");
		return true;
	}
}