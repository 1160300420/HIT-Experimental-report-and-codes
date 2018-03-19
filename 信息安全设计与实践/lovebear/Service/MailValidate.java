package com.lovebear.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import com.lovebear.Util.CalMD5;

public class MailValidate {

	public void mailSend(String idCode, String destAddress){
		try {
			Properties props = new Properties();  
	        props.setProperty("mail.smtp.auth", "true");//设置访问smtp服务器需要认证  
	        props.setProperty("mail.transport.protocol", "smtp"); //设置访问服务器的协议
	        
	        Session se = Session.getDefaultInstance(props);  
	        se.setDebug(true); //打开debug功能  
	          
	        Message msg = new MimeMessage(se);  
	        msg.setFrom(new InternetAddress("lovebear96@163.com")); //设置发件人，163邮箱要求发件人与登录用户必须一致（必填），其它邮箱不了解  
	        msg.setText(idCode); //设置邮件内容  
	        msg.setSubject("邮件认证"); //设置邮件主题  
	          
	        Transport trans = se.getTransport();  
	        trans.connect("smtp.163.com", 25, "lovebear96@163.com", "12345678hjj"); //连接邮箱smtp服务器，25为默认端口  
	        trans.sendMessage(msg, new Address[]{new InternetAddress(destAddress)}); //发送邮件  
	          
	        trans.close(); //关闭连接 
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		 
	}
	
	public String generateIdcode(){
		int x=(int)(Math.random()*10000);
		return CalMD5.getMD5(String.valueOf(x)).substring(0,10);
	}
	
	public void setIdCodeAndTime(HttpSession session,String idcode){		
		session.setAttribute("idcode",idcode);
		session.setAttribute("registertime", new Date());
	}
	
	public boolean mailValidate(HttpSession session, String idcode){
		String pre_idcode=(String) session.getAttribute("idcode");
		Date date=(Date) session.getAttribute("registertime");
		
		Calendar t =Calendar.getInstance();
		t.setTime(date);
		t.add(12, 10);
		date=t.getTime();

		Date current_date=new Date();
		if(idcode.equals(pre_idcode) && date.after(current_date)){
			return true;
		}else{
			return false;			
		}

	}

}
