package mc.mythofy.mythofycommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.rank.Rank;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.ADMIN)
public class MasssayCommand extends CoreCommand {
	
    public MasssayCommand() {
        super("masssay");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
    	// Check for args
    	if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/masssay [Message]");
            return;
        }

    	// Make args into a StringBuilder
        StringBuilder message = new StringBuilder();
        for (String s : args) {
            message.append(s).append(" ");
        }
        
        // Finally make everyone say it
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.chat(message.toString());
		}
    }
}