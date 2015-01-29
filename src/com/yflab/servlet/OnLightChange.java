package com.yflab.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yflab.util.LightDAO;

public class OnLightChange extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public OnLightChange() {
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
     
	response.setContentType("text/html; charset=utf-8");
	PrintWriter pw = response.getWriter();
	
	 try
	 {
	    String l1=(String)  request.getParameter("switch1");
	    String l2=(String) request.getParameter("switch2");
	    int light1=0,light2=0;
	    if (l1.equals("open"))
		light1=1;
	    if (l2.equals("open"))
		  light2=1;
	  
	  int lightState=light1*1+light2*2;
	  boolean bool=false;
	  
	  for (int i=0;i<3;i++)
	  {
	     bool=(bool)|| (new LightDAO().setLightState(lightState));
	      Thread.sleep(900);
	      if (bool)
				break;
	  }
	  
	  
	  response.sendRedirect("ControlCenter");
	 }
	 
	 catch (Exception e)
	 {
		pw.println("ERR");
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
