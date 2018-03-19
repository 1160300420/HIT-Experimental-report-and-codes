package com.lovebear.Actions;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lovebear.Service.OrderService;
import com.lovebear.entity.Orders;

public class OrderAction extends BaseAction{

	private OrderService orderService;
	private Orders order;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public String saveOrderInfo(){
		
//		System.out.println(order.getId().getOrderId());
//		System.out.println(order.getId().getCustname());
//		System.out.println(order.getId().getTotalPrice());
//		System.out.println(order.getId().getStatus());
		if (order.getId().getStatus().equals("0")){
			orderService.saveOrderInfoServ(order);
			
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	public String checkTrail(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		String orderno = (String) session.getAttribute("orderno");
		System.out.println("HJJJ"+orderno);
		if(orderno==null){
			return ERROR;
		}else{
			session.removeAttribute("orderno");
		}
		boolean yn = orderService.getTrail(orderno);
		if(yn==true){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
}
