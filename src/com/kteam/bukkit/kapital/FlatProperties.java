package com.kteam.bukkit.kapital;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Properties file object
 *
 * @author Ant59 and Master-Guy
 */

public class FlatProperties {
	private static final Logger log = Logger.getLogger("Minecraft");
    private Properties properties;
    private String name;

    public FlatProperties(String name) {
		this.name = name;
		this.properties = new Properties();
		File file = new File(name);
	
		if (!file.exists())
			try {
				this.properties.store(new FileOutputStream(this.name), "Kapital Properties File");
			} catch (IOException e) {
				log.log(Level.SEVERE, "Loading '" + this.name + "' failed!", e);
			}
    }
}
