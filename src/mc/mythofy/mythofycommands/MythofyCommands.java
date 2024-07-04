package mc.mythofy.mythofycommands;

import java.net.URLClassLoader;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import lombok.Getter;
import lombok.Setter;
import mc.mythofy.mythofycommands.commands.*;
import mc.mythofy.mythofycommands.commands.disabled.MeCommand;
import mc.mythofy.mythofycommands.commands.disabled.PrefixCommandListener;
import mc.mythofy.mythofycommands.commands.disabled.StopCommand;
import mc.mythofy.mythofycommands.player.impl.managers.PlayerScoreboardManager;
import mc.mythofy.mythofycommands.sql.SQLDriver;
import mc.mythofy.mythofycommands.sql.SQLGetter;
import mc.mythofycommands.commands.perm.PermissionCommand;
import mc.mythofycommands.player.impl.Chat;
import mc.mythofycommands.player.impl.Join;
import mc.mythofycommands.player.impl.Quit;
import network.palace.core.command.CoreCommand;
import network.palace.core.command.CoreCommandMap;
import network.palace.core.library.LibraryHandler;
import network.palace.core.plugin.PluginInfo;

@PluginInfo(name = "MythofyCommands", version = "0.0.1", depend = { "ProtocolLib" })
public class MythofyCommands extends JavaPlugin {

	/**
	 * 
	 * MythofyCommands
	 * @author Res
	 * A lot of code merged in from Palace Interactive Archive/Core so big credit to those devs that worked on it!
	 */
	
    @Getter private URLClassLoader coreClassLoader;
	@Getter private static MythofyCommands instance;
	@Getter private static String minecraftVersion = Bukkit.getBukkitVersion();
	@Getter private static FileConfiguration coreConfig;
	@Getter private static String instanceName;
	@Getter @Setter private static String tabHeader;
	@Getter @Setter private static String tabFooter; 
	
	public SQLDriver SQL;
	public SQLGetter data;
	public static Boolean chatMuted = false;
    private boolean starting = true;
    private boolean debug = false;
	private BukkitTask scoreboardTask;
    private CoreCommandMap commandMap;
    
    @Override
    public void onLoad() {
        this.coreClassLoader = (URLClassLoader) getClass().getClassLoader();
    }
    
