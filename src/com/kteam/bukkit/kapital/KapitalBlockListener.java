package com.kteam.bukkit.kapital;

import java.util.logging.Logger;

import org.bukkit.event.block.BlockListener;

/**
 * Kapital Block Listener
 * 
 * @author Ant59 and Master-Guy
 */

public class KapitalBlockListener extends BlockListener {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
	
    private final Kapital plugin;
    private int I;

    public KapitalBlockListener(Kapital plugin) {
        this.plugin = plugin;
    }
    
    public void debug(String msg) {
    	this.plugin.debug(msg);
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
