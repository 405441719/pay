package com.payinxl.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @类功能说明：自定义异常基类
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2012-12-18 下午11:14:55
 * @版本：V1.0
 */
public abstract class CustomizeException extends RuntimeException
{
    private static final long serialVersionUID = 9219946654459601294L;
    //transient目的在于当通过dubbo方式对外抛出异常时，序列化的的时候出异常
    //com.alibaba.com.caucho.hessian.io.HessianProtocolException:
    //'org.slf4j.impl.Log4jLoggerAdapter' could not be instantiated
    //因此不对logger进行序列化，以规避此类问题
    private transient Logger logger = LoggerFactory.getLogger(getClass());

    public abstract String getErrorId();

    public abstract void setErrorId(String errorId);

    public abstract String getErrorMsg();

    public abstract void setErrorMsg(String errorMsg);

    public CustomizeException()
    {
        super();
    }

    public CustomizeException(Exception e)
    {
        super(e);
        logger.debug("执行异常：[" + e.getMessage() + "]");
    }

    public CustomizeException(String errorId)
    {
        super(ErrorCodePropertyHandler.getErrMsg(errorId));
        this.setErrorId(errorId);
        this.setErrorMsg(ErrorCodePropertyHandler.getErrMsg(errorId));
        logger.debug("执行异常：[" + errorId + " " + this.getErrorMsg() + "]");
    }
    public CustomizeException(String errorId, String errorMsg)
    {
        super(errorMsg);
        this.setErrorId(errorId);
        this.setErrorMsg(errorMsg);
        logger.debug("执行异常：[" + errorId + " " + this.getErrorMsg() + "]");
    }
    @Override
    public String toString()
    {
        StackTraceElement[] ste = this.getStackTrace();
        if (ste == null)
        {
            return null;
        }
        StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
        sb.append("执行异常：[").append(this.getErrorId()).append(" ").append(this.getErrorMsg()).append(" ")
                .append(this.getMessage()).append("]\n");
        for (int i = 0; i < ste.length; i++)
        {
            sb.append(ste[i].toString() + "\n ");
        }
        return sb.toString();
    }

}
