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
	  mailInfo.setPassword("nsfz123456");//������������ 
	  mailInfo.setFromAddress("axeaux@126.com"); 
	  mailInfo.setToAddress("axeaux@126.com"); 
	  mailInfo.setSubject("�������µ���ʹ������ѳ�");
	  String contentStr="��ֹ ";
	  contentStr=contentStr+new Date().toString();
	  Energy energy=null;
	try {
		energy = new EnergyDAO().getEnergyState();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  if (energy!=null)
	    contentStr=contentStr+" ���ۼ��õ����Ϊ��"+energy.getTotal()+" ��ǰ�õ�������Ϊ��"+energy.getPower();
	  else
		contentStr="ϵͳ�쳣����ѯ��Դʹ�����ʧ�ܣ�����ϵ����Ա��";
	  mailInfo.setContent(contentStr); 
	  SimpleMailSender sms = new SimpleMailSender();
      sms.sendTextMail(mailInfo);//���������ʽ 
      SimpleMailSender.sendHtmlMail(mailInfo);//����html��ʽ
  }
}
