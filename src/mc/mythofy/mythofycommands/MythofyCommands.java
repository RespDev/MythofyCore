package mc.mythofy.mythofycommands;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
	public SQLDriver SQL;
	public SQLGetter data;
	@Getter private FileConfiguration config;
	@Getter private String pluginVersion = "0.0.1";

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.SQL = new SQLDriver();
		this.data = new SQLGetter(this);
		plugin = this;
		config = this.getConfig();

		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			Bukkit.getLogger().severe("[MythofyCommands] Failed to connect to the database!");
			e.printStackTrace();
		}
		if (SQL.isConnected()) {
			Bukkit.getLogger().info("[MythofyCommands] Now connected to the database!");
			data.createTable();
		}
		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {
		if (SQL.isConnected())
			SQL.disconnect();
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new Quit(), this);
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
	}

	private void registerCommands() {
		// General Commands
		this.getCommand("discord").setExecutor(new DiscordCommand());

		// Staff Commands
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("mutechat").setExecutor(new MutechatCommand());
		this.getCommand("clearchat").setExecutor(new ClearchatCommand());
		this.getCommand("stop").setExecutor(new StopCommand());
	}

	public static MythofyCommands getInstance() {
		return plugin;
	}
}