package mc.mythofy.mythofycommands.commands.disabled;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(description = "Disable me command")
public class MeCommand extends CoreCommand {

    public MeCommand() {
        super("me");
    }

    @Override
    protected void handleCommandUnspecific(CommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(ChatColor.RED + "Disabled");
    }
}