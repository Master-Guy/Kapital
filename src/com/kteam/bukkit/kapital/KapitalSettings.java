package com.kteam.bukkit.kapital;

import java.util.concurrent.ConcurrentHashMap;

public class KapitalSettings {
	
	private static final ConcurrentHashMap<KapitalSettings.Str,String> propStrings = new ConcurrentHashMap<KapitalSettings.Str,String>();
	private static final ConcurrentHashMap<KapitalSettings.Int,Integer> propInts = new ConcurrentHashMap<KapitalSettings.Int,Integer>();
	private static final ConcurrentHashMap<KapitalSettings.Bool,Boolean> propBools = new ConcurrentHashMap<KapitalSettings.Bool,Boolean>();
	
	// String
	enum Str {
		MYSQL_HOST,
		MYSQL_PORT,
		MYSQL_USER,
		MYSQL_PASS,
		MYSQL_DB
	};
	
	// Integer
	enum Int {
	};
	
	// Boolean
	enum Bool {
	};
	
	// LOAD DEFAULTS
	static {
		// String
		propStrings.put(KapitalSettings.Str.MYSQL_HOST, "localhost");
		propStrings.put(KapitalSettings.Str.MYSQL_PORT, "3306");
		propStrings.put(KapitalSettings.Str.MYSQL_USER, "user");
		propStrings.put(KapitalSettings.Str.MYSQL_PASS, "pass");
		propStrings.put(KapitalSettings.Str.MYSQL_DB, "minecraft");
	}

	public static boolean loadConfig() {
		try {
			String propertiesFile = Kapital.k_Folder + "/config.properties";
			KapitalProperties properties = new KapitalProperties(propertiesFile);
			properties.load();
			
			for (KapitalSettings.Str key : KapitalSettings.Str.values())
				propStrings.put(key, properties.getString(key.toString().toLowerCase(), getString(key)));
			for (KapitalSettings.Int key : KapitalSettings.Int.values())
				propInts.put(key, properties.getInt(key.toString().toLowerCase(), getInt(key)));
			for (KapitalSettings.Bool key : KapitalSettings.Bool.values())
				propBools.put(key, properties.getBoolean(key.toString().toLowerCase(), getBoolean(key)));
			
			properties.save("Kapital Main Configuration File");
		} catch (Exception e) {
			System.out.println("Failed to load configuration");
			return false;			
		}
		return true;
	}

	// Basic retrieval functions
	public static String getString(KapitalSettings.Str key) {
		return propStrings.get(key);
	}

	public static Integer getInt(KapitalSettings.Int key) {
		return propInts.get(key);
	}

	public static Boolean getBoolean(KapitalSettings.Bool key) {
		return propBools.get(key);
	}
	
	// Dedicated settings retrieval functions

}

/*
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Logger;

public class KapitalSettings {
	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
	private String curLine; // A line in the file
	
    public void testFolderExists(String folder) {
    	boolean success = (new File(folder)).mkdirs();
        if (success) {
        	log.info("Directory: " + folder + " created");
        }
    }
    public void testFileExists(String fileName) {
    	boolean success = (new File(fileName)).isFile();
    	if(!success) {
    		success = false;
    		try {
    			success = (new File(fileName)).createNewFile();
    		} catch (Exception e) {
    		} finally {
    	        if (success) {
    	        	log.info("File: " + fileName + " created");
    	        }
    		}
    	}
    }

    public String[] getSetting(String fileName, String optionName, String defaultValue) {
    	return getSetting(fileName, optionName, defaultValue, "");
    }

    public String[] getSetting(String fileName, String optionName, String defaultValue, String splitValue) {
    	Boolean gotLine; // Verification variable
    	String[] returnValue = new String[100]; // Settings max at 100 values

    	gotLine = false; // We don't have the settings found yet
    	testFileExists(fileName); // Test if the file exists
    	
		if(splitValue == "") {
			splitValue = "afs4wfa3waawfa3dogrsijkge5ssioeguhwar3awwa3rar";
		}
    	
        try {
        	// Get the line from the file
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
			while(in.ready()) {
				curLine = in.readLine().toString();
				if(curLine.split("=", 2)[0].equalsIgnoreCase(optionName)) {
					returnValue = new String[100];
					returnValue = curLine.split("=", 2)[1].split(splitValue);
					gotLine = true;
				}
			}
			in.close();
			
			// If the line does not exist, create it
			if(!gotLine) {
                returnValue = defaultValue.split(splitValue);
                FileOutputStream out;
                PrintStream p;
                try {
	                out = new FileOutputStream(fileName, true);
	                p = new PrintStream( out );
	                p.println (optionName+"="+defaultValue);
	                p.close();
                } catch (Exception e) {
                	log.warning("Error writing to file");
                }
			}
		}
        catch (Exception e) {
			log.info("-=-");
			log.info("File input error: "+e.toString());
			log.info("File input error: "+e.getStackTrace().toString());
			log.info("-=-");
		}
		finally {
		}
		return returnValue;
    }
}
*/