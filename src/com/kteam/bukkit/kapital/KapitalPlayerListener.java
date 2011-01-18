package com.kteam.bukkit.kapital;

import java.util.logging.Logger;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Kapital Player Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalPlayerListener extends PlayerListener {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    private final Settings Settings = new Settings();
    //private final Kapital plugin;
    private final Tile Tile;

    public KapitalPlayerListener(Kapital instance) {
        //plugin = instance;
        Tile = new Tile(instance);
    }


    public void onPlayerCommand(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
    	if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "buyPlotCommand", "/buyTile")[0])) {
    		event.getPlayer().sendMessage("Executing buyPlot");
    		Tile.buyTile(event);
    		event.setCancelled(true);
    	}
    	if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "showPlotXZ", "/tileXZ")[0])) {
    		event.getPlayer().sendMessage("Executing plotXZ");
    		Tile.displayTileXZ(event);
    		event.setCancelled(true);
    	}
    	event.getPlayer().sendMessage("Command entered: "+split[0]);
		event.setCancelled(true);
    }
}
