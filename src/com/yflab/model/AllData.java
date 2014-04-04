package com.yflab.model;

public class AllData {
	
	private Temperature temperature;
	private Humidity humidity;

	public AllData() {
		// TODO Auto-generated constructor stub
	}

	public AllData(Temperature temperature, Humidity huminity) {
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
