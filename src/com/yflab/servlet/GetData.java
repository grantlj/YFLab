package com.yflab.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yflab.model.Light;
import com.yflab.model.MyEnvironment;
import com.yflab.model.Humidity;
import com.yflab.model.Temperature;
import com.yflab.util.LightDAO;
import com.yflab.util.MyEnvironmentDAO;
import com.yflab.util.HumidityDAO;
import com.yflab.util.OperateListDAO;
import com.yflab.util.TemperatureDAO;

import com.alibaba.fastjson.*;

public class GetData extends HttpServlet {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GetData() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      this.doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		@SuppressWarnings("deprecation")
		String path=request.getRealPath("index.jsp");
	    path=path.substring(0,path.lastIndexOf('\\'))+"\\view\\images\\generated"; 
        
	    String reqType=null;
		reqType=(String) request.getParameter("reqType");
		String ret=null;
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		addViewLog(getRemoteAddress(request),"query"+"_"+reqType);
		
		if (reqType.equals("latestHumidity"))
		{
			Humidity humidity=null;
			try {
				humidity=HumidityDAO.GetLatestHumidity();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (humidity!=null) 
				ret=getJsonStr(humidity);
		}
		
	    if (reqType.equals("latestTemperature"))
	    {
	    	Temperature temperature=null;
	    	try
	    	{
	    		temperature=TemperatureDAO.GetLatestTemperature();
	    	}
	    	catch (SQLException e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    	if (temperature!=null)
	    		ret=getJsonStr(temperature);
	    }
	    
	    if (reqType.equals("myEnvironment"))
	    {
	    	MyEnvironment myEnvironment=null;
	    	try
	    	{
	    		myEnvironment=MyEnvironmentDAO.GetLatestAllData();
	    	}
	    	
	    	catch (SQLException e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    	if (myEnvironment!=null)
	    	  ret=getJsonStr(myEnvironment);
	    	
	    }
	    
	    if (reqType.equals("tempList"))
	    {
	    	  int count=-1;
	    	  try
	    	  {
	    	    count=Integer.parseInt((String)request.getParameter("count"));
	    	    ArrayList<Temperature> tempArr=TemperatureDAO.getTemperatureList(count);
	    	    ret=getJsonStr(tempArr);
	    	  }
	    	    catch (Exception e)
	    	  {
	    		  e.printStackTrace();
	    	  }
	    }
	    
	    if (reqType.equals("humidList"))
	    {
	    	  int count=-1;
	    	  try
	    	  {
	    	    count=Integer.parseInt((String)request.getParameter("count"));
	    	    ArrayList<Humidity> humidArr=HumidityDAO.getHumidityList(count);
	    	  
	    	    ret=getJsonStr(humidArr);
	    	  }
	    	    catch (Exception e)
	    	  {
	    		    
	    	    	e.printStackTrace();
	    	  }
	    }
	    
	    if (reqType.equals("tempChart"))
	    {
	    	int count=-1;
	    	try
	    	{
	    		count=Integer.parseInt((String) request.getParameter("count"));
	    		ret=TemperatureDAO.generateTemperatureChart(count, path);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	    if (reqType.equals("humidChart"))
	    {
	    	int count=-1;
	    	try
	    	{
	    		count=Integer.parseInt((String) request.getParameter("count"));
	    		ret=HumidityDAO.generateHumidityChart(count, path);
	    	}
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	    if (reqType.equals("lightState"))
	    {
	    	Light light=null;
	    	try
	    	{
	    		light=LightDAO.getLightState();
	    		ret=getJsonStr(light);
	    	}
	    	
	    	catch (Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	if (ret!=null)
		pw.println(ret);
	else
		pw.println("ERR");
	pw.flush();
	pw.close();
		
	}

	private void addViewLog(String ip,String type) {
		// TODO Auto-generated method stub
	  try {
		OperateListDAO.addViewLog(ip,type);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	}

	private String getJsonStr(Object obj) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(obj);
	}
    
	

	private String getRemoteAddress(HttpServletRequest request) {
		String ip = null;
		ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		String a[] = ip.split(",");
		return a[0];
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
