package mc.mythofy.mythofycommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.TRIALMOD, aliases = "cc")
public class ClearchatCommand extends CoreCommand {
	
    public ClearchatCommand() {
        super("clearchat");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (RankManager.getRank(player.getUniqueId()).getRankId() < Rank.TRIALMOD.getRankId()) {
				for (int i = 0; i < 150; i++)
					all.sendMessage(" ");
			}
		}
		Bukkit.broadcastMessage(ChatColor.GREEN + "Server chat has been cleared.");
    }
}