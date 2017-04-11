package com.payinxl.model;


import com.payinxl.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by barry
 * Date:2017/1/10
 */
public class MemAccountTrace extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String userid;
	private BigDecimal money;
	private BigDecimal balance;
	private Integer actiontype;
	private String orderno;
	private String remarks;
	private Date createtime;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money!=null?money.setScale(2,BigDecimal.ROUND_HALF_UP):null;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance =  balance!=null?balance.setScale(2,BigDecimal.ROUND_HALF_UP):null;
	}
	public Integer getActiontype() {
		return actiontype;
	}
	public void setActiontype(Integer actiontype) {
		this.actiontype = actiontype;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
