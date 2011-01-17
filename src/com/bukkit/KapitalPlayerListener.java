package com.bukkit;

import java.util.logging.Logger;

import org.bukkit.event.player.PlayerListener;

/**
 * Kapital Payer Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalPlayerListener extends PlayerListener {
	// Logger
	public static final Logger log = Logger.getLogger("Minecraft");
	
    private final Kapital plugin;

    public KapitalPlayerListener(Kapital instance) {
        plugin = instance;
    }
}
