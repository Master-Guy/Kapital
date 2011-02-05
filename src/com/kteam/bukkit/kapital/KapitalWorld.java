package com.kteam.bukkit.kapital;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class KapitalWorld extends KapitalObject {
    private final Kapital plugin;
	private HashMap<String, Inhabitant> inhabitants = new HashMap<String, Inhabitant>();
	private HashMap<String, City> cities = new HashMap<String, City>();
	private HashMap<String, Nation> nations = new HashMap<String, Nation>();
    
	public KapitalWorld(Kapital instance) {
		plugin = instance;
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
	    	player.sendMessage(line);
	}
	
	public void msgCity(City city, String[] lines) {
		for (Player player : getOnlinePlayers(city))
			for (String line : lines)
		    	player.sendMessage(line);
	}
	
	public void msgNation(Nation nation, String[] lines) {
		for (Player player : getOnlinePlayers(nation))
			for (String line : lines)
		    	player.sendMessage(line);
	}
	
	public void msgAll(String[] lines) {
		for (Player player : getOnlinePlayers())
			for (String line : lines)
		    	player.sendMessage(line);
	}
    
    public void msgPlayer(Player player, String line) {
    	player.sendMessage(line);
	}
	
	public void msgCity(City city, String line) {
		for (Player player : getOnlinePlayers(city))
			player.sendMessage(line);
	}
	
	public void msgNation(Nation nation, String line) {
		for (Player player : getOnlinePlayers(nation))
	    	player.sendMessage(line);
	}
	
	public void msgAll(String line) {
		for (Player player : getOnlinePlayers())
	    	player.sendMessage(line);
	}
	
	public Boolean startCity(Player founder, String name, String mayorName) {
		try {
			cities.put(name, new City(plugin, founder, name, mayorName));
			return true;
		} catch (Exception e) {
			plugin.consoleWarning(founder + " couldn't create new city \"" + name + "\"!");
			e.printStackTrace();
			return false;
		}
	}
}
