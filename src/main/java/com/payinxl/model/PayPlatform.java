package com.payinxl.model;



import com.payinxl.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 用户日志表
 */
public class PayPlatform extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String remarks;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
