package mc.mythofy.mythofycommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player)) {
			shutdown();
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(ChatColor.RED + "Disabled!");
		return true;
	}

	public void shutdown() {
		Integer size = Bukkit.getOnlinePlayers().size();
		if (size > 0) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.kickPlayer(ChatColor.RED + "Instance shutting down!");
				Bukkit.shutdown();
			}
		} else {
			Bukkit.shutdown();
		}
	}
}