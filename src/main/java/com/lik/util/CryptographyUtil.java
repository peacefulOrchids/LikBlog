package com.lik.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具
 * @author Administrator
 *
 */
public class CryptographyUtil {
	/**
	 * base64加密
	 */
	public static String encBase64(String str){
		return Base64.encodeToString(str.getBytes());
	}
	/**
	 * base64解密
	 */
	public static String decBase64(String str){
		return Base64.decodeToString(str);
	}
	/**
	 * Md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	
	public static void main(String[] args) {
		String yan = PropertiesUtil.getValue("yan");
		String password="123";
		System.out.println(CryptographyUtil.encBase64(password));
		System.out.println(CryptographyUtil.decBase64(CryptographyUtil.encBase64(password)));
		System.out.println("Md5加密："+CryptographyUtil.md5(password, yan));
		System.out.println(2<<3);
	}
}
