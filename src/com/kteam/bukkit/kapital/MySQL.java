package com.kteam.bukkit.kapital;

import java.util.logging.Logger;

import java.sql.*;
import com.mysql.jdbc.Driver;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class MySQL {
	/*
	 * Hostname: masterductions.com
	 * Username: Kapital
	 * Password: GVWshLL48b4FxJEf
	 * Database: Kapital
	 */
	

	public static final Logger log = Logger.getLogger("Minecraft"); // Logger
    private Settings Settings = new Settings();
	private Connection MySQLConnection;
	private Statement MySQLStatement;
	@SuppressWarnings("unused")
	private Driver MySQLDriver;
	private String MySQLUser, MySQLPass, MySQLHost, MySQLPort, MySQLDataBase, MySQLURL;
	
	public MySQL() {
		log.info("MySQL for Kapital loading");
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
		} catch (Exception e) {
    		log.warning("MySQL connection failed: "+e.toString());
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
    		log.warning("The following statement failed: "+sqlString);
    		log.warning("Statement failed: "+e.toString());
    	} finally {}	
	}
}
