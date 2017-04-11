package com.payinxl.model;



import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCode;
import com.payinxl.common.persistence.BaseEntity;
import com.payinxl.common.security.RSA;
import com.payinxl.common.util.MyStringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by barry
 * Date:2017/1/10
 */
public class MemAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final int LOCKED=1;
	private BigDecimal balance;
	private BigDecimal subbalance;
	private BigDecimal addbalance;
	private String valcode;
	private Date updatetime;
	private Integer locked;//0,正常，1冻结

	public String getValcode() {
		return valcode;
	}

	public void setValcode(String valcode) {
		this.valcode = valcode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getSubbalance() {
		return subbalance;
	}

	public void setSubbalance(BigDecimal subbalance) {
		this.subbalance = subbalance;
	}

	public BigDecimal getAddbalance() {
		return addbalance;
	}

	public void setAddbalance(BigDecimal addbalance) {
		this.addbalance = addbalance;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public  void validbalance() throws BusinessException {
		BigDecimal enbalance=new BigDecimal(RSA.getInstance().decrypt(this.getValcode()));
		if(enbalance.compareTo(this.getBalance())!=0){
			throw new BusinessException(ErrorCode.ACCOUNT_RISK,"账户存在异常");
		}
		if(LOCKED==this.getLocked()){
			throw new BusinessException(ErrorCode.ACCOUNT_LOCKED,"账户被冻结");
		}
	}
}
