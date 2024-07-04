package mc.mythofy.mythofycommands.commands.disabled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import network.palace.core.command.CommandException;
import network.palace.core.command.CoreCommand;

public class StopCommand extends CoreCommand {
	
    public StopCommand() {
        super("stop");
    }

    @Override
    protected void handleCommand(ConsoleCommandSender commandSender, String[] args) throws CommandException {
        Bukkit.getWorlds().forEach(World::save);
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(ChatColor.RED + "Server is stopping. Please rejoin in a few!"));
        Bukkit.shutdown();
    }
    
    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        player.sendMessage(ChatColor.RED + "Disabled");
    }
    
    @Override
    protected void handleCommand(BlockCommandSender commandSender, String[] args) throws CommandException {
        commandSender.sendMessage(ChatColor.RED + "Disabled");
    }
}