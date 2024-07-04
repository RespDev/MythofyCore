package mc.mythofy.mythofycommands.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import mc.mythofy.mythofycommands.rank.Rank;

@CommandMeta(description = "Broadcast to the server", rank = Rank.DEVELOPER)
public class BroadcastCommand extends CoreCommand {

    public BroadcastCommand() {
        super("bc");
    }

    @Override
    protected void handleCommandUnspecific(CommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/bc [Message]");
            return;
        }
        StringBuilder message = new StringBuilder();
        for (String s : args) {
            message.append(s).append(" ");
        }
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "BROADCAST" + ChatColor.DARK_GRAY + "" +
                ChatColor.BOLD + " >> " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message.toString().trim()));
    }
}