package com.kteam.bukkit.kapital;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Coordinate handler
 * Based-off Towny coord.java
 * 
 * @author Ant59
 */

public class Coord {
	private static int tileSize = 16;
	private int x, z;
	
	public Coord(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + z;
		return result;
	}
	
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Coord))
			return false;
		
		Coord o = (Coord)obj;
		return this.x == o.x
			&& this.z == o.z;
	}
	
	public static Coord parseCoord(int x, int z) {
		return new Coord(
				x / getTileSize() - (x < 0 ? 1 : 0),
				z / getTileSize() - (z < 0 ? 1 : 0));
	}
	
	public static Coord parseCoord(Player player) {
		return parseCoord(player.getLocation());
	}
	
	public static Coord parseCoord(Location loc) {
		return parseCoord(loc.getBlockX(), loc.getBlockZ());
	}
	
	public static Coord parseCoord(Block block) {
		return parseCoord(block.getX(), block.getZ());
	}

	public static void setTileSize(int tileSize) {
		Coord.tileSize = tileSize;
	}

	public static int getTileSize() {
		return tileSize;
	}
}
