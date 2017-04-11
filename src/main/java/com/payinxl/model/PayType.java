package com.payinxl.model;



import com.payinxl.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 用户日志表
 */
public class PayType extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer sort;
	private String channelid;
	private Integer status;
	private BigDecimal defaultfee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public BigDecimal getDefaultfee() {
		return defaultfee;
	}

	public void setDefaultfee(BigDecimal defaultfee) {
		this.defaultfee = defaultfee;
	}
}
