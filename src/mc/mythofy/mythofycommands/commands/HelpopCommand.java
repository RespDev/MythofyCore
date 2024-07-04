package mc.mythofy.mythofycommands.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;

/**
 * The type Helpop command.
 */
@CommandMeta(aliases = "ac", description = "Staff Chat command", rank = Rank.TRIALMOD)
public class HelpopCommand extends CoreCommand {

    /**
     * Instantiates a new Helpop command.
     */
    public HelpopCommand() {
        super("helpop");
    }

    @Override
    protected void handleCommand(ConsoleCommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/ac [Message]");
            return;
        }
        StringBuilder msg = new StringBuilder();
        for (String s : args) {
            msg.append(s).append(" ");
        }
        message("Console", msg.toString());
    }

    @Override
    protected void handleCommand(BlockCommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/ac [Message]");
            return;
        }
        StringBuilder msg = new StringBuilder();
        for (String s : args) {
            msg.append(s).append(" ");
        }
        Location loc = sender.getBlock().getLocation();
        message("CB (x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ() + ")", msg.toString());
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/ac [Message]");
            return;
        }
        StringBuilder msg = new StringBuilder();
        for (String s : args) {
            msg.append(s).append(" ");
        }
        message(player.getName(), msg.toString());
    }

    private void message(String sender, String message) {
        String msg = ChatColor.DARK_RED + "[ADMIN CHAT] " + ChatColor.GRAY + sender + ": " + ChatColor.WHITE +
                ChatColor.translateAlternateColorCodes('&', message);
        for (Player tp : Bukkit.getOnlinePlayers()) {
            if (RankManager.getRank(tp.getUniqueId()).getRankId() >= Rank.TRIALMOD.getRankId()) {
                tp.sendMessage(msg);
            }
        }
        Bukkit.getLogger().info(msg);
    }
}
