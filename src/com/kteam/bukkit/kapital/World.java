package com.kteam.bukkit.kapital;

import org.bukkit.entity.Player;

public class World extends Object {
    private final Kapital plugin;
    
	public World(Kapital plugin) {
		this.plugin = plugin;
	}
    
    public Player[] getOnlinePlayers() {
		return plugin.getServer().getOnlinePlayers();
	}
    
    public Player[] getOnlinePlayers(City city) {
    	Player inhabitants[];
    	// TODO: List all inhabitants of one city into players[]
    	inhabitants = plugin.getServer().getOnlinePlayers();
		return inhabitants;
	}
    
    public Player[] getOnlinePlayers(Nation nation) {
    	Player citizens[];
    	// TODO: List all citizens of one nation into players[]
    	citizens = plugin.getServer().getOnlinePlayers();
		return citizens;
	}
    
    public void msgPlayer(Player player, String[] lines) {
		for (String line : lines)
			plugin.msg(player, line);
	}
	
	public void msgCity(City city, String[] lines) {
		for (Player player : getOnlinePlayers(city))
			for (String line : lines)
				plugin.msg(player, line);
	}
	
	public void msgNation(Nation nation, String[] lines) {
		for (Player player : getOnlinePlayers(nation))
			for (String line : lines)
				plugin.msg(player, line);
	}
	
	public void msgAll(String[] lines) {
		for (Player player : getOnlinePlayers())
			for (String line : lines)
				plugin.msg(player, line);
	}
	
	
}
