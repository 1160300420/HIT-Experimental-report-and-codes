package com.lovebear.Actions;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletContextAware,
ServletRequestAware,ServletResponseAware{

	private final String CHARSET = "utf-8";
	protected String rootPath;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		arg0.setCharacterEncoding(CHARSET);
		arg0.setContentType("html/text;charset=utf-8");
		/*
		try {
			out = arg0.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		try {
			arg0.setCharacterEncoding(CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		rootPath=arg0.getRealPath("/");
		
	}
	
	
}
