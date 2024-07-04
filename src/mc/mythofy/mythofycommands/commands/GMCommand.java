package mc.mythofy.mythofycommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.ADMIN)
public class GMCommand extends CoreCommand {

    public GMCommand() {
        super("gm");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GREEN + "Gamemode updated to CREATIVE!");
        	return;
        }
    	player.setGameMode(GameMode.SURVIVAL);
    	player.sendMessage(ChatColor.GREEN + "Gamemode updated to SURVIVAL!");
    }
}
