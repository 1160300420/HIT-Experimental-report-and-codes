package com.lovebear.Dao;

import com.lovebear.entity.Orders;

public class OrderDao extends BaseDao<Orders>{

	public Orders getInfoByOrderno(String orderno){
		String hql = "from Orders where (orderID=?)";
		Orders order = queryEntity(hql,orderno);
		return order;
	}
	
	public void save(Orders order){
		super.save(order);
	}
}
