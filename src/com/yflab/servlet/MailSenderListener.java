package com.yflab.servlet;

import javax.servlet.*;

import com.yflab.mail.MailSenderTask;

public class MailSenderListener implements javax.servlet.ServletContextListener {
    private java.util.Timer timer=null;
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		timer=new java.util.Timer(true);
		timer.schedule(new MailSenderTask(), 0, 1000*60*10);
	}

}
