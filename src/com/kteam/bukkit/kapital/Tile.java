package com.kteam.bukkit.kapital;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerChatEvent;
/**
 * Land Tile
 * 
 * @author Ant59 and Master-Guy
 */

public class Tile {
	//private int x, z;
	//private City city; //Where does the tile belong to?

    private final Kapital plugin;
    
	public Tile(Kapital instance) {
		this.plugin = instance;
	}
	
	private HashMap<String, String> plots = new HashMap<String, String>();
	private Integer plotsBought = 0;
	private Location playerLoc;
	private Chunk plot;
	private Block topBlock;
	
	/* I think the these two methods should be in the Tile, rather than plot, since Tile is the chunk handing class.
	 * Plot was meant to be a scalable area, set by mayors as purchasable land within a City.
	 * 
	 * Up to you if you want to change it though :)
	 * 
	 * -Ant59
	 * 
	 * Move it to the tile class
	 * -MG
	 */
	public void buyTile(PlayerChatEvent event) {
		event.getPlayer().sendMessage("Buying plot");
		plots.put("plot"+plotsBought+"_owner", event.getPlayer().getName());
		playerLoc = event.getPlayer().getLocation();
		plots.put("plot"+plotsBought+"_pox_x", ""+playerLoc.getWorld().getChunkAt(playerLoc.getBlockX(), playerLoc.getBlockZ()).getX());
		plots.put("plot"+plotsBought+"_pox_z", ""+playerLoc.getWorld().getChunkAt(playerLoc.getBlockX(), playerLoc.getBlockZ()).getZ());
		plot = this.plugin.getServer().getWorlds()[0].getChunkAt(Integer.parseInt(plots.get("plot"+plotsBought+"_pox_x")), Integer.parseInt(plots.get("plot"+plotsBought+"_pox_z")));
		plotsBought = plotsBought + 1;
	}
	
	public void displayTileXZ(PlayerChatEvent event) {
		playerLoc = event.getPlayer().getLocation();
		topBlock = this.plugin.getServer().getWorlds()[0].getBlockAt(playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ());
		plot = this.plugin.getServer().getWorlds()[0].getChunkAt(topBlock);
		event.getPlayer().sendMessage("Your plot is at: X"+plot.getX()+"/Z"+plot.getZ());
	}
}
