package com.payinxl.trade;

import com.payinxl.common.http.ResultObj;

/**
 * Created by Administrator on 2017/2/28.
 */
public interface TradeChannel {
    //开户 成功返回账号，失败抛异常
    String createaccount(String password,String name);
    //出入金，amount 总金额(劣后+优先)；credit 总金额(优先)。
    void deposit(String account,String amount,String credit);
    //查询账户资金
    ResultObj queryaccount(String account);
    //设置保证金率 account:待设置保证金率的子账号 source:参考账号
    void setmarginrate(String account,String source);
    //设置手续费率 account:待设置保证金率的子账号 source:参考账号
    void setcommissionrate(String account,String source);
    //设置风控 account:待设置保证金率的子账号 source:参考账号
    void setriskcontrol(String account,String source);
    //全部出金
    ResultObj withdrawall(String account);
    //修改信管家密码
    void setpassword(String account,String newpwd);
}
