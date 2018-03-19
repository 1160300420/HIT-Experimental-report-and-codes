package com.lovebear.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;

import sun.security.util.Length;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.lovebear.Dao.ShopDao;
import com.lovebear.Util.CalMD5;
import com.lovebear.Util.CalRSA;
import com.lovebear.Util.MatrixToImageWriter;
import com.lovebear.entity.Items;
import com.lovebear.entity.OrderInfo;

public class ShopService {
	private ShopDao shopDao;

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public boolean joinCartServ(String itemID, int quantity){
		HttpSession session=ServletActionContext.getRequest().getSession();
		Items item = shopDao.getInfoByItemID(itemID);

		if(quantity>item.getId().getInventory()){
			return false;
		}
		if(session.getAttribute("cart_info") == null){
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put(itemID,quantity);
			session.setAttribute("cart_info", map);
		}else{
			HashMap<String, Integer> map=(HashMap<String, Integer>) session.getAttribute("cart_info");
			map.put(itemID,quantity);
			session.setAttribute("cart_info", map);
		}	
		
		return true;
	}
	
	public ArrayList<OrderInfo> getInfo(){
		String itemID = null;
		ArrayList<OrderInfo> list = new ArrayList<OrderInfo>();
		HttpSession session=ServletActionContext.getRequest().getSession();
		HashMap<String, Integer> map=(HashMap<String, Integer>) session.getAttribute("cart_info");
		if(map==null)
			return null;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {  		  
		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
			itemID = entry.getKey();
			Items item = shopDao.getInfoByItemID(itemID);
			OrderInfo oi= new OrderInfo();
			oi.setItemId(item.getId().getItemId());
			oi.setItemName(item.getId().getItemName());
			oi.setItemPrice(Long.parseLong(item.getId().getItemPrice()));
			oi.setInventory(item.getId().getInventory());
			oi.setImgUrl(item.getId().getImgUrl());
			oi.setQuantity(entry.getValue());
			list.add(oi);
		}  
		return list;
	}
	
	public String getOrderInfoJson(String idcard,String realname,Long totalPrice) throws Exception{
		HttpSession session=ServletActionContext.getRequest().getSession();
		String bankPublicKey;
		//time + random string to generate the orderno
		Date d = new Date();
		String sdate=(new SimpleDateFormat("yy-MM-dd-hh")).format(d);
		String o1,r1,t1,s1;
		int x=(int)(Math.random()*10000);
		String randstr = CalMD5.getMD5(String.valueOf(x)).substring(0,6);
		String orderno=sdate+randstr;
		System.out.println("LOVE"+orderno);
		session.setAttribute("orderno", orderno);
		JSONObject data=new JSONObject();
		
		//Double confirm
		String pr=null;
		String shopId=null;
		ArrayList<String> tmplist = new ArrayList<String>();
		tmplist.add("0");
		tmplist.add("1");
		doubleConfirm(orderno);
		o1 = tmplist.get(0)+" "+tmplist.get(1);
		doubleConfirm(realname);
		r1 = tmplist.get(0)+" "+tmplist.get(1);
		doubleConfirm(totalPrice.toString());
		t1 = tmplist.get(0)+" "+tmplist.get(1);
		doubleConfirm("666666");	
		s1 = tmplist.get(0)+" "+tmplist.get(1);
		
		orderno=doubleConfirm(orderno);
		realname=doubleConfirm(realname);
		pr=doubleConfirm(totalPrice.toString());
		shopId=doubleConfirm("666666");
		
		data.put("orderno", orderno);
		data.put("realname", realname);
		data.put("totalPrice", pr);
		data.put("ShopIdcard", shopId);
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
	
		String tmpdata=data.toJSONString();	
		
		generateQrcode(tmpdata, out1);
		byte[] b = out1.toByteArray(); 
		//base64 encode to transport
		
		try {
			String finaldata=new String(Base64.encodeBase64(b), "UTF-8");
			session.setAttribute("qrcode", finaldata);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		return orderno;
	}
	
	public void generateQrcode(String content, OutputStream out){
		
		try {
	        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	        Map hints = new HashMap();  
	        //内容所使用编码  
	        hints.put(EncodeHintType.CHARACTER_SET, "gb2312");  
	        BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.QR_CODE, 200, 200, hints);  
	        //生成二维码  	    
	        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", out);  
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public String doubleConfirm(String str){
		CalRSA calRSA=new CalRSA();
		calRSA=calRSA.getPkSk();
		String data=null;
		ArrayList<String> list = new ArrayList<String>();
		try{
			data=calRSA.encrypt(calRSA.getPrivateKey(), str.getBytes());
			
			list.add(calRSA.encrypt(calRSA.getPublicKey(), data.substring(0, 127).getBytes()));
			list.add(calRSA.encrypt(calRSA.getPublicKey(), data.substring(128).getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}

}