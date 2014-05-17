package com.yflab.mail;

public class MailTest {
  public static void main(String[] args)
  {
		  MailSenderInfo mailInfo = new MailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.126.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("axeaux@126.com"); 
		  mailInfo.setPassword("nsfz123456");//您的邮箱密码 
		  mailInfo.setFromAddress("axeaux@126.com"); 
		  mailInfo.setToAddress("axeaux@126.com"); 
		  mailInfo.setSubject("您的月电费已出"); 
		  mailInfo.setContent("总共用电度数100度，消费200元。"); 
	         //这个类主要来发送邮件
		  SimpleMailSender sms = new SimpleMailSender();
	          sms.sendTextMail(mailInfo);//发送文体格式 
	          SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式
		}

}
