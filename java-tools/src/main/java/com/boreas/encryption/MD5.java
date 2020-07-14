package com.boreas.encryption;
import java.security.MessageDigest;

/**
 * MD5加密
 * @author jiangxvhua
 * @date 创建时间  2017年2月15日
 */
public class MD5 {
	
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * J 转换byte到16进制
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * J 编码, MessageDigest 为 JDK 提供的加密类
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin.toUpperCase();
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes("GBK")));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	/**
	 * 中文转unicode
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str){  
		         String result="";  
		         for (int i = 0; i < str.length(); i++){  
		             int chr1 = (char) str.charAt(i);  
		             if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
		                 result+="\\u" + Integer.toHexString(chr1);  
		             }else{  
		                 result+=str.charAt(i);  
		             }  
		         }  
		         return result;  
		     }  
	
	public static void main(String[] args) {
		System.out.println(MD5.MD5Encode(("2222").toUpperCase()));
		System.out.println(MD5.chinaToUnicode("22222"));
		System.out.println(Integer.toHexString((char)'你'));
	}
	
}
