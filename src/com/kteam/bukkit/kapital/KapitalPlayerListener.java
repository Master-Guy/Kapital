package com.kteam.bukkit.kapital;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import com.kteam.bukkit.utils.Misc;

/**
 * Kapital Player Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalPlayerListener extends PlayerListener {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    private final KapitalSettings Settings = new KapitalSettings();
    
    private final Kapital plugin;
    //private final Tile Tile;

    public KapitalPlayerListener(Kapital plugin) {
        this.plugin = plugin;
        //Tile = new Tile(plugin);
    }
    
    	// Old code
    	/*if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "buyPlotCommand", "/buyTile")[0])) {
    		event.getPlayer().sendMessage("Executing buyPlot");
    		Tile.buyTile(event);
    		event.setCancelled(true);
    	}
    	else if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "showPlotXZ", "/tileXZ")[0])) {
    		event.getPlayer().sendMessage("Executing plotXZ");
    		Tile.displayTileXZ(event);
    		event.setCancelled(true);
    	}*/
}
