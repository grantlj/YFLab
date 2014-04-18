package com.yflab.model;

public class MyEnvironment {
	
	private Temperature temperature;
	private Humidity humidity;
	private Infrared infrared;
	private Smog smog;
	
	public Infrared getInfrared() {
		return infrared;
	}

	public void setInfrared(Infrared infrared) {
		this.infrared = infrared;
	}

	public Smog getSmog() {
		return smog;
	}

	public void setSmog(Smog smog) {
		this.smog = smog;
	}

	public MyEnvironment() {
		// TODO Auto-generated constructor stub
	}

	public MyEnvironment(Temperature temperature, Humidity huminity) {
		super();
		this.temperature = temperature;
		this.humidity = huminity;
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}

	public Humidity getHumidity() {
		return humidity;
	}

	public void setHumidity(Humidity humidity) {
		this.humidity = humidity;
	}

	
}
