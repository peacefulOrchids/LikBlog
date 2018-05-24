package com.lik.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//访问属性文件
public class PropertiesUtil {
	public static String getValue(String key){
		Properties prop=new Properties();
		InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/blog.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
