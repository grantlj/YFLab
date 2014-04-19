package com.yflab.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yflab.util.EnergyDAO;
import com.yflab.util.HumidityDAO;
import com.yflab.util.InfraredDAO;
import com.yflab.util.LightDAO;
import com.yflab.util.OperateListDAO;
import com.yflab.util.SmogDAO;
import com.yflab.util.TemperatureDAO;

public class SetData extends HttpServlet {
    
	 private static int lightViewCount=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SetData() {
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

		this.doPost(request, response);
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
		boolean doFlag=false;
		lightViewCount++;
		if (lightViewCount==10)
		{
			doFlag=true;
			lightViewCount=0;
		}
		
		@SuppressWarnings("deprecation")
		String path=request.getRealPath("index.jsp");
	    path=path.substring(0,path.lastIndexOf('\\'))+"\\view\\images\\generated"; 
        
	    String reqType=null;
		reqType=(String) request.getParameter("reqType");
		String ret=null;
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();
		
		addViewLog(getRemoteAddress(request),"update"+"_"+reqType);
		
		if (reqType.equals("lightState"))
		{
			String arg0=(String) request.getParameter("arg0");
			
			int lightState=-1;
			try
			{
			  lightState=Integer.parseInt(arg0);
			  if (lightState!=-1)
				  if (new LightDAO().setLightState(lightState))
			        ret="Set lightState OK";
				  else 
					ret="ERR";
			}
			catch (Exception e)
			{
				ret="ERR";
			}
		}
		
		if (reqType.equals("sensorData"))
		{
			
			/*
			 * http://localhost:8080/YFLab/SetData?reqType=sensorData&junction=1&light1=1&light2=0&humidity=35&temperature=40
			 */
			try
			{
			   int junction=Integer.parseInt((String) request.getParameter("junction"));
			   
			   int light1=Integer.parseInt((String) request.getParameter("light1"));
			   int light2=Integer.parseInt((String) request.getParameter("light2"));
			   
			   int lightState=light1*1+light2*2;
			   
			   int humidity=Integer.parseInt((String) request.getParameter("humidity"));
			   int temperature=Integer.parseInt((String) request.getParameter("temperature"));
			   int infraredState=Integer.parseInt((String) request.getParameter("infrared"));
			   int smogState=Integer.parseInt((String) request.getParameter("smog"));
			   
			   int power_hi=Integer.parseInt((String) request.getParameter("power_hi"));
			   int power_lo=Integer.parseInt((String) request.getParameter("power_lo"));
			   int total=Integer.parseInt((String) request.getParameter("total"));
			   
			   new TemperatureDAO().setLatestTemperature(junction,temperature);
			   new HumidityDAO().setLatestHumidity(junction,humidity);
			   if (doFlag)
			     new LightDAO().setLightState(lightState);
			   new InfraredDAO().setInfraredState(infraredState);
			   new SmogDAO().setSmogState(smogState);
			   new EnergyDAO().setEnergyState(power_hi, power_lo, total);
			   
			   ret="Set Sensor Data OK";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				ret="ERR";
			}
		}
		
		pw.println(ret);
		pw.flush();
		pw.close();
	}
    
	
	private void addViewLog(String ip,String type) {
		// TODO Auto-generated method stub
	  try {
		new OperateListDAO().addViewLog(ip,type);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
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
