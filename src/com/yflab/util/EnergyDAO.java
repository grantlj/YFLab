package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yflab.model.Energy;

public class EnergyDAO {
	 private final static String tableName="Energy";
	 
	 public  Energy getEnergyState() throws SQLException
	 {
		 Energy ret=null;
		 
		 
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
			 ret=new Energy();
			 ret.setId(rs.getInt(1));
			 ret.setPower(rs.getString(2));
			 ret.setTotal(rs.getString(3));
			 ret.setArg0(rs.getString(4));
			 ret.setArg1(rs.getString(5));
			 
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
	 
	 public boolean setEnergyState(int power_hi,int power_lo,int total) throws SQLException
	 {
		 String power=String.valueOf(power_hi*256+power_lo);
		 boolean ret=false;
		 
		 DbConnector db = new DbConnector();
		 Statement statement=null;
		 Connection connection=null;
		 try
		 {
			 connection=db.getConnection();
			 statement=connection.createStatement();
			 
		     String sql="update "+tableName+" SET power=\""+(power)+"\"+total=\""+(total)+"\" where id="+(1)+";";
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
