package mc.mythofy.mythofycommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.DEVELOPER)
public class TpallCommand extends CoreCommand {
	
    public TpallCommand() {
        super("tpall");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.teleport(player.getLocation());
			player.sendMessage(ChatColor.GRAY + "Everyone has been teleported to " + player.getName());
		}
    }
}