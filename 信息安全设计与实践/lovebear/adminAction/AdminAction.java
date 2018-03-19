package com.lovebear.adminAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lovebear.adminDao.adItemDao;
import com.lovebear.adminDao.adOrderDao;
import com.lovebear.adminDao.adUserDao;
import com.lovebear.entity.Items;
import com.lovebear.entity.Orders;
import com.lovebear.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport{

	private User user;
	private Items item;
	private Orders order;
	private adUserDao adUserDao;
	private adItemDao adItemDao;
	private adOrderDao adOrderDao;
	private List<User> userData = new ArrayList<User>();
	private List<Items> itemData = new ArrayList<Items>();
	private List<Orders> orderData = new ArrayList<Orders>();
	
	private String uniquekey;
	private int num;
	private String newname;
	private String newprice;
	private String isSelected;
	
	private String username;
	private String passwd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNewprice() {
		return newprice;
	}
	public void setNewprice(String newprice) {
		this.newprice = newprice;
	}
	public String getNewname() {
		return newname;
	}
	public void setNewname(String newname) {
		this.newname = newname;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}
	public adUserDao getAdUserDao() {
		return adUserDao;
	}
	public void setAdUserDao(adUserDao adUserDao) {
		this.adUserDao = adUserDao;
	}
	public adItemDao getAdItemDao() {
		return adItemDao;
	}
	public void setAdItemDao(adItemDao adItemDao) {
		this.adItemDao = adItemDao;
	}
	public adOrderDao getAdOrderDao() {
		return adOrderDao;
	}
	public void setAdOrderDao(adOrderDao adOrderDao) {
		this.adOrderDao = adOrderDao;
	}
	public List<User> getUserData() {
		return userData;
	}
	public void setUserData(List<User> userData) {
		this.userData = userData;
	}
	public List<Items> getItemData() {
		return itemData;
	}
	public void setItemData(List<Items> itemData) {
		this.itemData = itemData;
	}
	public List<Orders> getOrderData() {
		return orderData;
	}
	public void setOrderData(List<Orders> orderData) {
		this.orderData = orderData;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Items getItem() {
		return item;
	}
	public void setItem(Items item) {
		this.item = item;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	
	public String adminLogin(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(username.equals("admin") && passwd.equals("888888")){
			if(session.getAttribute("isadmin")==null){
				session.setAttribute("isadmin", "1");
			}
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	public String showUsers(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		userData = adUserDao.userFindAll();
		return SUCCESS;
	}
	
	public String userDeleteByUniq() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		adUserDao.userDelete(uniquekey);
		userData=adUserDao.userFindAll();
		return SUCCESS;
	}
	
	public String userDeleteMore() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		if(isSelected!=null){
			String[] list=isSelected.split(", ");
			for(String s:list){
				adUserDao.userDelete(s);
			}
		}
		
		userData=adUserDao.userFindAll();
		return SUCCESS;
	}
	
	public String showItems(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		itemData = adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemDeleteByUniq() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		adItemDao.itemDelete(uniquekey);
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemDeleteMore() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		if(isSelected!=null){
			String[] list=isSelected.split(", ");
			for(String s:list){
				adItemDao.itemDelete(s);
			}
		}
		
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemAddByUniq(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		Items item=adItemDao.itemFindByUniq(uniquekey);
		adItemDao.itemAddByUniq(num, uniquekey, item.getId().getInventory());
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemReduceByUniq(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		Items item=adItemDao.itemFindByUniq(uniquekey);
		adItemDao.itemReduceByUniq(num, uniquekey, item.getId().getInventory());
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemRenameByUniq(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		System.out.println(newname);
		adItemDao.itemRenameByUniq(newname, uniquekey);
		
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String itemPriceChangeByUniq(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		adItemDao.itemPriceChangeByUniq(newprice, uniquekey);
		itemData=adItemDao.itemFindAll();
		return SUCCESS;
	}
	
	public String showOrders(){
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		orderData = adOrderDao.orderFindAll();
		return SUCCESS;
	}
	
	public String orderDeleteByUniq() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		adOrderDao.orderDelete(uniquekey);
		orderData=adOrderDao.orderFindAll();
		return SUCCESS;
	}
	
	public String orderDeleteMore() {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("isadmin")==null || session.getAttribute("isadmin")!="1"){
			return ERROR;
		}
		if(isSelected!=null){
			String[] list=isSelected.split(", ");
			for(String s:list){
				adOrderDao.orderDelete(s);
			}
		}
		
		orderData=adOrderDao.orderFindAll();
		return SUCCESS;
	}
}
