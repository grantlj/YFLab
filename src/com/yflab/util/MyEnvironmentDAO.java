package com.yflab.util;

import java.sql.SQLException;

import com.yflab.model.MyEnvironment;

public class MyEnvironmentDAO {
  
   
  public static MyEnvironment GetLatestAllData() throws SQLException
  {
	  MyEnvironment myEnvironment=new MyEnvironment();
	  myEnvironment.setHumidity(HumidityDAO.GetLatestHumidity());
	  myEnvironment.setTemperature(TemperatureDAO.GetLatestTemperature());
	
	  
	  return myEnvironment;
  }
  
}
