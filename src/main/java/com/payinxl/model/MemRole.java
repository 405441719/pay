package com.payinxl.model;



import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCode;
import com.payinxl.common.persistence.BaseEntity;
import com.payinxl.common.security.RSA;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by barry
 * Date:2017/1/10
 */
public class MemRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	private String userid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
