package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("discord")) {
			sender.sendMessage(ChatColor.GREEN + "Join our discord at " + ChatColor.GOLD + "discord.mythofy.net"
					+ ChatColor.GREEN + "!");
		}
		return false;
	}

}
