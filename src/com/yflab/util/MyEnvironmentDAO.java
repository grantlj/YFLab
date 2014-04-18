package com.yflab.util;

import java.sql.SQLException;

import com.yflab.model.MyEnvironment;

public class MyEnvironmentDAO {
  
   
  public MyEnvironment GetLatestAllData() throws SQLException
  {
	  MyEnvironment myEnvironment=new MyEnvironment();
	  myEnvironment.setHumidity(new HumidityDAO().GetLatestHumidity());
	  myEnvironment.setTemperature(new TemperatureDAO().GetLatestTemperature());
	  myEnvironment.setSmog(new SmogDAO().getSmogState());
	  myEnvironment.setInfrared(new InfraredDAO().getInfraredState());
	  myEnvironment.setEnergy(new EnergyDAO().getEnergyState());
	  return myEnvironment;
  }
  
}
