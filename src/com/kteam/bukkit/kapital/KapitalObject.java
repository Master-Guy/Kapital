package com.kteam.bukkit.kapital;

/**
 * Basic standard object class for naming
 *
 * @author Ant59
 */

public abstract class KapitalObject {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}