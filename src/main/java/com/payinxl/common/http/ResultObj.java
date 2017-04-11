package com.payinxl.common.http;

import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCode;

import java.io.Serializable;

public class ResultObj  implements Serializable {
	private static final long serialVersionUID = 955268998822198731L;
	private boolean success;
	private String resultCode;
	private String resultMsg;
	private Object data;
	
	public boolean isSuccess() {
		return success;
	}
	public boolean isException() {
		if("EXCEPTION".equals(this.resultCode)){
			return true;
		}
		return false;
	}
	public ResultObj(Exception e) {
		this.success=false;
		if(e instanceof BusinessException){
			BusinessException be=(BusinessException)e;
			this.resultCode=be.getErrorId();
			this.resultMsg=be.getErrorMsg();
		}else{
			this.resultCode=ErrorCode.ERROR;
			this.resultCode=e.getMessage(); 
		}
	}
	public ResultObj(Object data) {
		this.success=true;
		this.data=data;
	}
	public ResultObj(boolean success,String resultMsg) {
		this.resultMsg=resultMsg;
		this.success=success;
	}
	public ResultObj(boolean success, String resultCode, String resultMsg) {
		this.resultCode=resultCode;
		this.resultMsg=resultMsg;
		this.success=success;
	}
	public ResultObj(String resultCode, String resultMsg) {
		this.resultMsg=resultMsg;
		this.resultCode=resultCode;
		this.success=false;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[resultCode="+this.resultCode+",resultMsg="+this.resultMsg+"]";
	}
}
