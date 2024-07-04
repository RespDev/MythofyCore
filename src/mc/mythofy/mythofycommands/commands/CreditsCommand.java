package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import network.palace.core.command.CommandException;
import network.palace.core.command.CoreCommand;

public class CreditsCommand extends CoreCommand {

    public CreditsCommand() {
        super("credits");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "-- NETWORK CREDITS --");
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Massive thank you to ResTheCoder for the Backend and Hub plugins.");
        player.sendMessage("");
    }
}