package com.kteam.bukkit.kapital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class MySQL {
	private final Kapital k_Plugin;

	private Connection MySQLConnection;
	private Statement MySQLStatement;
	@SuppressWarnings("unused")
	private Driver MySQLDriver;
	private String MySQLUser, MySQLPass, MySQLHost, MySQLPort, MySQLDataBase, MySQLURL;

	public MySQL(Kapital instance) {
		k_Plugin = instance;
		k_Plugin.consoleLog("Running database connection...");
		try {
			MySQLUser = KapitalSettings.getString(KapitalSettings.Str.MYSQL_USER);
			MySQLPass = KapitalSettings.getString(KapitalSettings.Str.MYSQL_PASS);
			MySQLHost = KapitalSettings.getString(KapitalSettings.Str.MYSQL_HOST);
			MySQLPort = KapitalSettings.getString(KapitalSettings.Str.MYSQL_PORT);
			MySQLDataBase = KapitalSettings.getString(KapitalSettings.Str.MYSQL_DB);
			MySQLURL = "jdbc:mysql://" + MySQLHost + ":" + MySQLPort + "/" + MySQLDataBase;
			Class.forName("com.mysql.jdbc.Driver");
			MySQLConnection = DriverManager.getConnection(MySQLURL, MySQLUser, MySQLPass);
			MySQLStatement = MySQLConnection.createStatement();
			MySQLConnection.setAutoCommit(true);
		} catch (Exception e) {
			k_Plugin.consoleWarning("MySQL connection failed: " + e.toString());
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
			k_Plugin.consoleWarning("The following statement failed: "
					+ sqlString);
			k_Plugin.consoleWarning("Statement failed: " + e.toString());
		}
	}

	public ResultSet trySelect(String sqlString) {
		try {
			System.out.println(getStatement().toString());
			return getStatement().executeQuery(sqlString);
		} catch (Exception e) {
			k_Plugin.consoleWarning("The following statement failed: "
					+ sqlString);
			k_Plugin.consoleWarning("Statement failed: " + e.toString());
		}
		return null;
	}
}
