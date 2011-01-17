package com.kteam.bukkit.kapital;

import org.bukkit.Location;

public class Tile {
    double x1, x2, y1, y2;
    String name;
    
    public Tile() {
        x1 = 0;
        x2 = 0;
        y1 = 0;
        y2 = 0;
    }
    
    public Tile(Location a, Location b) {
        this(a, b, "");
    }
    
    public Tile(Location a, Location b, String name) {
        this.name = name;
        
        if (a.getX() < b.getX()) {
            x1 = a.getX();
            x2 = b.getX();
        } else {
            x1 = b.getX();
            x2 = a.getX();
        }
        if (a.getY() < b.getY()) {
            y1 = a.getY();
            y2 = b.getY();
        } else {
            y1 = b.getY();
            y2 = a.getY();
        }
    }
    
    public String toString() {
        return name+"["+x1+","+y1+"]["+x2+","+y2+"]";
    }
    
    public boolean inside(Location p) {
        if (x1 < p.getX() && p.getX() < x2 && y1 < p.getY() && p.getY() < y2)
            return true;
        return false;
    }
}
