package com.payinxl.model;
import com.payinxl.common.persistence.BaseEntity;
import com.payinxl.common.util.IdGen;
import com.payinxl.common.util.MyStringUtil;
import com.payinxl.common.util.ValidUtil;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.Date;

/**
 * Created by barry
 * Date:2017/1/10
 */
public class MemUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public static final int LOCKED=1;
	private String parentid;
	private String usercode;
	private String username;
	private String password;
	private String paypwd;
	private String description;
	private String qq;
	private String email;
	private String mobile;
	private Integer status;
	private Integer locked;
	private Date registertime;
	private String registerurl;
	private Date logintime;
	private String loginip;
	private String registerip;
	private Date lastlogintime;
	private String lastloginip;
	private String sdkey;
	public MemUser() {
	}
	public MemUser(MemUser memUser) {
		this.username = memUser.getUsername();
		this.password = memUser.getPassword();
		this.setId(memUser.getId());
	}
	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getRegisterurl() {
		return registerurl;
	}

	public void setRegisterurl(String registerurl) {
		this.registerurl = registerurl;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getRegisterip() {
		return registerip;
	}

	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}

	public String getSdkey() {
		return sdkey;
	}

	public void setSdkey(String sdkey) {
		this.sdkey = sdkey;
	}
}
