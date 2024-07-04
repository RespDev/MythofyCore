package mc.mythofy.mythofycommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.TRIALMOD)
public class MutechatCommand extends CoreCommand {
	
    public MutechatCommand() {
        super("mutechat");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
		MythofyCommands.chatMuted = !MythofyCommands.chatMuted;
		Bukkit.broadcastMessage(
				(MythofyCommands.chatMuted ? ChatColor.RED + "Chat has been muted by " + player.getName() + "."
						: ChatColor.GREEN + "Chat has been unmuted by " + player.getName() + "."));
    }
}