package com.kteam.bukkit.utils;

public class Misc {
	public static String arrayToString(String[] a, String separator) {
	    String result = new String();
	    if (a.length > 0) {
	        result = a[0];
	        for (int i=1; i<a.length; i++) {
	            result = result + separator + a[i];
	        }
	    }
	    return result;
	}
}
