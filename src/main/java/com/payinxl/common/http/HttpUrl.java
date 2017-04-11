package com.payinxl.common.http;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpUrl {

	public static String post(String url, Map<String, String> body) {
		return post(url, null, body);
	}

	public static Map<String, String> body(String body) {
		Map<String, String> map = new LinkedHashMap<String, String>();

		if (body != null && body.trim().length() > 0) {
			body = body.replace("&", " & ").replace("=", " = ");
			for (String keyvaluestr : body.split("&")) {
				String[] param = keyvaluestr.trim().split("=");
				if (param.length == 2) {
					String key = param[0].trim();
					String value = param[1].trim();
					if (key.length() > 0 && value.length() > 0) {
						map.put(key, value);
					}

				}

			}

		}
		return map;

	}

	public static String post(String url, Map<String, String> header, Map<String, String> body) {
		body = body == null ? new HashMap<String, String>() : body;
		header = header == null ? new HashMap<String, String>() : header;
		System.out.println("body:"+body);
		System.out.println("header"+header);
		StringBuilder params = new StringBuilder();
		StringBuilder bodys = new StringBuilder();
		StringBuilder data = new StringBuilder();
		try {
			java.net.HttpURLConnection urlconn = (HttpURLConnection) new URL(url).openConnection();
			urlconn.setDoOutput(true);
			urlconn.setDoInput(true);
			urlconn.setRequestMethod("POST");
			urlconn.setConnectTimeout(60000);
			urlconn.setReadTimeout(60000);
			urlconn.setUseCaches(false);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (String key : header.keySet()) {
				urlconn.setRequestProperty(key, header.get(key));
			}
			urlconn.connect();
			DataOutputStream dataout = new DataOutputStream(urlconn.getOutputStream());
			for (String key : body.keySet()) {
				String value = body.get(key);
				value = value == null ? "" : value;
				params.append("&").append(key).append("=").append(URLEncoder.encode(value, "utf-8"));
				bodys.append("&").append(key).append("=").append(value);
			}
			params.delete(0, 1);
			bodys.delete(0, 1);
			System.out.println(url + "?" + bodys + "  " + header);
			dataout.writeBytes(params.toString());
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			for (String line = null; ((line = bf.readLine()) != null); data.append(line))
				;
			bf.close();
			urlconn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(data);
		return data.toString();
	}

	public static String soap(String url, String soapBody) {
		StringBuilder data = new StringBuilder();
		System.out.println(soapBody);
		try {
			URL _url = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) _url.openConnection();
			httpConnection.setConnectTimeout(100000);
			httpConnection.setReadTimeout(100000);
			httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			httpConnection.setRequestProperty("Content-Length", soapBody);
			httpConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setUseCaches(false);
			httpConnection.connect();
			OutputStream out = httpConnection.getOutputStream();
			out.write(soapBody.getBytes("utf-8"));
			out.flush();
			out.close();

			BufferedReader bf = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			for (String line; ((line = bf.readLine()) != null);)
				data.append(line);
			bf.close();
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data + "\n");

		return data.toString();
	}
}
