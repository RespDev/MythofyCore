package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.TRIALMOD)
public class FlyCommand extends CoreCommand {

    public FlyCommand() {
        super("fly");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        if (player.getAllowFlight() == false) {
        	player.setAllowFlight(true);
        	player.sendMessage(ChatColor.GREEN + "Flight enabled.");
        }
        else {
        	player.setAllowFlight(false);
        	player.sendMessage(ChatColor.RED + "Flight disabled.");
        }
    }
}
