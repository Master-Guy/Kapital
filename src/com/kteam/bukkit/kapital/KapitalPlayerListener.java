package com.kteam.bukkit.kapital;

import java.util.logging.Logger;
import org.bukkit.entity.Player;
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
    
    private final Kapital plugin;
    private final Tile Tile;

    public KapitalPlayerListener(Kapital plugin) {
        this.plugin = plugin;
        Tile = new Tile(plugin);
    }


    public void onPlayerCommand(PlayerChatEvent event) {
    	if (event.isCancelled())
			return;
    	
    	Player player = event.getPlayer();
    	
    	String[] command = event.getMessage().split(" ");
    	String[] args = new String[command.length - 1];
		System.arraycopy(command, 1, args, 0, command.length - 1);
    	
    	if (command[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "buyPlotCommand", "/buyTile")[0])) {
    		event.getPlayer().sendMessage("Executing buyPlot");
    		Tile.buyTile(event);
    		event.setCancelled(true);
    	}
    	else if (command[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "showPlotXZ", "/tileXZ")[0])) {
    		event.getPlayer().sendMessage("Executing plotXZ");
    		Tile.displayTileXZ(event);
    		event.setCancelled(true);
    	}
    	else if (command[0].equalsIgnoreCase("/msgall")) {
    		plugin.getWorld().msgAll(args);
    		event.setCancelled(true);
    	}
    	else
    		return;
    	
    	//event.getPlayer().sendMessage("Command entered: "+split[0]);
		event.setCancelled(true);
    }
}
