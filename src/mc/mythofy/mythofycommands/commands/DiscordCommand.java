package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import network.palace.core.command.CommandException;
import network.palace.core.command.CoreCommand;

public class DiscordCommand extends CoreCommand {

    public DiscordCommand() {
        super("discord");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        player.sendMessage(ChatColor.GREEN + "Join our discord at " + ChatColor.GOLD + "discord.mythofy.net"
				+ ChatColor.GREEN + "!");
    }
}
