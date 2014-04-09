package com.yflab.model;

import java.util.ArrayList;

public class Light {
  private static final int maxLightCount=100;
  private int lightState;
  private int lightCount;
  private ArrayList<Boolean> stateMap;
  
  
public ArrayList<Boolean> getStateMap() {
	return stateMap;
}

public void setStateMap(ArrayList<Boolean> stateMap) {
	this.stateMap = stateMap;
}

public int getLightCount() {
	return lightCount;
}

public void setLightCount(int lightCount) {
	this.lightCount = lightCount;
}

public int getLightState() {
	return lightState;
}

public void setLightState(int lightState) {
	this.lightState = lightState;
}

public static int getMaxlightcount() {
	return maxLightCount;
}
  
}
