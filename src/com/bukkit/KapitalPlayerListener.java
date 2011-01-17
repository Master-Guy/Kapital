package com.bukkit;

import org.bukkit.event.player.PlayerListener;

/**
 * Kapital Payer Listener
 * 
 * @author Ant59 and Master-Guy
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
