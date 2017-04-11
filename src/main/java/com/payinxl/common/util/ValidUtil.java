package com.payinxl.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil
{
    /**
     * IP地址正则表达式
     * ((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\
     * d|[01]?\\d?\\d))
     */
    public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
    /**
     * 手机号码:13333333333
     */
    public static final String MOBILE = "^(((1[0-9]{2}))+\\d{8})$";

    /**
     * 邮箱:service@ofpay.com
     */
    public static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * 邮箱前缀
     */
    public static final String EMAILPRE = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*";

    /**
     * 固定电话:025-88888888
     */
    public static final String TELL = "^\\d{3,4}-\\d{7,8}$";

    /**
     * 身份证号码:15 或 17为数字 + 字母
     */
    public static final String IDNO = "^(\\d{15}|\\d{17}[A-Za-z0-9])$";

    /**
     * 判断是手机号
     */
    public static final String PRICEMODULE = "5|6|7";

    /**
     * 是否是数字
     */
    public static final String NUMBER = "\\d*";

    /**
     * 是否是字母
     */
    public static final String STRING = "[A-Za-z]*";

    /**
     * 是否是IP地址段
     */
    public static final String SHORTIP = "25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))";

    /**
     * 数字并可以存在小数点
     */
    public static final String NUMWITHPOINT = "^\\d+[\\.]?\\d*$";

    /**
     * 数字并可以存在小数点并且可为负数
     */
    public static final String NUMWITHPOINTORNEGTIVE = "^[-]?\\d+[\\.]?\\d*$";

    /**
     * 手机号码或者为空
     */
    public static final String MOBILEOREMPTY = "^(((14[0-9]{1})|(13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})+|$";

    /**
     * 邮箱:service@ofpay.com或者为空
     */
    public static final String EMAILOREMPTY = "^([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))+|$";

    /**
     * 是否是数字且不能为空
     */
    public static final String NUMBERNOTEMPTY = "\\d+";

    /**
     * 是否是MAC地址
     */
    public static final String MAC = "[0-9A-F]{12}";

    /**
     * 收银员班次
     */
    public static final String EMPCONVERTDUTY = "1|2|3";

    /**
     * 交接班时 是否是主账号
     */
    public static final String ACCOUNTTYPE = "0|1";

    /**
     * 短信验证码
     */
    public static final String SMS = "\\d{6}";

    /**
     * 邮箱验证码
     */
    public static final String EMAILCODE = "\\d{6}";

    /**
     * 移动密保序列号
     */
    public static final String TOKENNO = "\\d{13}";

    /**
     * 移动密保序口令
     */
    public static final String TOKENCODE = "\\d{6}";

    /**
     * 打印样式 1-58mm热敏打印 2-普通打印样板
     */
    public static final String PRINTSTYLE = "1|2";

    /**
     * 安全策略类型
     */
    public static final String PLOYTYPE = "1|2|3";

    /**
     * 安全策略值
     */
    public static final String PLOYSTAT = "0|1";

    /**
     * 数字，只有一位小数点
     */
    public static final String PAYAMOUNT = "^\\d+[\\.]?\\d{1}$";

    public static final String PAYRATE = "";

    public static final String PAYROUNDTYPE = "0|1|2|3";

    public static final String SECURITYQ1 = "[1-5]{1}";
    public static final String SECURITYQ2 = "[6-9]{1}||10";
    public static final String SECURITYQ3 = "1[1-5]{1}";

    public static final String CREDIT = "^\\d+[\\.]?\\d*$||''";

    public static final String CREDIT2 = "^\\d+[\\.]?\\d*$||-\\d+[\\.]?\\d*$||''";
    /**
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";

    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    
    
    /**
     * 密码验证
     */
    public static final String PASSWD = ".{6,}";
    
    /**
     * 用户名验证
     */
    public static final String USERNAME = "([0-9]|[A-Za-z]){3,}";
    /**
     * 判断字段是否为Email 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isEmail(String str)
    {
        if (null == str || str.trim().length() <= 0)
            return false;
        return Regular(str, EMAIL);
    }

    /**
     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str)
    {
        return Regular(str, DOUBLE);
    }

    /**
     * 判断字段是否为INTEGER 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str)
    {
        return Regular(str, INTEGER);
    }

    /**
     * 判断是否为IP地址 符合返回ture
     * 
     * @param str
     * @return boolean
     */
    public static boolean isIpaddress(String str)
    {
        return Regular(str, IPADDRESS);
    }

    public static boolean validPasswd(String str)
    {
    	if(str==null){
    		return false;
    	}
        return Regular(str.trim(), PASSWD);
    }
    public static boolean validUsername(String str)
    {
    	if(str==null){
    		return false;
    	}
        return Regular(str.trim(), USERNAME);
    }
    public static void validNotUndefined(final String... params)
    {
        for (String param : params)
        {
            if (StringUtils.isEmpty(param) || "undefined".equals(param))
            {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void validNotNull(final Object... params)
    {
        for (Object param : params)
        {
            if (param == null)
            {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void validNotEmpty(final String... params)
    {
        for (String param : params)
        {
            if (StringUtils.isEmpty(param))
            {
                throw new IllegalArgumentException("参数非法");
            }
        }
    }

    public static void validNotUndefined(Map<String, String> map, final String... params)
    {
        for (String param : params)
        {
            String val = map.get(param);
            if (StringUtils.isEmpty(val) || "undefined".equals(val))
            {
                throw new IllegalArgumentException("参数非法");
            }
        }
    }
    /**
     * 校验参数不为空
     * 
     * @author shuzhiqin
     * @date 2012-7-9 上午9:45:52
     * @param mobileno手机号码 ,
     * @return
     */
    public static boolean isUnEmpty(String param)
    {
    	
    	 if (param==null||"".equals(param.trim())||StringUtils.isEmpty(param) || "undefined".equals(param))
         {
             return false;
         }else{
        	 return true;
         }
    }
    /**
     * 校验参数为空
     * 
     * @author shuzhiqin
     * @date 2012-7-9 上午9:45:52
     * @param mobileno手机号码 ,
     * @return
     */
    public static boolean isEmpty(String param)
    {
    	 if (StringUtils.isEmpty(param) || "undefined".equals(param)||"".equals(param.trim()))
         {
             return true;
         }else{
        	 return false;
         }
    }
    
    /**
     * 校验参数为空
     * 
     * @author shuzhiqin
     * @date 2012-7-9 上午9:45:52
     * @param mobileno手机号码 ,
     * @return
     */
    public static boolean isEmpty(List list)
    {
    	 if (list==null ||list.size()==0||list.isEmpty())
         {
             return true;
         }else{
        	 return false;
         }
    }
    public static boolean isUnEmpty(List list)
    {
    	 if (list==null ||list.size()==0||list.isEmpty())
         {
             return false;
         }else{
        	 return true;
         }
    }
    /**
     * 校验是否手机号码
     * 
     * @author shuzhiqin
     * @date 2012-7-9 上午9:45:52
     * @param mobileno手机号码 ,
     * @return
     */
    public static boolean isMobile(String mobileno)
    {
        return Regular(mobileno, MOBILE);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     * 
     * @param str 匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    public static boolean Regular(String str, String pattern)
    {
        if (StringUtils.isEmpty(str))
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 比较两个数字字符串大小 str1 大于 str2 返回 true 否则返回 false
     * 
     * @param str 匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    public static boolean compareByStr(String str1, String str2)
    {
    	try{
    		float f1= Float.valueOf(str1);
    		float f2= Float.valueOf(str2);
    		if(f1>f2){
    			return true;
    	    }
    	}catch(Exception e){
    		return false;
    	}
        return false;
    }
    
    /**
     * 比较两个数字字符串是否相等 返回 true 否则返回 false
     * 
     * @param str 匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    public static boolean isEquery(String str1, String str2)
    {
    	if(isUnEmpty(str1)){
    		return str1.equals(str2);
    	}
        return false;
    }
    
    public static void main(String[] args)
    {
/*        System.out.println(Regular("1001.", NUMWITHPOINT));
        System.out.println(new BigDecimal("1001."));*/
    	System.out.println("".equals(null));
    }

}
