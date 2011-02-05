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
    private final Kapital k_Plugin;
    private final KapitalWorld kapitalWorld;
    private HashMap<String, Integer> cityLevels = new HashMap<String, Integer>();
    private MySQL mysql;

    public Player mayor;
    public Integer mayorId;
    
	public City(Kapital instance, Player founder, String cityName, String mayorName) {
		this.k_Plugin = instance;
		this.kapitalWorld = k_Plugin.getKapitalWorld();
		mysql = new MySQL(k_Plugin);
	    cityLevels.put("inhabitant", 1);
	    cityLevels.put("council", 2);
	    cityLevels.put("mayor", 3);
		startCity(founder, cityName, mayorName);
	};
    
    public void debug(String msg) {
    	this.k_Plugin.debug(msg);
    }
    
    public int checkPlayer(String playerName) {
    	ResultSet rs;
    	mysql.tryUpdate("insert ignore into kapital__players (name) values ('"+playerName+"')");
    	try {
    		rs = mysql.trySelect("select id from kapital__players where name = '"+playerName+"'");
    		rs.next();
    		return rs.getInt("id");
    	} catch (Exception e) {
    		k_Plugin.consoleWarning("checkPlayer failed: "+e.toString());
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
		debug("Starting city "+newCityName+" for "+newMayorName);
		City city;
		city = this;
		Player newMayor;
		ResultSet rs;
		newMayor = null;
		newMayorIsOnline = false;
		debug("Checking if the mayor is online");
		for (int i = 0; i < kapitalWorld.getOnlinePlayers().length; i++) {
			if(kapitalWorld.getOnlinePlayers()[i].getName().toString().equalsIgnoreCase(newMayorName)) {
				newMayorIsOnline = true;
				newMayor = kapitalWorld.getOnlinePlayers()[i];
			}
		}
		if (newMayorIsOnline) {
			debug("Mayor is online");
			try {
				debug("Counting cities where "+newMayorName+" is mayor");
				rs = mysql.trySelect("select count(*) cnt from kapital__cities c where mayor = '"+newMayor.getName()+"'");
				rs.next();
				debug("Cities where "+newMayorName+" is mayor: "+rs.getInt("cnt"));
				if (rs.getInt("cnt") > 0) {
					k_Plugin.msg(ply, newMayor.getName()+" is already mayor of a city!");
					newMayor.sendMessage(ply.getName()+" tried to start a city for you, but you are mayor already of a city!");
					return null;
				} else {
					debug("Creating city");
					Chunk tile = this.k_Plugin.getServer().getWorlds()[0].getChunkAt(newMayor.getLocation().getBlockX(), newMayor.getLocation().getBlockZ());
					rs = mysql.trySelect("select count(*) cnt from kapital__tiles t where x = "+tile.getX()+" and z = "+tile.getZ());
					rs.next();
					debug("Number of cities on this tile: "+rs.getInt("cnt"));
					if(rs.getInt("cnt") > 0) {
						debug("Tile is already taken");
						k_Plugin.msg(ply, "The location at which "+newMayor.getName()+" is standing, is already taken!");
						newMayor.sendMessage(ply.getName()+" tried to start a city for you, but the location you are standing is taken already!");
						return null;
					} else {
						debug("Tile is free, reserving for the new city");
						mysql.tryUpdate("insert into kapital__cities (name, mayor) values('"+newCityName+"', '"+newMayor.getName()+"')");
						debug("Created the city");
						rs = mysql.trySelect("select id from kapital__cities c where mayor = '"+newMayor.getName()+"'");
						debug("Selected the city ID");
						rs.next();
						mysql.tryUpdate("insert into kapital__player_cities (city_id, player_id, player_level) values("+rs.getInt("id")+", "+checkPlayer(newMayor.getName())+", "+cityLevels.get("mayor")+")");
						debug("Assigned player to the city");
						mysql.tryUpdate("insert into kapital__tiles (x, z, city_id) values("+tile.getX()+", "+tile.getZ()+", "+rs.getInt("id")+")");
						debug("Assigned tile to the city");
						newMayor.sendMessage("The city "+newCityName+" has been created at your current location. Type '/city help' for more information.");
						k_Plugin.msg(ply, "The city "+newCityName+" has been created with "+newMayor.getName()+" as mayor");
						this.setMayor(newMayor);
						debug("City creation completed");
						return city;
					}
				}
			} catch (Exception e) {
	    		k_Plugin.consoleWarning("startCity failed: "+e.toString());
	    		e.printStackTrace();
			}
		} else {
			debug("Mayor is not online");
			k_Plugin.msg(ply, newMayor.getName()+" is not online!");
		}
		return city;
	}
}
