package com.kteam.bukkit.kapital;

/**
 * Land Plot
 * Plots of land are designated areas by the city that citizens can buy
 * 
 * @author Ant59 and Master-Guy
 */
public class Plot {
//	private City city; //Where does the plot belong to?
    private final Kapital k_Plugin;
    
	public Plot(Kapital instance) {
		k_Plugin = instance;
	}
    
    public void debug(String msg) {
    	this.k_Plugin.debug(msg);
    }
	
}
