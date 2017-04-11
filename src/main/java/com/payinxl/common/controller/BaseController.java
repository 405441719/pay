package com.payinxl.common.controller;


import com.payinxl.common.exception.ErrorCodePropertyHandler;
import com.payinxl.common.http.ResultObj;
import com.payinxl.common.util.RequestUtils;
import com.payinxl.model.MemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public abstract class BaseController {
	private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
	public ResultObj exceptionObj(Exception e) {
		return new ResultObj(e);
	}
	public ResultObj successObj(Object object) {
		return new ResultObj(object);
	}
	public ResultObj successMsg(String msg) {
		return new ResultObj(true,msg);
	}
	public ResultObj errorMsg(String msg) {
		return new ResultObj(false,msg);
	}
	public ResultObj successCodeMsg(String code, String msg) {
		return new ResultObj(true,code,msg);
	}
	public ResultObj errorCodeMsg(String code, String msg) {
		return new ResultObj(false,code,msg);
	}
	public ResultObj successCode(String code) {
		return new ResultObj(true,code,null);
	}
	public ResultObj errorCode(String code) {
		return new ResultObj(false,code,null);
	}
	protected String getSessionUsername(){
		Authentication ath=SecurityContextHolder.getContext().getAuthentication();
		try {
			UserDetails userDetails = (UserDetails) ath.getPrincipal();
			return userDetails != null ? userDetails.getUsername() : null;
		}catch(Exception e){
			logger.warn("获取sessionUsername出现异常:"+e.getMessage());
			return null;
		}
	}
	public String getRemoteIp(HttpServletRequest request)
	{
		return RequestUtils.getIp(request);
	}
	public String getUrl(HttpServletRequest request)
	{
		String url=request.getRequestURL().toString();
		url=url.indexOf("/",10)!=-1?url.substring(0,url.indexOf("/",10)):url;
		return url;
	}

}