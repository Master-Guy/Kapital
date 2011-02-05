package com.kteam.bukkit.kapital;

public class Inhabitant extends KapitalObject {
    private final Kapital k_Plugin;
    
	public Inhabitant(Kapital instance) {
		k_Plugin = instance;
	}
    
    public void debug(String msg) {
    	this.k_Plugin.debug(msg);
    }
}
