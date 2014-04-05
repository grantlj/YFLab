package com.yflab.model;

public class Humidity {
  private int id;
  private String value;
  private String date;
  private String arg0,arg1;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getArg0() {
	return arg0;
}
public void setArg0(String arg0) {
	this.arg0 = arg0;
}
public String getArg1() {
	return arg1;
}
public void setArg1(String arg1) {
	this.arg1 = arg1;
}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	boolean ret=false;
	try
	{
	  Humidity t=(Humidity) obj;
	  if (
		  t.getDate().equals(date) &&
		  t.getId()==id            &&
		  t.getValue().equals(value))
		  ret=true;
	} 
	catch (Exception e)
	{
		ret=false;
	}
	
	return ret;
}


}
