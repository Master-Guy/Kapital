package com.bukkit;

import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Master-Guy
 */

public class KapitalPlayerListener extends PlayerListener {
    private final Kapital plugin;

    public KapitalPlayerListener(Kapital instance) {
        plugin = instance;
    }
    
    public void log(String logText) {
    	System.out.println(logText);
    }
}
