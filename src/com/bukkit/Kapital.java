package com.bukkit;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MG for Bukkit
 *
 * @author Master-Guy
 */

public class Kapital extends JavaPlugin {
    private final KapitalPlayerListener playerListener = new KapitalPlayerListener(this);
    private final KapitalBlockListener blockListener = new KapitalBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    public Kapital(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        log("Kapital loaded!");
    }
    
    public void onDisable() {
        System.out.println("Goodbye world!");
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
    
    public void log(String logText) {
    	System.out.println(logText);
    }
}
