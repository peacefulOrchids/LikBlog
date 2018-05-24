package com.lik.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * ���ܹ���
 * @author Administrator
 *
 */
public class CryptographyUtil {
	/**
	 * base64����
	 */
	public static String encBase64(String str){
		return Base64.encodeToString(str.getBytes());
	}
	/**
	 * base64����
	 */
	public static String decBase64(String str){
		return Base64.decodeToString(str);
	}
	/**
	 * Md5����
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
		System.out.println("Md5���ܣ�"+CryptographyUtil.md5(password, yan));
		System.out.println(2<<3);
	}
}
