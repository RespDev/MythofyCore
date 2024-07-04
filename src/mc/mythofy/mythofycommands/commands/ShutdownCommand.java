package mc.mythofy.mythofycommands.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
//import network.palace.core.messagequeue.packets.EmptyServerPacket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;

/**
 * Server shutdown command.
 */
@CommandMeta(description = "Safely stop the server.", aliases = "sd", rank = Rank.DEVELOPER)
public class ShutdownCommand extends CoreCommand {
    private int taskID = 0;

    /**
     * Instantiates a new Safestop command.
     */
    public ShutdownCommand() {
        super("shutdown");
    }

    @Override
    protected void handleCommandUnspecific(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/shutdown [delay]");
            sender.sendMessage(ChatColor.RED + "/shutdown cancel " + ChatColor.AQUA + "- Cancel existing shutdown");
            return;
        }
        if (args[0].equalsIgnoreCase("cancel")) {
            if (taskID == 0) {
                sender.sendMessage(ChatColor.RED + "No shutdown is pending!");
                return;
            }
            MythofyCommands.cancelTask(taskID);
            taskID = 0;
            sender.sendMessage(ChatColor.GREEN + "Shutdown cancelled!");
            return;
        }
        if (taskID != 0) {
            sender.sendMessage(ChatColor.RED + "A shutdown is already pending! Run '/shutdown cancel' to cancel it.");
            return;
        }
        final int delay;
        try {
            delay = Integer.parseInt(args[0]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(ChatColor.RED + "/shutdown [delay]");
            return;
        }
        sender.sendMessage(ChatColor.RED + "Shutting the server down in " + delay + " seconds...");
        taskID = MythofyCommands.runTaskTimer(MythofyCommands.getInstance(), new Runnable() {
            int i = delay, count = 19;

            @Override
            public void run() {
                if (++count % 20 != 0) return;
                count = 0;
                if (i > 0) {
                    message(i);
                    i--;
                    return;
                }
                if (i-- < 0) return;
                MythofyCommands.setStarting(true);
                Bukkit.getWorlds().forEach(World::save);
                /*try {
                	MythofyCommands.getMessageHandler().sendMessage(new EmptyServerPacket(MythofyCommands.getInstanceName()), MythofyCommands.getMessageHandler().ALL_PROXIES);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                MythofyCommands.runTaskTimer(MythofyCommands.getInstance(), () -> {
                    if (Bukkit.getOnlinePlayers().size() <= 0) {
                        Bukkit.shutdown();
                    }
                }, 40L, 40L);
            }
        }, 0L, 1L);
    }

    public void message(int seconds) {
        if (seconds <= 0) {
            return;
        }
        String time;
        if (seconds > 60 && seconds % 60 == 0) {
            time = seconds / 60 + " minutes";
        } else if (seconds == 60) {
            time = "1 minute";
        } else if (seconds == 30) {
            time = "30 seconds";
        } else {
            return;
        }
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + ChatColor.GREEN + "This server (" + MythofyCommands.getInstanceName() + ") will restart in " + time);
    }
}
