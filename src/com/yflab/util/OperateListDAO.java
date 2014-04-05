package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class OperateListDAO {
	private final static String tableName="operatelist";
	
	public static void addViewLog(String ip, String type) throws SQLException {
		// TODO Auto-generated method stub
		DbConnector db = new DbConnector();
		Statement statement=null;
		Connection connection=null;
		
		try {
			connection=db.getConnection();
			statement = connection.createStatement();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			java.util.Date date=new java.util.Date();  
			
			String dateStr=sdf.format(date); 
			
			String sql0="select count(*) from "+tableName;
			ResultSet rs=statement.executeQuery(sql0);
			rs.first();
			int id=rs.getInt(1)+1;
			
			System.out.println("No."+id+" visit @ "+ip+" @"+dateStr);
		
			String sql = "insert into "+tableName+" values("+id+","+"\""+type+"\",\""+ip+"\","+"\"\""+",\"\",\""+dateStr+"\")";
			//System.out.println(sql);
			statement.execute(sql);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		finally
		{
			if (statement!=null) statement.close();
			if (connection!=null) connection.close();
		}
	}

}
