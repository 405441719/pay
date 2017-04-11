package com.payinxl.common.exception;

import java.io.Serializable;

/**
 * @类功能说明：业务异常
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2012-12-30 下午10:34:24
 * @版本：V1.0
 */
public class BusinessException extends CustomizeException implements Serializable
{
    private static final long serialVersionUID = 8021729977511593857L;

    private String errorId;
    private String errorMsg;

    @Override
    public String getErrorId()
    {
        return this.errorId;
    }

    @Override
    public void setErrorId(String errorId)
    {
        this.errorId = errorId;
    }

    @Override
    public String getErrorMsg()
    {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public BusinessException()
    {
        super();
    }

    public BusinessException(Exception e)
    {
        super(e);
        if(e instanceof BusinessException){
        	this.setErrorId(((BusinessException)e).getErrorId());
            this.setErrorMsg(((BusinessException)e).getErrorMsg());
        }else{
        	this.setErrorId(ErrorCode.ERROR);
            this.setErrorMsg(e.getMessage());
        }
    }

    public BusinessException(String errorId)
    {
        super(errorId);
    }

    public BusinessException(String errorId, String errormsg)
    {
        super(errorId, errormsg);
    }

    @Override
    public Throwable fillInStackTrace()
    {
        return null;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
        sb.append("执行异常：[Code=").append(this.getErrorId()).append(",Msg=").append(this.getErrorMsg()).append("]");
        return sb.toString();
    }
}
