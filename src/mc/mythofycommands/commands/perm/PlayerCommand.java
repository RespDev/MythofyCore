package mc.mythofycommands.commands.perm;

import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CommandMeta(description = "Player commands")
public class PlayerCommand extends CoreCommand {

    public PlayerCommand() {
        super("player");
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
        if (args.length < 2) {
            helpMenu(sender);
            return;
        }
        Player player = (Player) Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return;
        }
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        Rank rank = RankManager.getRank(player.getUniqueId());
        List<String> tags = new ArrayList<>();
        switch (args[1].toLowerCase()) {
            case "rank": {
                sender.sendMessage(ChatColor.GREEN + name + " has rank " + rank.getFormattedName());
                return;
            }
            case "setrank": {
                if (args.length < 3) {
                    helpMenu(sender);
                    return;
                }
                Rank next = Rank.fromString(args[2]);
                if (next == null) {
                    sender.sendMessage(ChatColor.RED + args[2] + " isn't a rank!");
                    return;
                }
                if (sender instanceof Player) {
                	Player p = (Player) sender;
                    if (next.getRankId() > RankManager.getRank(p.getUniqueId()).getRankId()) {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to set a player to " + next.getFormattedName() + "!");
                        return;
                    }
                }
                sender.sendMessage(ChatColor.GREEN + name + " is now rank " + next.getFormattedName());
                RankManager.setRank(uuid, next);
                return;
            }
        }
        helpMenu(sender);
    }

    private void helpMenu(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "/perm player [player] rank " + ChatColor.AQUA + "- Get the player's rank.");
        sender.sendMessage(ChatColor.GREEN + "/perm player [player] setrank [rank] " + ChatColor.AQUA + "- Set a player's rank");
    }
}