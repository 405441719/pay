package com.payinxl.common.http;

import com.payinxl.common.security.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpMas {

	public static String post(String url, Map<String, String> body, String key) {
		return post(url, null, body, key);
	}

	public static String post(String url, Map<String, String> header, Map<String, String> body, String key) {
		String signType = body.get("signType");
		String charset = body.get("charset");
		StringBuilder sb = new StringBuilder();
		try {
			for (String str : body.keySet())
				sb.append("&").append(str).append("=").append(URLEncoder.encode(body.get(str), charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.delete(0, 1);
		String signMsg = "";
		System.out.println("要加密的字符串"+sb.toString() + key.toString());
		signMsg = MD5.digest(sb.toString() + key.toString()).toUpperCase();
		header = header == null ? new HashMap<String, String>() : body;
		header.put("signType", signType);
		header.put("signMsg", signMsg);
		//header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		return HttpUrl.post(url, header, body);
	}

}
