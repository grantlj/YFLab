package com.yflab.mail;

public class MailTest {
  public static void main(String[] args)
  {
		  MailSenderInfo mailInfo = new MailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.126.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("axeaux@126.com"); 
		  mailInfo.setPassword("nsfz123456");//������������ 
		  mailInfo.setFromAddress("axeaux@126.com"); 
		  mailInfo.setToAddress("axeaux@126.com"); 
		  mailInfo.setSubject("�����µ���ѳ�"); 
		  mailInfo.setContent("�ܹ��õ����100�ȣ�����200Ԫ��"); 
	         //�������Ҫ�������ʼ�
		  SimpleMailSender sms = new SimpleMailSender();
	          sms.sendTextMail(mailInfo);//���������ʽ 
	          SimpleMailSender.sendHtmlMail(mailInfo);//����html��ʽ
		}

}
