package mc.mythofy.mythofycommands.rank;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public enum Rank {
	OWNER(14, "Owner", ChatColor.RED),
	MANAGER(13, "Manager", ChatColor.RED),
	MYTHOFYEVENTS(12, "Mythofy Events", ChatColor.LIGHT_PURPLE),
	DEVELOPER(11, "Dev", ChatColor.RED),
	ADMIN(10, "Admin", ChatColor.RED),
	MOD(9, "Mod", ChatColor.DARK_GREEN),
	BUILDTEAM(8, "Build Team", ChatColor.DARK_AQUA),
	HELPER(7, "Helper", ChatColor.BLUE),
	TRAINEE(6, "Trainee", ChatColor.DARK_GREEN),
	YOUTUBE(5, "YouTube", ChatColor.RED),
	OG(4, "OG", ChatColor.DARK_PURPLE),
	MYTH(3, "Myth", ChatColor.LIGHT_PURPLE),
	LEGEND(2, "Legend", ChatColor.AQUA),
	VIP(1, "Vip", ChatColor.GREEN),
	MEMBER(0, "Member", ChatColor.GRAY);
	
	@Getter private final int rankId;
	@Getter private final String tabName;
	@Getter private final ChatColor color;

	Rank(int rankId, String tabName, ChatColor color) {
		this.rankId = rankId;
		this.tabName = tabName;
		this.color = color;
	}

	public static Rank fromId(int id) {
		for (Rank rank : Rank.values()) {
			if (rank.getRankId() == id) return rank;
		}
		return MEMBER;
	}

	public String getSidebarName() {
		return color + tabName;
	}
}