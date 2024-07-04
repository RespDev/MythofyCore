package mc.mythofy.mythofycommands.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mc.mythofy.mythofycommands.rank.Rank;
import mc.mythofy.mythofycommands.rank.RankManager;
import network.palace.core.command.CommandException;
import network.palace.core.command.CommandMeta;
import network.palace.core.command.CoreCommand;

@CommandMeta(rank = Rank.MOD)
public class FreezeCommand extends CoreCommand implements Listener {

	private static List<UUID> frozenPlayers = new ArrayList<UUID>();
	
    public FreezeCommand() {
        super("freeze");
    }

    @Override
    protected void handleCommand(Player player, String[] args) throws CommandException {
    	if (args.length == 1) {
            Player froze = Bukkit.getPlayer(args[0]);
            if (froze == null) {
                player.sendMessage(ChatColor.RED + "Player not found!");
                return;
            }
            toggleFreeze(froze, player);
            return;
        }
    	else {
    		player.sendMessage(ChatColor.RED + "/freeze (player)");
    	}
    }
    
    private void toggleFreeze(Player player, Player sender) {
    	UUID uuid = player.getUniqueId();
    	if (RankManager.getRank(sender.getUniqueId()).getRankId() <= RankManager.getRank(uuid).getRankId()) {
    		sender.sendMessage(ChatColor.RED + "You cannot freeze someone who is your rank or higher!");
    		return;
    	}
    	
    	if (frozenPlayers.contains(uuid)) {
    		frozenPlayers.remove(uuid);
    		player.sendMessage(ChatColor.GREEN + "You have been unfrozen, you may now logout.");
    		sender.sendMessage(ChatColor.GREEN + player.getName() + " has been unfrozen successfully.");
    	}
    	else {
    		frozenPlayers.add(uuid);
    		player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "           --> FROZEN <--");
    		player.sendMessage(ChatColor.RED + "You have been frozen do not logout");
    		player.sendMessage(ChatColor.RED + "or you will be banned from the server!");
    		player.sendMessage(" ");
    		player.sendMessage(ChatColor.RED + "Join our discord with /discord!");
    		sender.sendMessage(ChatColor.GREEN + player.getName() + " has been frozen successfully.");
    	}
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
    			
    	if (frozenPlayers.contains(uuid)) {
    		frozenPlayers.remove(uuid);
			for (Player online : Bukkit.getOnlinePlayers())
				if (RankManager.getRank(online.getUniqueId()).getRankId() >= Rank.TRIALMOD.getRankId())
					player.sendMessage("[" + ChatColor.RED + "STAFF" + ChatColor.WHITE + "] " + ChatColor.RED
							+ player.getName() + " logged out while frozen!");
    	}
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
    	Player player = event.getPlayer();
    	UUID uuid = player.getUniqueId();
    	
    	if (frozenPlayers.contains(uuid)) event.setCancelled(true);
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
    	if (event.getEntity() instanceof Player) {
    		Player player = (Player) event.getEntity();
    		UUID uuid = player.getUniqueId();
    		
    		if (frozenPlayers.contains(uuid)) {
    			event.setCancelled(true);
    		}
    	}
    }
}