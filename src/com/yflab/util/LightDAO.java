package com.yflab.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.yflab.model.Light;

public class LightDAO {
	 private final static String tableName="light";
	 private static boolean doing=false;
	 
	 public  Light getLightState() throws SQLException
	 {
		 Light ret=null;
		 
		 
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
			 
			 int state=0;
			 int i=0;
			 ArrayList<Boolean> stateMap=new ArrayList<Boolean>();
			 
			 while (rs.next())
			 {
				 state=state*2+rs.getInt(2);
				 i++;
				 boolean bval=(rs.getInt(2)==1)?true:false;
				 Boolean e=new Boolean(bval);
				 stateMap.add(e);
			 }
			 
			 ret=new Light();
			 ret.setLightState(state);
			 ret.setLightCount(i);
			 ret.setStateMap(stateMap);
			 
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
	 
	 public boolean setLightState(int lightState) throws SQLException, InterruptedException
	 {
		 
	     while (doing)
	     {
	    	Thread.sleep(10); 
	    
	     }
	     
		 doing=true;
		 boolean ret=false;
		 int[] lightMap=new int[100];
		 
		 int count=0;
		 
		 while (lightState!=0)
		 {
			 lightMap[count++]=lightState%2;
			 lightState/=2;
		 }
		 
		 
		 DbConnector db = new DbConnector();
		 Statement statement=null;
		 Connection connection=null;
		 try
		 {
			 connection=db.getConnection();
			 statement=connection.createStatement();
			 
			 for (int i=0;i<count;i++)
			 	{
				  String sql="update "+tableName+" SET state="+(lightMap[i])+" where id="+(i+1)+";";
				 // System.out.println(sql);
				  // System.out.println(sql);
				  statement.execute(sql);
				}
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