	@Override
	public final void onEnable() {
		// Load Plugin
		instance = this;
		// Kick all players on reload
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(ChatColor.RED + "Server is reloading!");
		}
        // Load needed libraries for Core
		LibraryHandler.loadLibraries(this);
		// Load Config
		instance.saveDefaultConfig();
		coreConfig = instance.getConfig();
		instanceName = getCoreConfig().getString("server.instance-name");
        debug = getCoreConfig().getBoolean("debug", false);
		tabHeader = ChatColor.AQUA + "Galaxy Parks";
		tabFooter = ChatColor.GREEN + "You're on the " + ChatColor.AQUA + instanceName + ChatColor.GREEN + " server.";
		// Load SQL variables
		this.SQL = new SQLDriver();
		this.data = new SQLGetter(this);
		// Managers
		// TODO Custom Player Class cause its better
		// Core Command Map
		commandMap = new CoreCommandMap(this);
		// Connect to SQL
		try {
			SQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			logMessage("Core", ChatColor.DARK_RED + "Failed to connect to the database");
			e.printStackTrace();
		}
		if (SQL.isConnected()) {
			logMessage("Core", "Now connected to the database");
			data.createTable();
		}
		// Load commands & listeners
		registerCommands();
		registerDisabledCommands();
		registerListeners();
		// Load runnables
		if (coreConfig.getBoolean("default-scoreboard") == true)
			scoreboardTask = getServer().getScheduler().runTaskTimer(instance, new PlayerScoreboardManager(), 0, 1);
		// Enabled
		logMessage("Core", "Enabled");
        // Always keep players off the server until it's been finished loading for 1 second
        // This prevents issues with not loading player data when they join before plugins are loaded
        runTaskLater(this, () -> {
            setStarting(false);
        }, 20);
	}

	private void registerListeners() {
		registerListener(new Join());
		registerListener(new Quit());
		registerListener(new Chat());
		registerListener(new PrefixCommandListener());
		registerListener(new FreezeCommand());
	}

	private void registerCommands() {
		// General Commands
		registerCommand(new DiscordCommand());
		registerCommand(new CreditsCommand());
		// Staff Commands
		registerCommand(new MutechatCommand());
		registerCommand(new ClearchatCommand());
		registerCommand(new FlyCommand());
		registerCommand(new FreezeCommand());
		registerCommand(new MasssayCommand());
		registerCommand(new GMCommand());
		registerCommand(new PermissionCommand());
		registerCommand(new PluginsCommand());
		registerCommand(new HealCommand());
		registerCommand(new HelpopCommand());
		registerCommand(new ShutdownCommand());
		registerCommand(new LockArmorStandCommand());
		registerCommand(new TeleportCommand());
		registerCommand(new TpallCommand());
		registerCommand(new BroadcastCommand());
	}
	
	private void registerDisabledCommands() {
		// Disabled Commands
		registerCommand(new StopCommand());
		registerCommand(new MeCommand());
	}
	
    /**
     * Register a core command.
     *
     * @param command the command
     */
    public final void registerCommand(CoreCommand command) {
        commandMap.registerCommand(command);
    }
	
	@Override
	public final void onDisable() {
		if (SQL.isConnected())
			SQL.disconnect();
		if (scoreboardTask != null && !scoreboardTask.isCancelled())
			scoreboardTask.cancel();
        logMessage("Core", ChatColor.DARK_RED + "Disabled");
	}
	
    /**
     * Is debug enabled.
     *
     * @return the boolean
     */
    public static boolean isDebug() {
        return getInstance().debug;
    }

    /**
     * Log message.
     *
     * @param name    the name
     * @param message the message
     */
    public static void logMessage(String name, String message) {
        logInfo(ChatColor.GOLD + name + ChatColor.DARK_GRAY + " > " + message);
    }
    
    /**
     * Debug log.
     *
     * @param s the message to log
     */
    public static void debugLog(String s) {
        if (isDebug()) {
            logMessage("CORE-DEBUG", s);
        }
    }

    /**
     * Log info.
     *
     * @param message the message
     */
    public static void logInfo(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
    
    /**
     * Gets command map.
     *
     * @return the command map
     */
    public static CoreCommandMap getCommandMap() {
        return getInstance().commandMap;
    }
    
    /**
     * The main world
     *
     * @return the default world
     */
    public static World getDefaultWorld() {
        return Bukkit.getWorlds().get(0);
    }
    
    /**
     * Shutdown the server.
     */
    public static void shutdown() {
        Bukkit.shutdown();
    }

    /**
     * Register listener.
     *
     * @param listener the listener to be registered
     */
    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    /**
     * Call event.
     *
     * @param event the event
     */
    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    /**
     * Cancel task.
     *
     * @param taskId the task id
     */
    public static void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    /**
     * Call sync method
     *
     * @param callable the callable
     * @return future
     */
    public static <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> callable) {
        return Bukkit.getScheduler().callSyncMethod(plugin, callable);
    }

    /**
     * Run task asynchronously int.
     *
     * @param task the task
     * @return the task id
     */
    public static int runTaskAsynchronously(Plugin plugin, Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, task).getTaskId();
    }

    /**
     * Run task later async int.
     *
     * @param task  the task
     * @param delay the delay
     * @return the task id
     */
    public static int runTaskLaterAsynchronously(Plugin plugin, Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay).getTaskId();
    }

    /**
     * Run task later int.
     *
     * @param task  the task
     * @param delay the delay
     * @return the task id
     */
    public static int runTaskLater(Plugin plugin, Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLater(plugin, task, delay).getTaskId();
    }

    /**
     * Run task timer asynchronously int.
     *
     * @param task   the task
     * @param delay  the delay
     * @param period the period
     * @return the task id
     */
    public static int runTaskTimerAsynchronously(Plugin plugin, Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period).getTaskId();
    }

    /**
     * Run task timer int.
     *
     * @param task   the task
     * @param delay  the delay
     * @param period the period
     * @return the task id
     */
    public static int runTaskTimer(Plugin plugin, Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period).getTaskId();
    }

    /**
     * Run task int.
     *
     * @param task the task
     * @return the task id
     */
    public static int runTask(Plugin plugin, Runnable task) {
        return Bukkit.getScheduler().runTask(plugin, task).getTaskId();
    }
    
    /**
     * Is core starting.
     *
     * @return the boolean
     */
    public static boolean isStarting() {
        return getInstance().starting;
    }

    /**
     * Sets starting.
     *
     * @param isStarting the is starting
     */
    public static void setStarting(boolean isStarting) {
        if (!isStarting) logMessage("Core", ChatColor.DARK_GREEN + "Server Joinable!");
        else logMessage("Core", ChatColor.DARK_RED + "Server Not Joinable!");
        getInstance().starting = isStarting;
    }
    
    /**
     * Gets core version.
     *
     * @return Core version
     */
    public static String getVersion() {
        return getInstance().getDescription().getVersion();
    }
}