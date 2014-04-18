package com.yflab.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yflab.model.Humidity;
import com.yflab.model.Temperature;
import com.yflab.util.HumidityDAO;
import com.yflab.util.InfraredDAO;
import com.yflab.util.LightDAO;
import com.yflab.util.OperateListDAO;
import com.yflab.util.SmogDAO;
import com.yflab.util.TemperatureDAO;

public class ControlCenter extends HttpServlet {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int maxItemCount=10;
	/**
	 * Constructor of the object.
	 */
	public ControlCenter() {
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
	
		
	@SuppressWarnings("deprecation")
	String path=request.getRealPath("index.jsp");
    path=path.substring(0,path.lastIndexOf('\\'))+"\\view\\images\\generated"; 
	try
	{
		addViewLog(getRemoteAddress(request),"enterCenter");
	}
	
	catch (Exception e)
	{
		
	};
	
	
	try
	{
      ArrayList<Temperature> tempArr;
      ArrayList<Humidity> humidArr;
      
      tempArr=new TemperatureDAO().getTemperatureList(maxItemCount);
      humidArr=new HumidityDAO().getHumidityList(maxItemCount);
      
      String tempChartStr=new TemperatureDAO().generateTemperatureChart(maxItemCount,path);
      String humidChartStr=new HumidityDAO().generateHumidityChart(maxItemCount,path);
      
      String lightState=String.valueOf(new LightDAO().getLightState().getLightState());
      String smogState=new SmogDAO().getSmogState().getState();
      String infraredState=new InfraredDAO().getInfraredState().getState();
      
      request.setAttribute("tempArr", tempArr);
      request.setAttribute("humidArr",humidArr);
      request.setAttribute("tempChartStr", tempChartStr);
      request.setAttribute("humidChartStr", humidChartStr);
      request.setAttribute("lightState",lightState);
      request.setAttribute("smogState",smogState);
      request.setAttribute("infraredState", infraredState);
      
      request.getRequestDispatcher("/view/ControlCenter.jsp").forward(request, response);
	}
	
	catch (Exception e){
		e.printStackTrace();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.println("ERR");
		pw.close();
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
	

	private void addViewLog(String ip,String type) {
		// TODO Auto-generated method stub
	  try {
		new OperateListDAO().addViewLog(ip,type);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
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
