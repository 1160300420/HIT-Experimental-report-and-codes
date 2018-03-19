package com.lovebear.Util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CalMD5 {

	public static String getMD5(String str) {
	    try {
	        // ����һ��MD5���ܼ���ժҪ
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // ����md5����
	        md.update(str.getBytes());
	        // digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
	        // BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
