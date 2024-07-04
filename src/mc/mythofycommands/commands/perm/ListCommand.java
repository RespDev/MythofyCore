package mc.mythofycommands.commands.perm;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import mc.mythofy.mythofycommands.rank.Rank;

@CommandMeta(description = "List ranks")
public class ListCommand extends CoreCommand {

    public ListCommand() {
        super("list");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        handle(player, args);
    }

    @Override
    protected void handleCommand(ConsoleCommandSender commandSender, String[] args) throws CommandException {
        handle(commandSender, args);
    }

    protected void handle(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "- /perm list ranks " + ChatColor.AQUA + "- List ranks");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "ranks": {
                sender.sendMessage(ChatColor.AQUA + "Galaxy Parks Ranks:");
                sender.sendMessage(ChatColor.GREEN + "- id:dbname:display name");
                for (Rank rank : Rank.values()) {
                    sender.sendMessage(ChatColor.GREEN + "- " + rank.getRankId() + ":" + rank.getName() + ":" + rank.getFormattedName());
                }
                break;
            }
        }
    }
}
