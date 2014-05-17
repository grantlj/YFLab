package com.yflab.mail;
import java.util.TimerTask;
public class MailSenderTask extends TimerTask {
	
    public void run()
    {
    	System.out.println("Running Mail Service...");
    	MailSender.doMailSend();
    	System.out.println("Finish Mail Service...");
    }
}
