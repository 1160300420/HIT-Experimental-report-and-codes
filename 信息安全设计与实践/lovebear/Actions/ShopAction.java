package com.lovebear.Actions;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lovebear.Service.ShopService;
import com.lovebear.entity.Items;
import com.lovebear.entity.OrderInfo;

public class ShopAction extends BaseAction{

	private ShopService shopService;
	private Items item;
	private ArrayList<OrderInfo> list;
	private int quantity;
	private long totalPrice;
	private String idcard;
	private String realname;
	private String orderno;
	
	public ShopAction(){
		totalPrice=0;
	}
	
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

	public ArrayList<OrderInfo> getList() {
		return list;
	}

	public void setList(ArrayList<OrderInfo> list) {
		this.list = list;
	}

	public String joinCart(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("email")==null){
			return ERROR;
		}
		
		if(item.getId().getItemId()!=null){
			boolean enough = shopService.joinCartServ(item.getId().getItemId(), quantity);
			if(enough){
				return SUCCESS;
			}else{
				return "notenough";
			}
			
		}
		
		return SUCCESS;

	}
	
	public String showCart(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		list = shopService.getInfo();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				totalPrice=totalPrice + list.get(i).getItemPrice()*list.get(i).getQuantity();
			}
			
			session.setAttribute("totalPrice", totalPrice);
		}	
		if(session.getAttribute("email")==null){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String submitOrder() throws Exception{
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("email")==null){
			return ERROR;
		}
		if(session.getAttribute("totalPrice")!=null){
			totalPrice = (Long) session.getAttribute("totalPrice");
		}else{
			return "catoerr";
		}
				
		orderno = shopService.getOrderInfoJson(idcard, realname,totalPrice);
		//order info write into database
		
		return SUCCESS;
	}
}
