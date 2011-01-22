package com.kteam.bukkit.kapital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import com.mysql.jdbc.Driver;

public class MySQL {
	/*
	 * Hostname: masterductions.com
	 * Username: Kapital
	 * Password: GVWshLL48b4FxJEf
	 * Database: Kapital
	 */
	

	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    private final Kapital plugin;
    private KapitalSettings Settings = new KapitalSettings();
    
	private Connection MySQLConnection;
	private Statement MySQLStatement;
	@SuppressWarnings("unused")
	private Driver MySQLDriver;
	private String MySQLUser, MySQLPass, MySQLHost, MySQLPort, MySQLDataBase, MySQLURL;
	
	public MySQL(Kapital plugin) {
		this.plugin = plugin;
		plugin.consoleLog("Loading MySQL");
		try {
			MySQLUser = Settings.getSetting("settings/kapital.ini", "mysql_user", "root")[0];
			MySQLPass = Settings.getSetting("settings/kapital.ini", "mysql_pass", "passwd")[0];
			MySQLHost = Settings.getSetting("settings/kapital.ini", "mysql_host", "localhost")[0];
			MySQLPort = Settings.getSetting("settings/kapital.ini", "mysql_port", "3306")[0];
			MySQLDataBase = Settings.getSetting("settings/kapital.ini", "mysql_db", "minecraft")[0];
			MySQLURL = "jdbc:mysql://"+MySQLHost+":"+MySQLPort+"/"+MySQLDataBase;
			Class.forName("com.mysql.jdbc.Driver");
			MySQLConnection = DriverManager.getConnection(MySQLURL, MySQLUser, MySQLPass);
			MySQLStatement = MySQLConnection.createStatement();
			MySQLConnection.setAutoCommit(true);
		} catch (Exception e) {
			plugin.consoleWarning("MySQL connection failed: "+e.toString());
		} finally {
		}
	}
	
	public Connection getConnection() {
		return MySQLConnection;
	}
	
	public Statement getStatement() {
		return MySQLStatement;
	}
	
	public void tryUpdate(String sqlString) {
		try {
			getStatement().executeUpdate(sqlString);
    	} catch (Exception e) {
    		plugin.consoleWarning("The following statement failed: "+sqlString);
    		plugin.consoleWarning("Statement failed: "+e.toString());
    	} finally {}	
	}
	
	public ResultSet trySelect(String sqlString) {
		try {
			System.out.println(getStatement().toString());
			return getStatement().executeQuery(sqlString);
    	} catch (Exception e) {
    		plugin.consoleWarning("The following statement failed: "+sqlString);
    		plugin.consoleWarning("Statement failed: "+e.toString());
    	} finally {}
    	return null;
	}
}
