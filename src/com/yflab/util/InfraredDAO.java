package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.yflab.model.Infrared;

public class InfraredDAO {
	 private final static String tableName="Infrared";
	 
	 public  Infrared getInfraredState() throws SQLException
	 {
		 Infrared ret=null;
		 
		 
		 DbConnector db = new DbConnector();
		 Statement statement=null;
		 Connection connection=null;
		 
		 try
		 {
			 connection=db.getConnection();
			 statement=connection.createStatement();
			 String sql="select * from "+tableName+" order by id DESC;";
			 ResultSet rs;
			 rs=statement.executeQuery(sql);
			 
			
			 rs.next();
			 ret=new Infrared();
			 ret.setId(rs.getInt(1));
			 ret.setState(rs.getString(2));
			 ret.setArg0(rs.getString(3));
			 ret.setArg1(rs.getString(4));
			 
		 }
		 
		 catch (Exception e)
		 {
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
	 
	 public boolean setInfraredState(int InfraredState) throws SQLException
	 {
		 String state=String.valueOf(InfraredState%2);
		 boolean ret=false;
		 
		 DbConnector db = new DbConnector();
		 Statement statement=null;
		 Connection connection=null;
		 try
		 {
			 connection=db.getConnection();
			 statement=connection.createStatement();
			 
		     String sql="update "+tableName+" SET state=\""+(state)+"\" where id="+(1)+";";
		     statement.executeUpdate(sql);
				
			ret=true;
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
			 ret=false;
			 
		 }
		 
		 finally
		 {
			 if (statement!=null) statement.close();
			 if (connection!=null) connection.close();
		 }
		return ret;
		 
	 }
}
