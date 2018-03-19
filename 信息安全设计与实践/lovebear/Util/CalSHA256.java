package com.lovebear.Util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CalSHA256 {

	public static String getSHA256(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(str.getBytes());
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
