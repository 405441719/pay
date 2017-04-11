package com.payinxl.model;



import com.payinxl.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户充值通道
 */
public class MemChannel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String userid;
	private Integer type;
	private String channelid;
	private Integer status;//0:开启，1禁用
	private BigDecimal fee;//费率，千分位

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
