package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yflab.model.Humidity;
import com.yflab.util.DbConnector;

public class HumidityDAO {
  private final static String tableName="humidity";

	public static Humidity GetLatestHumidity() throws SQLException {
		

		DbConnector db = new DbConnector();
		Statement statement=null;
		Connection connection=null;
		Humidity ret=new Humidity();
		
		try {
			connection=db.getConnection();
			statement = connection.createStatement();
			String sql = "select * from "+tableName+" ORDER BY date DESC limit 1,1";
			
			ResultSet rs;
			rs=statement.executeQuery(sql);
			rs.next();
			
			ret.setId(rs.getInt(1));
			ret.setValue(rs.getString(2));
			ret.setDate(rs.getString(3));
			ret.setArg0(rs.getString(4));
			ret.setArg1(rs.getString(5));
			
			
		} catch (SQLException e) {
			ret=null;
			e.printStackTrace();
		}
		
		finally
		{
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
		}
		return ret;
	}
}
