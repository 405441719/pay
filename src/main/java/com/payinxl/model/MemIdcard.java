package com.payinxl.model;



import com.payinxl.common.persistence.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by barry
 * Date:2017/1/10
 */
public class MemIdcard extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String userid;
	private String idcard;
	private String realname;
	private Date updatetime;
	private Integer status;
	private String remarks;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
