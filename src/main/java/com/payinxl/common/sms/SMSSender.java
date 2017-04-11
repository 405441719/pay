 
/**
  * @Description: TODO
  * Copyright: Copyright (c) 2015 
  * Company:索兑软件服务有限公司
  * @author xiahj
  * @date 2015年5月20日 上午9:45:26
  * @version V1.0
  */ 
 
package com.payinxl.common.sms;

import com.alibaba.fastjson.JSONObject;
import com.payinxl.common.auc.AUCTool;
import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCode;
import com.payinxl.common.exception.ErrorCodePropertyHandler;
import com.payinxl.common.http.HttpRequester;
import com.payinxl.common.security.MD5;
import com.payinxl.common.xml.XmlStringParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SMSSender
 * @Description: TODO
 * @author xiahj
 * @date 2015年5月20日 上午9:45:26
 *
 */
@Component
public class SMSSender {
	private final static Logger logger = LoggerFactory.getLogger(SMSSender.class);
	private static String POSTURL="http://cf.51welink.com/submitdata/Service.asmx/g_Submit";
	private static String SNAME="dltqbwl0";
	private static String SPWD="9SXUIsx1";
	private static String SPRDID="1012818";
	public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

	
	private static void parseSMSResult(String result) throws BusinessException{
		XmlStringParse xml = new XmlStringParse(result);
		String state=xml.getParameter("State");
		String msgState=xml.getParameter("MsgState");
		if(state!=null&&!"0".equals(state)){
			throw new BusinessException(ErrorCode.ERROR,msgState);
		}
	}
	private static String getPostData(String phonenum, String content) throws UnsupportedEncodingException {
		StringBuffer postData=new StringBuffer();
		postData.append("sname="+SNAME);
		postData.append("&spwd="+SPWD);
		postData.append("&scorpid=&sprdid="+SPRDID);
		postData.append("&sdst="+phonenum);
		postData.append("&smsg="+java.net.URLEncoder.encode(content,"utf-8"));
		return postData.toString();
	}
	public static void main(String[] args) {

	}
}