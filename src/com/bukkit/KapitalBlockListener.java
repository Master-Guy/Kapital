package com.bukkit;

import org.bukkit.event.block.BlockListener;

/**
 * Kapital Block Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalBlockListener extends BlockListener {
    private final Kapital plugin;
    private int I;

    public KapitalBlockListener(final Kapital plugin) {
        this.plugin = plugin;
    }

    public void log(String logText) {
    	System.out.println(logText);
    }
    
    public void logToAll(String logText) {
    	System.out.println(logText);
    	I = 0;
    	while (I < this.plugin.getServer().getOnlinePlayers().length) {
    		this.plugin.getServer().getOnlinePlayers()[I].sendMessage(logText);
    		I = I + 1;
    	}
    }
}
