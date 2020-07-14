package com.boreas.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

@SuppressWarnings("restriction")
public class AESUtil {
	/* 
	 * 如果编译报错改下配置Windows -> Preferences -> Java -> Compiler -> Errors/Warnings ->
	 * Deprecated and trstricted API -> Forbidden reference (access rules): ->
	 * change to warning
	 */
	/**
	 * 根据参数生成 KEY
	 */
	private static String code = "AES";
	private static String strKey = "asdwghgvvfdnrnsernrn";
	public static Key getKey(String strKey) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
			secureRandom.setSeed(strKey.getBytes("UTF-8"));  
			Key key = null;
			KeyGenerator _generator = KeyGenerator.getInstance(code);
			_generator.init(secureRandom);
			key = _generator.generateKey();
			_generator = null;
			return key;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
	}

	/**
	 * 加密 String 明文输入 ,String 密文输出
	 */
	public static String encryptStr(String strKey, String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		Key key = getKey(strKey);
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = encryptByte(key, byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}
	public static String encryptStr(String strMing) {
		return encryptStr(strKey,  strMing);
	}
	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String decryptStr(String strKey, String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		Key key = getKey(strKey);
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = decryptByte(key, byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}
	public static String decryptStr(String strMi) {
		return decryptStr(strKey, strMi);
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private static byte[] encryptByte(Key key, byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(code);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] decryptByte(Key key, byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(code);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String[] args) throws Exception {
		String strKey = "sadsadasd1s";
		String str1 = "asdsad";
		// DES 加密字符串
		String str2 = encryptStr( str1);
		// DES 解密字符串
		String deStr = decryptStr("XASBBAC8nb1UJ9/1EVw9Xg==");
		System.out.println(" 加密前： " + str1);
		System.out.println(" 加密后： " + str2);
		System.out.println(" 解密后： " + deStr);//xlz123xlz456xlz789
		
//		System.out.println( AESUtil.decryptStr("697889a6c06bc8188a5d10881f833fa5", "H3PWoOAvrGyD1cpSFQkxgOi4AY2ehUEBep1k5PUBAUHgqyA+pPUk0jKEbEmp+PrxPq19ANbiHSugQI8Ww4E7LBJVwKXR5vE/VAVfSdM26GPjMa9e31PfH1jIrG0PXAIIBGSOnXCFm6yEa04grKFsiicTtizRPIwKykc1wSVbb6yiUbvCCz+sEz/kdnFu6SAeVaO32r+2DrFW9hSG9IfCyfDG4AZEF467ehJGO+S6RKTSFYQO/nMcnI3/2kbu/px5"));
	}
}