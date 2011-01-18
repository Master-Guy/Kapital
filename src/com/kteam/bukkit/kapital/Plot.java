package com.kteam.bukkit.kapital;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerChatEvent;


/**
 * Land Plot
 * Plots of land are designated areas by the city that citizens can buy
 * 
 * @author antony
 */
public class Plot {
	// Logger
	public static final Logger log = Logger.getLogger("Minecraft");
	
    private final Kapital plugin;
	public Plot(Kapital instance) {
		this.plugin = instance;
	}
	
	private HashMap<String, String> plots = new HashMap<String, String>();
	private Integer plotsBought = 0;
	private Location playerLoc;
//	private City city; //Where does the plot belong to?
	private Chunk plot;
	private Block topBlock;
	
	public void buyPlot(PlayerChatEvent event) {
		event.getPlayer().sendMessage("Buying plot");
		plots.put("plot"+plotsBought+"_owner", event.getPlayer().getName());
		playerLoc = event.getPlayer().getLocation();
		plots.put("plot"+plotsBought+"_pox_x", ""+playerLoc.getWorld().getChunkAt(playerLoc.getBlockX(), playerLoc.getBlockZ()).getX());
		plots.put("plot"+plotsBought+"_pox_z", ""+playerLoc.getWorld().getChunkAt(playerLoc.getBlockX(), playerLoc.getBlockZ()).getZ());
		plot = this.plugin.getServer().getWorlds()[0].getChunkAt(Integer.parseInt(plots.get("plot"+plotsBought+"_pox_x")), Integer.parseInt(plots.get("plot"+plotsBought+"_pox_z")));
		plotsBought = plotsBought + 1;
	}
	
	public void displayPlotXZ(PlayerChatEvent event) {
		playerLoc = event.getPlayer().getLocation();
		topBlock = this.plugin.getServer().getWorlds()[0].getBlockAt(playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ());
		plot = this.plugin.getServer().getWorlds()[0].getChunkAt(topBlock);
		event.getPlayer().sendMessage("Your plot is at: X"+plot.getX()+"/Z"+plot.getZ());
	}
}
