package com.payinxl.model;
import com.payinxl.common.persistence.BaseEntity;
public class BankCodes extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String code;
	private String bankname;
	private Integer sort;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
