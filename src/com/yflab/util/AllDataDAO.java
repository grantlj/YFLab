package com.yflab.util;

import java.sql.SQLException;

import com.yflab.model.AllData;

public class AllDataDAO {
  
  
  public static AllData GetLatestAllData() throws SQLException
  {
	  AllData allData=new AllData();
	  allData.setHumidity(HumidityDAO.GetLatestHumidity());
	  allData.setTemperature(TemperatureDAO.GetLatestTemperature());
	
	  
	  return allData;
  }
  
}
