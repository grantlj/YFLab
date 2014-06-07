package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yflab.model.Remote;


public class RemoteDAO {
	 private final static String tableName="remote";
	 private static boolean doing=false;
	 
	 public Remote getRemoteState() throws SQLException
	 {
		Remote ret=null;
		
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
			 ret=new Remote();
			 ret.setId(rs.getInt(1));
			 ret.setKeyValue(rs.getInt(2));
			 
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
	 
	 public boolean setRemoteState(int keyValue) throws SQLException, InterruptedException
	 {
		 while (doing)
		 {
			 Thread.sleep(50); 
			 
		 };
		 
		 doing=true;
		 boolean ret=false;
		 
		 DbConnector db = new DbConnector();
		 Statement statement=null;
		 Connection connection=null;
		 
		 try
		 {
			// System.out.println("key value="+keyValue);
			 connection=db.getConnection();
			 statement=connection.createStatement();
			 
			  String sql="update "+tableName+" SET keyvalue=\""+(keyValue)+"\" where id="+(1)+";";
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
		 doing=false;
		 return ret;
	 }
}
