package mc.mythofy.mythofycommands.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum Rank {
    OWNER("OWNER", ChatColor.DARK_RED + "OWNER ", ChatColor.DARK_RED, ChatColor.WHITE, true, 9),
    ADMIN("ADMIN", ChatColor.DARK_RED + "ADMIN ", ChatColor.DARK_RED, ChatColor.WHITE, false, 8),
    DEVELOPER("DEVELOPER", ChatColor.AQUA + "DEVELOPER ", ChatColor.AQUA, ChatColor.WHITE, false, 7),
    MOD("MOD", ChatColor.GOLD + "MODERATOR ", ChatColor.GOLD, ChatColor.WHITE, false, 6),
    TRIALMOD("T.MOD", ChatColor.GOLD + "T. MODERATOR ", ChatColor.GOLD, ChatColor.WHITE, false, 5),
    YT("YT", ChatColor.AQUA + "YT ", ChatColor.AQUA, ChatColor.WHITE, false, 4),
    GIANT("GIANT", ChatColor.RED + "GIANT ", ChatColor.RED, ChatColor.WHITE, false, 3),
    VIP("VIP", ChatColor.GREEN + "VIP ", ChatColor.GREEN, ChatColor.WHITE, false, 2),
    MEMBER("MEMBER", ChatColor.GRAY + "MEMBER ", ChatColor.GRAY, ChatColor.GRAY, false, 1);
    
    @Getter private String name;
    @Getter private String scoreboardName;
    @Getter private ChatColor tagColor;
    @Getter private ChatColor chatColor;
    @Getter private boolean isOp;
    @Getter private int rankId;

    public static Rank fromString(String name) {
        if (name == null) return MEMBER;
        String rankName = name.replaceAll(" ", "");

        for (Rank rank : Rank.values()) {
            if (rank.name.equalsIgnoreCase(rankName)) return rank;
        }
        return MEMBER;
    }
	
    /**
     * Get the formatted name of a rank
     *
     * @return the rank name with any additional formatting that should exist
     */
    public String getFormattedName() {
        return getTagColor() + "" + ChatColor.BOLD + getName();
    }
}