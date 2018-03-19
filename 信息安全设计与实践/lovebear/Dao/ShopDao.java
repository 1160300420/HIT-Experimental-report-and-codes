package com.lovebear.Dao;

import com.lovebear.entity.Items;

public class ShopDao  extends BaseDao<Items>{

	public Items getInfoByItemID(String itemID){
		String hql = "from Items where (itemID=?)";
		Items item = queryEntity(hql,itemID);
		return item;
	}
}
