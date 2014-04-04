package com.yflab.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

	private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String userName = "root";
    private static final String userPwd = "940414";
    private static final String dbName = "lab";
    private static final String url = "jdbc:mysql://localhost:3306/";

	private Connection connection = null;

	private void createConnection() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		Class.forName(driverName).newInstance();
		connection = DriverManager.getConnection(url + dbName, userName,
				userPwd);

	}

	public Connection getConnection()

	{
		if (connection == null)
			try {
				createConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return connection;

	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection = null;
		}

	}
}

