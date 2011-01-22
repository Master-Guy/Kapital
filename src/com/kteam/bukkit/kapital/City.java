package com.kteam.bukkit.kapital;
import java.sql.ResultSet;
import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

/**
 * City
 * 
 * @author Ant59 and Master-Guy
 */

public class City {
    private final Kapital plugin;
    private HashMap<String, Integer> cityLevels = new HashMap<String, Integer>();
    
	public City(Kapital plugin) {
		this.plugin = plugin;
	    cityLevels.put("inhabitant", 1);
	    cityLevels.put("council", 2);
	    cityLevels.put("mayor", 3);
	}
    private final MySQL MySQL = new MySQL(this.plugin);
    
    public int checkPlayer(String playerName) {
    	MySQL.tryUpdate("insert ignore into kapital__players (name) values ('"+playerName+"')");
    	try {
    		return MySQL.trySelect("select id from kapital__players where name = '"+playerName+"'").getInt("id");
    	} catch (Exception e) {
    		plugin.consoleWarning("checkPlayer failed: "+e.toString());
    	}
    	return -1;
    }
	
	public boolean startCity(Player ply, String newCityName, String newMayorName) {
		boolean newMayorIsOnline;
		Player newMayor;
		ResultSet rs;
		newMayor = null;
		newMayorIsOnline = false;
		for (int i = 0; i < this.plugin.getServer().getOnlinePlayers().length; i++) {
			if(this.plugin.getServer().getOnlinePlayers()[i].getName().toString().equalsIgnoreCase(newMayorName)) {
				newMayorIsOnline = true;
				newMayor = this.plugin.getServer().getOnlinePlayers()[i];
			}
		}
		if (newMayorIsOnline) {
			rs = MySQL.trySelect("select count(*) cnt from kapital__cities c where mayor = '"+newMayor.getName()+"'");
			try {
				if (rs.getInt("cnt") > 0) {
					ply.sendMessage(newMayor.getName()+" is already mayor of a city!");
					newMayor.sendMessage(ply.getName()+" tried to start a city for you, but you are mayor already of a city!");
					return false;
				} else {
					Chunk tile = this.plugin.getServer().getWorlds()[0].getChunkAt(newMayor.getLocation().getBlockX(), newMayor.getLocation().getBlockZ());
					rs = MySQL.trySelect("select count(*) cnt from kapital__tiles t where x = "+tile.getX()+" and z = "+tile.getZ());
					if(rs.getInt("cnt") > 0) {
						ply.sendMessage("The location at which "+newMayor.getName()+" is standing, is already taken!");
						newMayor.sendMessage(ply.getName()+" tried to start a city for you, but the location you are standing is taken already!");
						return false;
					} else {
						MySQL.tryUpdate("insert into kapital__cities (name, mayor) values('"+newCityName+"', '"+newMayor.getName()+"')");
						rs = MySQL.trySelect("select id from kapital__cities c where mayor = '"+newMayor.getName()+"'");
						MySQL.tryUpdate("insert into kapital__player_cities (city_id, player_id, player_level) values("+rs.getInt("id")+", "+checkPlayer(newMayor.getName())+", "+cityLevels.get("mayor")+")");
						newMayor.sendMessage("The city "+newCityName+" has been created at your current location. Type '/city help' for more information.");
						ply.sendMessage("The city "+newCityName+" has been created with "+newMayor.getName()+" as mayor");
					}
				}
			} catch (Exception e) {
	    		plugin.consoleWarning("buyCity failed: "+e.toString());
			}
		}
		return false;
	}
}
