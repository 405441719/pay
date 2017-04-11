package com.payinxl.trade.impl;

import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCodePropertyHandler;
import com.payinxl.common.http.HttpRequester;
import com.payinxl.common.http.ResultObj;
import com.payinxl.common.util.FilePropertyConfigurer;
import com.payinxl.common.util.MyStringUtil;
import com.payinxl.trade.TradeChannel;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XGJTradeChannel implements TradeChannel {
	private final static Logger logger = LoggerFactory.getLogger(XGJTradeChannel.class);
	private String SA;
	private String SAPASS;
	private String GROUP;
	private String SERVERURL;
	private String MAINACCOUNT;
	public XGJTradeChannel(Map<String,Object> param){
		this.SA=(String)param.get("sa");
		this.SAPASS=(String)param.get("sapass");
		this.GROUP=(String)param.get("sagroup");
		this.SERVERURL=(String)param.get("serverurl");
		this.MAINACCOUNT=(String)param.get("mainaccount");
	}
	@Override
	public String createaccount(String password,String name) {
		try {
			String account="1"+ MyStringUtil.getRandom_123(6);
			String result = HttpRequester.httpsget(SERVERURL+"createaccount?sa="+SA+"&sapass="+SAPASS+"&account="+account+"&password="+password+"&name="+name+"&group="+GROUP+"&mainaccount="+MAINACCOUNT);
			logger.info("account="+account+"report="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseCreateAccountXML(result);
			int time=0;
			while(!resultObj.isSuccess()&&time<5){
				time++;
				account="1"+MyStringUtil.getRandom_123(8);
				result = HttpRequester.httpsget(SERVERURL+"createaccount?sa="+SA+"&sapass="+SAPASS+"&account="+account+"&password="+password+"&name="+name+"&group="+GROUP+"&mainaccount="+MAINACCOUNT);
				logger.info("account="+account+"report="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
				resultObj=parseCreateAccountXML(result);
			}
			if(resultObj.isSuccess()){
				return resultObj.getResultMsg();
			}else{
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deposit(String account,String amount,String credit) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"deposit?sa="+SA+"&sapass="+SAPASS+"&account="+account+"&amount="+amount+"&credit="+credit+"&currency=USD&remark=zidong");
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				logger.info("ResultCode:"+resultObj.getResultCode()+",ResultMsg:"+resultObj.getResultMsg());
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			logger.error("出现异常："+ ErrorCodePropertyHandler.errorException(e));
			throw new BusinessException(e);
		}
	}
	@Override
	public void setpassword(String account,String newpasswd) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"setpassword?sa="+SA+"&sapass="+SAPASS+"&account="+account+"&password="+newpasswd);
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public ResultObj queryaccount(String account) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"queryaccount?sa="+SA+"&sapass="+SAPASS+"&account="+account);
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseQueryAccountXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}else{
				return resultObj;
			}
			
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void setmarginrate(String account, String source) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"setmarginrate?requestid=2&sa="+SA+"&sapass="+SAPASS+"&account="+account+"&source="+source);
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void setcommissionrate(String account, String source) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"setcommissionrate?requestid=3&sa="+SA+"&sapass="+SAPASS+"&account="+account+"&source="+source);
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void setriskcontrol(String account, String source) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"setriskcontrol?requestid=4&sa="+SA+"&sapass="+SAPASS+"&account="+account+"&source="+source);
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public ResultObj withdrawall(String account) {
		try {
			String result = HttpRequester.httpsget(SERVERURL+"withdrawall?requestid=5&sa="+SA+"&sapass="+SAPASS+"&account="+account+"&remark=自动");
			logger.info("result="+new String(result.getBytes("ISO-8859-1"),"GB2312") );
			ResultObj resultObj=parseDepositXML(result);
			if(!resultObj.isSuccess()){
				throw new BusinessException(resultObj.getResultCode(), resultObj.getResultMsg());
			}else{
				return resultObj;
			}

		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}


	private static ResultObj parseCreateAccountXML(String xml){
		SAXReader saxreader = new SAXReader();
		try {
			InputStream sbs=new StringBufferInputStream(xml);
			Document doc = saxreader.read(sbs,"GB2312");
			Element root = doc.getRootElement();
			Element result = root.element("Result");
			Element error = result.element("Error");
			Element code =  error.element("Code");
			Element message =  error.element("Message");
			Element account = result.element("Account");
			if("0".equals(code.getText())){
				return new ResultObj(true,account.getText());
			}else{
				return new ResultObj(code.getText(),message.getText());
			}
		} catch (Exception e) {
			return new ResultObj(e);
		}
	}
	private static ResultObj parseDepositXML(String xml){
		SAXReader saxreader = new SAXReader();
		try {
			InputStream sbs=new StringBufferInputStream(xml);
			Document doc = saxreader.read(sbs,"GB2312");
			Element root = doc.getRootElement();
			Element result = root.element("Result");
			Element error = result.element("Error");
			Element code =  error.element("Code");
			Element message =  error.element("Message");
			if("0".equals(code.getText())){
				return new ResultObj(true,message.getText());
			}else{
				return new ResultObj(code.getText(),message.getText());
			}
		} catch (Exception e) {
			return new ResultObj(e);
		}
	}
	private static ResultObj parseQueryAccountXML(String xml){
		SAXReader saxreader = new SAXReader();
		try {
			InputStream sbs=new StringBufferInputStream(xml);
			Document doc = saxreader.read(sbs,"GB2312");
			Element root = doc.getRootElement();
			Element result = root.element("Result");
			Element error = result.element("Error");
			Element code =  error.element("Code");
			Element message =  error.element("Message");
			List<Element> summarys = result.elements("Summary");
			if("0".equals(code.getText())){
				List<Map<String,String>> resultMap=new ArrayList<Map<String,String>>();
				for(Element e:summarys){
					Map<String,String> tmp=new HashMap<String,String>();
					tmp.put("Currency", e.element("Currency").getText());
					tmp.put("Balance", e.element("Balance").getText());
					tmp.put("PreBalance", e.element("PreBalance").getText());
					tmp.put("Available", e.element("Available").getText());
					tmp.put("Margin", e.element("Margin").getText());
					tmp.put("MarginFrozen", e.element("MarginFrozen").getText());
					tmp.put("CommissionFrozen", e.element("CommissionFrozen").getText());
					tmp.put("PositionProfitFloat", e.element("PositionProfitFloat").getText());
					tmp.put("CloseProfitFloat", e.element("CloseProfitFloat").getText());
					tmp.put("Commission", e.element("Commission").getText());
					tmp.put("PositionProfit", e.element("PositionProfit").getText());
					tmp.put("CloseProfit", e.element("CloseProfit").getText());
					tmp.put("Deposit", e.element("Deposit").getText());
					tmp.put("Withdraw", e.element("Withdraw").getText());
					tmp.put("Credit", e.element("Credit").getText());
					tmp.put("BaseCapital", e.element("BaseCapital").getText());
					tmp.put("EverMaxBalance", e.element("EverMaxBalance").getText());
					tmp.put("MaxBalance", e.element("MaxBalance").getText());
					resultMap.add(tmp);
				}
				return new ResultObj(resultMap);
			}else{
				return new ResultObj(code.getText(),message.getText());
			}
		} catch (Exception e) {
			return new ResultObj(e);
		}
	}

}
