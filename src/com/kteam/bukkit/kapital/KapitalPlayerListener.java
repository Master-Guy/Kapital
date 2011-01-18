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
	// Logger
	public static final Logger log = Logger.getLogger("Minecraft");
    private final Settings Settings = new Settings();
    //private final Kapital plugin;
    private final Plot Plot;

    public KapitalPlayerListener(Kapital instance) {
        //plugin = instance;
        Plot = new Plot(instance);
    }


    public void onPlayerCommand(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
    	if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "buyPlotCommand", "/buyPlot")[0])) {
    		event.getPlayer().sendMessage("Executing buyPlot");
    		Plot.buyPlot(event);
    		event.setCancelled(true);
    	}
    	if (split[0].equalsIgnoreCase(Settings.getSetting("settings/kapital.ini", "showPlotXZ", "/plotXZ")[0])) {
    		event.getPlayer().sendMessage("Executing plotXZ");
    		Plot.displayPlotXZ(event);
    		event.setCancelled(true);
    	}
    	event.getPlayer().sendMessage("Command entered: "+split[0]);
		event.setCancelled(true);
    }
}
