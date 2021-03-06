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
import org.bukkit.command.CommandSender;

/**
 * Kapital for Bukkit
 *
 * @author Ant59 and Master-Guy
 */

public class Kapital extends JavaPlugin {
	public boolean debugging = true;
	
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    
    public String name;
    public String version;
    public PluginDescriptionFile desc;
    public static File k_Folder;
    
    private final KapitalPlayerListener playerListener = new KapitalPlayerListener(this);
    private final KapitalBlockListener blockListener = new KapitalBlockListener(this);

    private KapitalWorld kapitalWorld = new KapitalWorld(this);
    
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    private MySQL sql;
	
	//public KapitalProperties kapitalProperties = new KapitalProperties();

    public Kapital(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);

        this.desc = desc;
        name = this.desc.getName();
        version = this.desc.getVersion();
        k_Folder = folder;
    }

    public void onEnable() {
		if (!k_Folder.exists())
			k_Folder.mkdir();
    	KapitalSettings.loadConfig();
    	
    	try {
    		createTables();
    	} catch (Exception e) {
			consoleWarning("Failed to check database tables exist: "+e.toString());
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}

    	log.info("[" + name + "] v" + version + " - Loaded and Enabled");
    }
    
    public void onDisable() {
    	log.info("[" + name + "] v" + version + " - Disabled");
    }
    
    private void createTables() {
    	sql = new MySQL(this);
    	sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__cities` (`id` int(11) NOT NULL auto_increment,`name` varchar(100) NOT NULL,`mayor` varchar(30) NOT NULL,`welcome` varchar(100) default NULL,`farewell` varchar(100) default NULL,`free_build` tinyint(1) NOT NULL default '0',`nation` int(11) default NULL,PRIMARY KEY  (`id`))");
    	sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nations` (`id` int(11) NOT NULL auto_increment,`name` varchar(100) NOT NULL,`gov_type` tinyint(2) NOT NULL,`leader_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nation_cities` (`nation_id` int(11) NOT NULL,`city_id` int(11) NOT NULL)");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__nation_relations` (`nation1_id` int(11) NOT NULL,`nation2_id` int(11) NOT NULL,`relation` tinyint(2) NOT NULL)");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__players` (`id` int(11) NOT NULL auto_increment,`name` varchar(100) NOT NULL,PRIMARY KEY  (`id`))");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__player_cities` (`city_id` int(11) NOT NULL,`player_id` int(11) NOT NULL,`player_level` int(11) NOT NULL default '0')");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__plots` (`id` int(11) NOT NULL auto_increment,`city_id` int(11) NOT NULL,`min_x` int(11) NOT NULL,`min_z` int(11) NOT NULL,`max_x` int(11) NOT NULL,`max_z` int(11) NOT NULL,`owner_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__plot_players` (`plot_id` int(11) NOT NULL,`player_id` int(11) NOT NULL)");
		sql.tryUpdate("CREATE TABLE IF NOT EXISTS `kapital__tiles` (`id` int(11) NOT NULL,`x` int(11) NOT NULL,`z` int(11) NOT NULL,`city_id` int(11) NOT NULL,PRIMARY KEY  (`id`))");
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
    
    public void msg(CommandSender sender, String msg) {
    	sender.sendMessage(ChatColor.GOLD + "[Kapital] " + ChatColor.WHITE + msg);
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
    
    public void debug(String msg) {
    	if(debugging) {
    		System.out.println("[DBG " + name + "] "+msg);
    	}
    }
    
    private boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        Player player = null;
        
        if (commandName.equals("nation")) {
            return true; // performTeleport(sender, trimmedArgs);
        } else if (commandName.equals("city")) {
        	if (args.length == 0) {
        		msg(sender, "The following commands are available:");
        		msg(sender, " ");
        		msg(sender, "/city start "+ChatColor.GOLD+"townname mayor"+ChatColor.DARK_GREEN+" - Creates a city");
        		msg(sender, "/city list [page]"+ChatColor.DARK_GREEN+" - Lists all created cities and mayors");
        		msg(sender, "/city info"+ChatColor.DARK_GREEN+" - Show information about your city");
        		msg(sender, "/city set"+ChatColor.DARK_GREEN+" - Show information about managing your city");
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
            	Integer pageNr = 1;
            	if (args.length > 1) {
            		try {
            			pageNr = Integer.parseInt(args[1]);
            		} catch (Exception e) {
            			pageNr = 1;
            		}
            	}
        		msg(sender, "Show list of cities - page #"+pageNr); // TODO
        		return true;
            } else if (args[0].equalsIgnoreCase("start")) {
            	if (args.length == 3) {
            		if (anonymousCheck(sender)) return false;
                    player = (Player)sender;
            		return kapitalWorld.startCity(player, args[1], args[2]);
                } else {
            		msg(sender, "This command requires two special parameters:");
            		msg(sender, "/city start "+ChatColor.GOLD+"Townname Mayor");
            		msg(sender, "Townname"+ChatColor.DARK_GREEN+" - The name of the new town");
            		msg(sender, "Mayor"+ChatColor.DARK_GREEN+" - The name of the player that will be mayor");
                	return false;
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