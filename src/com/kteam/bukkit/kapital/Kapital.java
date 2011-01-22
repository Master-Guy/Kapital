package com.kteam.bukkit.kapital;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;

/**
 * Kapital for Bukkit
 *
 * @author Ant59 and Master-Guy
 */

public class Kapital extends JavaPlugin {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    
    private String name;
    private String version;
    private PluginDescriptionFile desc;

    private final MySQL MySQL = new MySQL(this);
    private final KapitalPlayerListener playerListener = new KapitalPlayerListener(this);
    private final KapitalBlockListener blockListener = new KapitalBlockListener(this);

    public FlatProperties settings;
    //public FlatProperties commands;
    
    private KapitalWorld kapitalWorld = new KapitalWorld(this);
    
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public Boolean firstRun = false;

    public Kapital(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);

        this.desc = desc;
        name = this.desc.getName();
        version = this.desc.getVersion();

        if (!folder.exists())
        	firstRun = true;
        folder.mkdirs();

        this.settings = new FlatProperties(folder.getPath() + "/settings.properties");
        //this.commands = new FlatProperties(folder.getPath() + "/commands.properties");
    }

    public void onEnable() {
    	regEvents();
    	checkDB();

    	log.info("[" + name + "] v" + version + " - Loaded and Enabled");
    }
    
    public void onDisable() {
    	log.info("[" + name + "] v" + version + " - Disabled");
    }
    
    private void regEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    }
    
    private void checkDB() {
    	/*
    	MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__cities` (`id` int(11) NOT NULL auto_increment,`mayor` varchar(30) NOT NULL,`welcome` varchar(100) default NULL,`farewell` varchar(100) default NULL,`free_build` tinyint(1) NOT NULL default '0',`nation` int(11) default NULL,PRIMARY KEY  (`id`))");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nations` (`id` int(11) NOT NULL auto_increment,`name` varchar(100) NOT NULL,`gov_type` tinyint(2) NOT NULL,`leader_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nation_cities` (`nation_id` int(11) NOT NULL,`city_id` int(11) NOT NULL)");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nation_relations` (`nation1_id` int(11) NOT NULL,`nation2_id` int(11) NOT NULL,`relation` tinyint(2) NOT NULL)");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__players` (`id` int(11) NOT NULL auto_increment,`name` varchar(100) NOT NULL,PRIMARY KEY  (`id`))");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__player_cities` (`city_id` int(11) NOT NULL,`player_id` int(11) NOT NULL,`player_level` int(11) NOT NULL default '0')");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__plots` (`id` int(11) NOT NULL auto_increment,`city_id` int(11) NOT NULL,`min_x` int(11) NOT NULL,`min_z` int(11) NOT NULL,`max_x` int(11) NOT NULL,`max_z` int(11) NOT NULL,`owner_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__plot_players` (`plot_id` int(11) NOT NULL,`player_id` int(11) NOT NULL)");
		MySQL.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__tiles` (`id` int(11) NOT NULL,`x` int(11) NOT NULL,`z` int(11) NOT NULL,`city_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
		*/
    }
    
    public String getVersion() {
    	return version;
    }
    
    public KapitalWorld getKapitalWorld() {
		return kapitalWorld;
	}
    
    public void msg(Player player, String msg) {
		player.sendMessage(ChatColor.GOLD + "[Kapital] " + ChatColor.WHITE + msg);
	}
    
    public void consoleLog(String msg) {
    	log.info("[" + name + "] v" + version + " - " + msg);
    }
    
    public void consoleWarning(String msg) {
    	log.warning("[" + name + "] v" + version + " - " + msg);
    }
    
    public void consoleError(String msg) {
    	log.severe("[" + name + "] v" + version + " - " + msg);
    }
    
    @Override
    public boolean onCommand(Player player, Command command, String commandLabel, String[] args) {
        String[] split = args;
        String commandName = command.getName().toLowerCase();

        if (commandName.equals("nation")) {
            if (split.length == 0) {
            	msg(player, "Info on nations"); // TODO
                return true;
            } else if (split[0] == "list") {
        		msg(player, "List of nations"); // TODO
        		return true;
            }
        } else if (commandName.equals("city")) {
        	if (split.length == 0) {
            	msg(player, "Info on cities"); // TODO
                return true;
            } else if (split[0] == "list") {
            	if (split.length == 1) {
            		msg(player, "List of cities"); // TODO
            		return true;
            	} else if (split.length == 2) {
            		msg(player, "List of cities in nation " + split[1]); // TODO
            		return true;
            	}
            }
        }
        return false;
    }
    
    
    // DEBUGGING
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
