package mc.mythofy.mythofycommands.commands;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

public class InfoCommand implements CommandExecutor {
	
	private FileConfiguration config = MythofyCommands.getInstance().getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		if (label.equalsIgnoreCase("info")) {
			if (RankManager.getRank(uuid).getRankId() >= Rank.DEVELOPER.getRankId()) {
				p.sendMessage(ChatColor.GREEN + "Instance Info:");
				p.sendMessage(ChatColor.GREEN + "Instance-name: " + config.getString("instance-name"));
				p.sendMessage(ChatColor.GREEN + "Instance-type: " + config.getString("instance-type"));
				p.sendMessage(ChatColor.GREEN + "Plugin-version: " + MythofyCommands.getInstance().getPluginVersion());
			} else {
				sender.sendMessage(ChatColor.RED + "No permission!");
				return true;
			}
		}
		return false;
	}
}
