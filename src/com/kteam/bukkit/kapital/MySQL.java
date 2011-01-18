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
			log.info("t1");
			MySQLConnection = DriverManager.getConnection(MySQLURL, MySQLUser, MySQLPass);
			log.info("t2");
			MySQLStatement = MySQLConnection.createStatement();
			log.info("t3");
			MySQLStatement.executeUpdate("CREATE TABLE IF NOT EXISTS `kapital__tiles` ( `id` int(11) NOT NULL auto_increment, `x` int(11) NOT NULL, `z` int(11) NOT NULL, `owner` varchar(30) NOT NULL, `welcome` varchar(250) default NULL, `farewell` varchar(250) default NULL, PRIMARY KEY  (`id`))");
			log.info("t4");
		} catch (Exception e) {
    		log.warning("Create statement failed: "+e.toString());
		} finally {
			log.info("MySQL for Kapital loaded");
		}
	}
	
	public Connection getConnection() {
		return MySQLConnection;
	}
	
	public Statement getStatement() {
		return MySQLStatement;
	}
}
