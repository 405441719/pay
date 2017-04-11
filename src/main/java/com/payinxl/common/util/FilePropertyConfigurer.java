package com.payinxl.common.util;

import com.payinxl.common.exception.ErrorCodePropertyHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Properties;


public class FilePropertyConfigurer {
	private static final Log log = LogFactory.getLog(FilePropertyConfigurer.class);
	public static final String NAME_CONFIG = "config.dir";
	
	 //读取properties的全部信息
	public static String readValue(String fileName, String key) {
		String confDir = System.getProperty(NAME_CONFIG);
		if (confDir == null || confDir.equals("")) {
			log.error("not found System Property -D" + NAME_CONFIG);
			return null;
		}
		String filePath = confDir+ File.separator + fileName + ".properties";
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error(ErrorCodePropertyHandler.errorException(e));
			return null;
		}
	}
	 //读取properties的全部信息
	public static String readValue(String key) {
		return readValue("yunkapay", key);
	}
	 //读取properties的全部信息
	public static String getConfidir() {
		return System.getProperty(NAME_CONFIG);
	}
	// 写入properties信息
	public static void writeProperties(String fileName, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			String confDir = System.getProperty(NAME_CONFIG);
			if (confDir == null || confDir.equals("")) {
				log.error("not found System Property -D" + NAME_CONFIG);
				return;
			}
			String filePath = confDir + fileName + ".properties";
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			log.error("Visit " + fileName + " for updating " + parameterName + " value error");
		}
	}
}
