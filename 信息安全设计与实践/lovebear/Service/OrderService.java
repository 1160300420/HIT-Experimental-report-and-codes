package com.lovebear.Service;

import com.lovebear.Dao.OrderDao;
import com.lovebear.entity.Orders;

public class OrderService {

	private OrderDao orderDao;

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public boolean saveOrderInfoServ(Orders order){
		if(order.getId().getOrderId()==null){
			return false;
		}
		if(order.getId().getCustname()==null){
			return false;
		}
		if(order.getId().getTotalPrice()==null){
			return false;
		}
		if(order.getId().getStatus()==null){
			order.getId().setStatus("Fail");
		}
		orderDao.save(order);
		return true;
	}
	
	public boolean getTrail(String orderno){
		Orders order = orderDao.getInfoByOrderno(orderno);
		if(order!=null){
			return true;
		}
		return false;
	}
}
