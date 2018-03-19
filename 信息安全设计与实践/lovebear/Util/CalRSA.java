package com.lovebear.Util;

import java.io.IOException;
import java.security.InvalidKeyException;  
import java.security.KeyFactory;  
import java.security.NoSuchAlgorithmException;  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;  
import sun.misc.BASE64Decoder;  

public class CalRSA {

	
			
	
//	 private static final String DEFAULT_PUBLIC_KEY=   
//			 "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/Gu8Q1orbSzDs2+HUquJ6bA8v"+"\r"+
//			 "q52we4x3pOILeF+VxsuqTBWBi/IK5usFr6r913wKBdhxErop0ypeP3gkdOboLb0a"+"\r"+
//			 "f49epY1+ffIBAiPf2ohWzuQUNj7lUCyD6Kmo8UD8CI76+kfRrgbyl+IHXj/neg58"+"\r"+
//			 "OGtSWI7X1rrC/RfBWwIDAQAB"+"\r";
	
	
	 private static final String DEFAULT_PUBLIC_KEY=
			 "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2aP2dBemonXiXny3zlzjtR3HM"+"\r"+
			 "GoEHmi6sC434dBb/lF00SehrmpZzbuHJYderxV3sWzWLJ6e1SsCR2v0kx0BOwCah"+"\r"+
			 "RGr2B6fYOKyB1gnsn/286u5BCIbqsJEInTAQtgtZL8NEemOcKxTFXEofc16AkIYe"+"\r"+
			 "eABcOG352TCcogX+0wIDAQAB"+"\r";

	 private static final String DEFAULT_PRIVATE_KEY=  
			 "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL8a7xDWittLMOzb"+"\r"+
			 "4dSq4npsDy+rnbB7jHek4gt4X5XGy6pMFYGL8grm6wWvqv3XfAoF2HESuinTKl4/"+"\r"+
			 "eCR05ugtvRp/j16ljX598gECI9/aiFbO5BQ2PuVQLIPoqajxQPwIjvr6R9GuBvKX"+"\r"+
			 "4gdeP+d6Dnw4a1JYjtfWusL9F8FbAgMBAAECgYAi4ky/WxpPu2L76YtIocTjqfP/"+"\r"+
			 "tKj9PSA/+sYUZ+2yzm4A8RB/7EbLfKlsCUwCOCJ5ReXjhhKbbeNu/tD0fq5jZHvd"+"\r"+
			 "NvYg2vyVkE60utHg1EdlYX/T7B30wB00oWI9s1QHupmu0gRfCIS0dkhVPIAqgDRv"+"\r"+
			 "cgsy6Ta8wmxKJNYZcQJBAO0a2vpBfd6Jhi2xbPaY8jmb82oW3pt886eyqTba0+zV"+"\r"+
			 "beOMMvpTtnmpXBcClnpYAtFHcskHCmdEvlIGFpjl5uMCQQDOVaUg3rDtE1Kp6NyZ"+"\r"+
			 "UlArSVqqQmINVQY4ql86s70fmmAvzwdZJEvj7mBHHtYG6BB6JlXidqSB0/RjG5eL"+"\r"+
			 "ts0pAkAIKIdmWwCIurSqN9Om/IZBIzOR7+2C7h64I3adE5R9Lq7USIusf1rk/sXT"+"\r"+
			 "aNxio0qdhtbVQe/Cs+PUkK8QXcC/AkEAtJso6CWk7k9smwcWGSqAu/MAZmmm1A6p"+"\r"+
			 "XG5c3tA3afNB4Hcx8mgf+YpNQdOO+gqlaZYmtQyuoGl70Eh1lTjUSQJBAM17AmgF"+"\r"+
			 "NXZ1cx3HRozNZTs58KzHrWMXGxKigJb5cYanb5CK5VVv04KTUT4ngoRz5NoCHVta"+"\r"+
			 "jKt07bnDwFiXl18="+"\r";
//	private static final String DEFAULT_PRIVATE_KEY=
//			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL8a7xDWittLMOzb"+"\r"+
//			"4dSq4npsDy+rnbB7jHek4gt4X5XGy6pMFYGL8grm6wWvqv3XfAoF2HESuinTKl4/"+"\r"+
//			"eCR05ugtvRp/j16ljX598gECI9/aiFbO5BQ2PuVQLIPoqajxQPwIjvr6R9GuBvKX"+"\r"+
//			"4gdeP+d6Dnw4a1JYjtfWusL9F8FbAgMBAAECgYAi4ky/WxpPu2L76YtIocTjqfP/"+"\r"+
//			"tKj9PSA/+sYUZ+2yzm4A8RB/7EbLfKlsCUwCOCJ5ReXjhhKbbeNu/tD0fq5jZHvd"+"\r"+
//			"NvYg2vyVkE60utHg1EdlYX/T7B30wB00oWI9s1QHupmu0gRfCIS0dkhVPIAqgDRv"+"\r"+
//			"cgsy6Ta8wmxKJNYZcQJBAO0a2vpBfd6Jhi2xbPaY8jmb82oW3pt886eyqTba0+zV"+"\r"+
//			"beOMMvpTtnmpXBcClnpYAtFHcskHCmdEvlIGFpjl5uMCQQDOVaUg3rDtE1Kp6NyZ"+"\r"+
//			"UlArSVqqQmINVQY4ql86s70fmmAvzwdZJEvj7mBHHtYG6BB6JlXidqSB0/RjG5eL"+"\r"+
//			"ts0pAkAIKIdmWwCIurSqN9Om/IZBIzOR7+2C7h64I3adE5R9Lq7USIusf1rk/sXT"+"\r"+
//			"aNxio0qdhtbVQe/Cs+PUkK8QXcC/AkEAtJso6CWk7k9smwcWGSqAu/MAZmmm1A6p"+"\r"+
//			"XG5c3tA3afNB4Hcx8mgf+YpNQdOO+gqlaZYmtQyuoGl70Eh1lTjUSQJBAM17AmgF"+"\r"+
//			"NXZ1cx3HRozNZTs58KzHrWMXGxKigJb5cYanb5CK5VVv04KTUT4ngoRz5NoCHVta"+"\r"+
//			"jKt07bnDwFiXl18="+"\r";
//	
    private RSAPrivateKey privateKey;  

