package com.lovebear.Util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CalSHA256 {

	public static String getSHA256(String str) {
	    try {
	        // ����һ��MD5���ܼ���ժҪ
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(str.getBytes());
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
