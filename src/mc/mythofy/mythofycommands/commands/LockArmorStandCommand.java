package mc.mythofy.mythofycommands.commands;

import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import mc.mythofy.mythofycommands.MythofyCommands;
import mc.mythofy.mythofycommands.rank.Rank;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

@CommandMeta(description = "Toggle whether players can edit the items on an armor stand", rank = Rank.TRIALMOD)
public class LockArmorStandCommand extends CoreCommand {

    public LockArmorStandCommand() {
        super("lockarmorstand");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
        Collection<ArmorStand> stands = player.getWorld().getEntitiesByClass(ArmorStand.class);
        Location loc = player.getLocation();

        ArmorStand closest = null;
        double distance = -1;
        for (ArmorStand stand : stands) {
            if (distance == -1 && stand.getLocation().distance(loc) <= 2) {
                closest = stand;
                distance = stand.getLocation().distance(loc);
            } else if (stand.getLocation().distance(loc) < distance) {
                closest = stand;
                distance = stand.getLocation().distance(loc);
            }
        }

        if (closest == null) {
            player.sendMessage(ChatColor.RED + "There are no ArmorStands within two blocks!");
            return;
        }

        try {
            String lockField;
            switch (MythofyCommands.getMinecraftVersion()) {
                case "v1_13_R1":
                case "v1_13_R2":
                    lockField = "bH";
                    break;
                case "v1_12_R1":
                    lockField = "bB";
                    break;
                default:
                    lockField = "bA";
                    break;
            }
            Field f = Class.forName("net.minecraft.server." + MythofyCommands.getMinecraftVersion() + ".EntityArmorStand")
                    .getDeclaredField(lockField);
            if (f != null) {
                f.setAccessible(true);
                Object craftStand = Class.forName("org.bukkit.craftbukkit." + MythofyCommands.getMinecraftVersion() +
                        ".entity.CraftArmorStand").cast(closest);
                Object handle = craftStand.getClass().getDeclaredMethod("getHandle").invoke(craftStand);
                int i = (int) f.get(handle);
                if (i == 2096896) {
                    player.sendMessage(ChatColor.YELLOW + (closest.getCustomName() == null ? "The Armor Stand" : closest.getCustomName()) + ChatColor.GREEN + " has been unlocked!");
                    f.set(handle, 0);
                } else {
                    player.sendMessage(ChatColor.YELLOW + (closest.getCustomName() == null ? "The Armor Stand" : closest.getCustomName()) + ChatColor.GREEN + " has been locked!");
                    f.set(handle, 2096896);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
