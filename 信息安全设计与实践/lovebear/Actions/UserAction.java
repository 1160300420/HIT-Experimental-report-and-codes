package com.lovebear.Actions;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lovebear.Service.MailValidate;
import com.lovebear.Service.UserService;
import com.lovebear.entity.Result;
import com.lovebear.entity.User;
import com.lovebear.entity.UserId;

public class UserAction extends BaseAction{
	private String idcode;	
	private User u;
	private UserService userService;

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}	
	
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setIdcode(){
		MailValidate mailValidate=new MailValidate();	
		HttpSession session=ServletActionContext.getRequest().getSession();
		
		String idcode=mailValidate.generateIdcode();
		mailValidate.setIdCodeAndTime(session,idcode);
		mailValidate.mailSend(idcode, getU().getId().getEmail());
		if(session==null){
			System.out.println("hjjj");
		}else {
			System.out.println(session.toString());
		}
		System.out.println(session.getAttribute("idcode"));
	}
	
	public String register(){
		MailValidate mailValidate=new MailValidate();
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		String key = (String)session.getAttribute("DHKey");
		
		if(mailValidate.mailValidate(session, getIdcode())==true){
			
			Result<Boolean> result = userService.register(u);
			return SUCCESS;
		}
		return ERROR;
		
	}
	
	public String login() throws Exception{
		Result<UserId> result = userService.queryToResult(u);
		if(result.state==1){
			HttpSession session=ServletActionContext.getRequest().getSession();
			session.setAttribute("email", u.getId().getEmail());
			System.out.println(u.getId().getEmail());
			return SUCCESS;
		}		
		return ERROR;
	}
	
}
