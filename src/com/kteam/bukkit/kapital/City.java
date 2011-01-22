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
    private MySQL mysql;

    public Player mayor;
    public Integer mayorId;
    
	public City(Kapital plugin, Player founder, String cityName, String mayorName) {
		this.plugin = plugin;
		mysql = new MySQL(plugin);
	    cityLevels.put("inhabitant", 1);
	    cityLevels.put("council", 2);
	    cityLevels.put("mayor", 3);
		startCity(founder, cityName, mayorName);
	};
	
    
    public int checkPlayer(String playerName) {
    	ResultSet rs;
    	mysql.tryUpdate("insert ignore into kapital__players (name) values ('"+playerName+"')");
    	try {
    		rs = mysql.trySelect("select id from kapital__players where name = '"+playerName+"'");
    		rs.next();
    		return rs.getInt("id");
    	} catch (Exception e) {
    		plugin.consoleWarning("checkPlayer failed: "+e.toString());
    	}
    	return -1;
    }
    
    public void setMayor(Player newMayor) {
    	this.mayor = newMayor;
    	this.mayorId = checkPlayer(newMayor.getName());
    }
    public int getMayorId() {
    	return this.mayorId;
    }
    public Player getMayor() {
    	return this.mayor;
    }
	
	public City startCity(Player ply, String newCityName, String newMayorName) {
		boolean newMayorIsOnline;
		City city;
		city = this;
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
			try {
				rs = mysql.trySelect("select count(*) cnt from kapital__cities c where mayor = '"+newMayor.getName()+"'");
				rs.next();
				if (rs.getInt("cnt") > 0) {
					ply.sendMessage(newMayor.getName()+" is already mayor of a city!");
					newMayor.sendMessage(ply.getName()+" tried to start a city for you, but you are mayor already of a city!");
					return null;
				} else {
					Chunk tile = this.plugin.getServer().getWorlds()[0].getChunkAt(newMayor.getLocation().getBlockX(), newMayor.getLocation().getBlockZ());
					rs = mysql.trySelect("select count(*) cnt from kapital__tiles t where x = "+tile.getX()+" and z = "+tile.getZ());
					rs.next();
					if(rs.getInt("cnt") > 0) {
						ply.sendMessage("The location at which "+newMayor.getName()+" is standing, is already taken!");
						newMayor.sendMessage(ply.getName()+" tried to start a city for you, but the location you are standing is taken already!");
						return null;
					} else {
						mysql.tryUpdate("insert into kapital__cities (name, mayor) values('"+newCityName+"', '"+newMayor.getName()+"')");
						rs = mysql.trySelect("select id from kapital__cities c where mayor = '"+newMayor.getName()+"'");
						rs.next();
						mysql.tryUpdate("insert into kapital__player_cities (city_id, player_id, player_level) values("+rs.getInt("id")+", "+checkPlayer(newMayor.getName())+", "+cityLevels.get("mayor")+")");
						mysql.tryUpdate("insert into kapital__tiles (x, z, city_id) values("+tile.getX()+", "+tile.getZ()+", "+rs.getInt("id")+")");
						newMayor.sendMessage("The city "+newCityName+" has been created at your current location. Type '/city help' for more information.");
						ply.sendMessage("The city "+newCityName+" has been created with "+newMayor.getName()+" as mayor");
						this.setMayor(newMayor);
						return city;
					}
				}
			} catch (Exception e) {
	    		plugin.consoleWarning("buyCity failed: "+e.toString());
			}
		}
		return city;
	}
}
