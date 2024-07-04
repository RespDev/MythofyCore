package mc.mythofy.mythofycommands.player.impl.managers;

//import java.lang.reflect.Field;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
//import mc.mythofy.mythofycommands.MythofyCommands;
//import net.minecraft.server.v1_12_R1.ChatComponentText;
//import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;

public class PlayerTabManager {
	
	public void setHeaderFooter(Player player) {
		/*PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			
			Field b = packet.getClass().getDeclaredField("b");
			b.setAccessible(true);
			
			a.set(packet, new ChatComponentText(MythofyCommands.getTabHeader()));
			b.set(packet, new ChatComponentText(MythofyCommands.getTabFooter()));
			
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
