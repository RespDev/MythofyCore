package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.MOD)
public class HealCommand extends CoreCommand {
	
    public HealCommand() {
        super("heal");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.sendMessage(ChatColor.GREEN + "You have been healed!");
    }
}
