package mc.mythofy.mythofycommands;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;
import mc.mythofy.mythofycommands.commands.ClearchatCommand;
import mc.mythofy.mythofycommands.commands.DiscordCommand;
import mc.mythofy.mythofycommands.commands.MutechatCommand;
import mc.mythofy.mythofycommands.commands.RankCommand;
import mc.mythofy.mythofycommands.commands.StopCommand;
import mc.mythofy.mythofycommands.listeners.Chat;
import mc.mythofy.mythofycommands.listeners.Join;
import mc.mythofy.mythofycommands.listeners.Quit;
import mc.mythofy.mythofycommands.sql.SQLDriver;
import mc.mythofy.mythofycommands.sql.SQLGetter;

public class MythofyCommands extends JavaPlugin {

	/*
	 * 
	 * Written by Res
	 * 
	 */

	private static MythofyCommands plugin;
	public static Boolean chatMuted = false;
	@Getter private static FileConfiguration mythofyConfig;
	public SQLDriver SQL;
	public SQLGetter data;
	@Getter private String pluginVersion = "0.0.1";

	@Override
	public void onEnable() {
		// Load Plugin
		plugin = this;
		// Kick all players on reload
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(ChatColor.RED + "Server is reloading!");
		}
		// Load Config
		plugin.saveDefaultConfig();
		mythofyConfig = plugin.getConfig();
		// Load SQL
		this.SQL = new SQLDriver();
		this.data = new SQLGetter(this);

		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			logError("Core", "Failed to connect to the database");
			e.printStackTrace();
		}
		if (SQL.isConnected()) {
			logMessage("Core", "Now connected to the database");
			data.createTable();
		}
		registerCommands();
		registerListeners();
		MythofyCommands.logMessage("Core", "Enabled");
	}

	@Override
	public void onDisable() {
		if (SQL.isConnected())
			SQL.disconnect();
		MythofyCommands.logMessage("Core", "Disabled");
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new Quit(), this);
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		MythofyCommands.logMessage("Core", "Listeners registered");
	}

	private void registerCommands() {
		// General Commands
		this.getCommand("discord").setExecutor(new DiscordCommand());

		// Staff Commands
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("mutechat").setExecutor(new MutechatCommand());
		this.getCommand("clearchat").setExecutor(new ClearchatCommand());
		this.getCommand("stop").setExecutor(new StopCommand());
		MythofyCommands.logMessage("Core", "Commands registered");
	}

	public static MythofyCommands getInstance() {
		return plugin;
	}
	
	public static void logMessage(String name, String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + name + "> " + ChatColor.GREEN + "" + message);
	}
	
	public static void logError(String name, String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + name + "> " + ChatColor.RED + "" + message);
	}
}