    private RSAPublicKey publicKey;  
    
    private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    
    public RSAPrivateKey getPrivateKey() {  
        return privateKey;  
    }  
   
    public RSAPublicKey getPublicKey() {  
        return publicKey;  
    }  
    
    public void loadPublicKey(String publicKeyStr) throws Exception{  
        try {  
            BASE64Decoder base64Decoder= new BASE64Decoder();  
            byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
            this.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (IOException e) {  
            throw new Exception("公钥数据内容读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
    
    public void loadPrivateKey(String privateKeyStr) throws Exception{  
        try {  
            BASE64Decoder base64Decoder= new BASE64Decoder();  
            byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            this.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (IOException e) {  
            throw new Exception("私钥数据内容读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
    
    public String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{  
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output= cipher.doFinal(plainTextData);  
            String cipherdata=new String(Base64.encodeBase64(output), "UTF-8");
            return cipherdata;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
    
    public String encrypt(RSAPrivateKey publicKey, byte[] plainTextData) throws Exception{  
        if(publicKey== null){  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output= cipher.doFinal(plainTextData);  
            String cipherdata=new String(Base64.encodeBase64(output), "UTF-8");
            return cipherdata;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
    
    public byte[] decrypt(RSAPrivateKey privateKey, String cipherData) throws Exception{  
        if (privateKey== null){  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
        	byte[] c=(byte[])Base64.decodeBase64(cipherData);
            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output= cipher.doFinal(c);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }         
    }  
    
    public byte[] decrypt(RSAPublicKey privateKey, String cipherData) throws Exception{  
        if (privateKey== null){  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher= null;  
        try {  
        	byte[] c=(byte[])Base64.decodeBase64(cipherData);
            cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output= cipher.doFinal(c);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        }catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }         
    }  
    
    public static String byteArrayToString(byte[] data){  
        StringBuilder stringBuilder= new StringBuilder();  
        for (int i=0; i<data.length; i++){  
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);  
            //取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);  
            if (i<data.length-1){  
                stringBuilder.append(' ');  
            }  
        }  
        return stringBuilder.toString();  
    }  
    
    public CalRSA getPkSk(){
    	CalRSA calRSA=new CalRSA();
    	try {  
            calRSA.loadPublicKey(CalRSA.DEFAULT_PUBLIC_KEY);  
            System.out.println("加载公钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载公钥失败");  
        }  
  
        //加载私钥  
        try {  
            calRSA.loadPrivateKey(CalRSA.DEFAULT_PRIVATE_KEY);  
            System.out.println("加载私钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载私钥失败");  
        }  
        return calRSA;
    }
    public void test(HttpSession session){
    	System.out.println("Hjj");
    	System.out.println(session.getAttribute("DHKey"));
    }
    
    public static void main(String[] args){  
        CalRSA rsaEncrypt= new CalRSA();  
        //rsaEncrypt.genKeyPair();  
  
        //加载公钥  
        try {  
            rsaEncrypt.loadPublicKey(CalRSA.DEFAULT_PUBLIC_KEY);  
            System.out.println("加载公钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载公钥失败");  
        }  
  
        //加载私钥  
        try {  
            rsaEncrypt.loadPrivateKey(CalRSA.DEFAULT_PRIVATE_KEY);  
            System.out.println("加载私钥成功");  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
            System.err.println("加载私钥失败");  
        }  
  
        //测试字符串  
        String encryptStr= "123456789";  
  
        try {  
            //加密  
            //byte[] cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes());  
            String cipherdata="U+jS4UaD/19QuhfXZRWzdwnre+I4LnwSfJdgn6NYmFTQhzTs//SFFlIyxzCxA8dN09QIko/4wsJ8i6Sbxt3l1BxjQJyyg/nOQ4fea3HvXmL0z2/xFydV4aoo9iZCNENbgUTWZaMCZhlykPbRrxbqUrZ6cqnkQ30wvFfqXbEYE/o=";
            //解密  
            byte[] plainText = rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), cipherdata);  
           // System.out.println("密文长度:"+ cipher.length);  
            System.out.println(cipherdata);  
            System.out.println("明文长度:"+ plainText.length);  
            System.out.println(CalRSA.byteArrayToString(plainText));  
            System.out.println(new String(plainText));  
        } catch (Exception e) {  
            System.err.println(e.getMessage());  
        }  
    } 
    
}  

