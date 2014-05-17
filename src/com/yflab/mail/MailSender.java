package com.yflab.mail;

import java.sql.SQLException;
import java.util.Date;

import com.yflab.model.Energy;
import com.yflab.util.EnergyDAO;

public class MailSender {
  public static void doMailSend()
  {
	  MailSenderInfo mailInfo = new MailSenderInfo(); 
	  mailInfo.setMailServerHost("smtp.126.com"); 
	  mailInfo.setMailServerPort("25"); 
	  mailInfo.setValidate(true); 
	  mailInfo.setUserName("axeaux@126.com"); 
	  mailInfo.setPassword("nsfz123456");//您的邮箱密码 
	  mailInfo.setFromAddress("axeaux@126.com"); 
	  mailInfo.setToAddress("axeaux@126.com"); 
	  mailInfo.setSubject("您的最新电能使用情况已出");
	  String contentStr="截止 ";
	  contentStr=contentStr+new Date().toString();
	  Energy energy=null;
	try {
		energy = new EnergyDAO().getEnergyState();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  if (energy!=null)
	    contentStr=contentStr+" 您累计用电度数为："+energy.getTotal()+" 当前用电器功率为："+energy.getPower();
	  else
		contentStr="系统异常，查询能源使用情况失败！请联系管理员！";
	  mailInfo.setContent(contentStr); 
	  SimpleMailSender sms = new SimpleMailSender();
      sms.sendTextMail(mailInfo);//发送文体格式 
      SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式
  }
}
