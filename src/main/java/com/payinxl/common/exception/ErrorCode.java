package com.payinxl.common.exception;

/**
 * @类功能说明：统一业务异常ErrorId,采用8位方式前三位分配给modual唯一ID（字母数字组合）后五位为modual内部分配
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2012-12-30 下午8:07:07
 * @版本：V1.0
 */
public interface ErrorCode
{
	//系统异常
    String ERROR = "18000";
    //用户不存在
    String USER_NOT_EXIST="18001";
    //余额不足
    String BANLANCE_NOT_ENOUGH="18002";
    //XX参数格式不正确
    String PARAM_ILLEGAL="18002";
    //XX已存在
    String OBJ_EXIST="18003";
 
    //用户被锁定
    String USER_LOCKED="18005";
    //账户被冻结
    String ACCOUNT_LOCKED="18006";
    //风险账户
    String ACCOUNT_RISK="18007";
    //业务异常
    String BUSINESS_EXCEPTION = "18008";

    //手机号码已存在
    String MOBILE_EXIST = "18009";

}