package com.kteam.bukkit.kapital;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Kapital for Bukkit
 *
 * @author Ant59 and Master-Guy
 */

public class Kapital extends JavaPlugin {
	// Logger
	public static final Logger log = Logger.getLogger("Minecraft");
	
    private final KapitalPlayerListener playerListener = new KapitalPlayerListener(this);
//    private final KapitalBlockListener blockListener = new KapitalBlockListener(this);
//    private final Settings Settings = new Settings();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public static String name = "Kapital";
    public static String version = "0.0.1";

    public Kapital(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    	log.info("[" + name + "] v" + version + " - Loaded and Enabled");
    }
    
    public void onDisable() {
    	log.info("[" + name + "] v" + version + " - Disabled");
    }
    
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
