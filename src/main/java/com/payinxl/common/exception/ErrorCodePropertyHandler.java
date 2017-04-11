package com.payinxl.common.exception;


import com.payinxl.common.util.ValidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @公司名称：Noname
 * @作者：Panda
 * @创建时间：2012-12-18 下午10:58:46
 * @版本：V1.0
 */
public class ErrorCodePropertyHandler extends PropertyPlaceholderConfigurer
{
    private static Properties ERRMSG_PROPERTY = new Properties();
    private static Logger logger = LoggerFactory.getLogger(ErrorCodePropertyHandler.class);

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException
    {
        logger.info("开始加载自定义异常配置信息");
        super.processProperties(beanFactory, props);

        loadErrorCodes(props);
        logger.info("自定义异常配置信息加载完成");
    }

    /**
     * @函数功能:根据异常编号找出异常信息并返回，如果不存在则返回异常编号
     * @author 俞少坊 2012-11-22
     * @param errId
     * @return
     * @return String
     * @throws
     */
    public static String getErrMsg(String errId)
    {
        String errMsg = ERRMSG_PROPERTY.getProperty(errId);

        if (ValidUtil.isEmpty(errMsg))
        {
            logger.warn("Warning! No Message Had Defined For errorId:" + errId);
            errMsg = "其他错误";
        }
        return errMsg;
    }

    /**
     * @函数功能:根据异常编号找出异常信息并进行参数格式化后返回，如果不存在则返回异常编号
     * @author 俞少坊 2012-11-22
     * @param errId
     * @param args
     * @return
     * @return String
     * @throws
     */
    public static String getErrMsg(String errId, Object... args)
    {
        String errMsg = ERRMSG_PROPERTY.getProperty(errId);

        if (ValidUtil.isEmpty(errMsg))
        {
            logger.warn("Warning! No Message Had Defined For errorId:" + errId);
            errMsg = errId;
        }
        else
        {
            errMsg = String.format(errMsg, args);
        }
        return errMsg;
    }

    /**
     * 函数功能：获取异常堆栈信息
     * 
     * @author Panda 2012-12-18 下午11:28:45
     * @param e
     * @return
     */
    public static String errorException(Exception e)
    {
    	 StackTraceElement[] ste = e.getStackTrace();
         if (ste == null)
         {
             return null;
         }
         StringBuffer sb = new StringBuffer();
         if (e instanceof CustomizeException)
         {
             // "执行异常：[" + errorId + " " + this.getErrorMsg() + "]"
             sb.append("Code:"+((CustomizeException) e).getErrorId()+" Msg:"+((CustomizeException) e).getErrorMsg());
         }else{
        	 sb.append(e.getMessage());
         }
         for (int i = 0; i < ste.length; i++)
         {
             sb.append(ste[i].toString()).append("\n ");
         }
         return sb.toString();
    }

    private void loadErrorCodes(Properties props)
    {
        synchronized (ERRMSG_PROPERTY)
        {
            Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<Object, Object> entry = it.next();
                logger.debug("自定义异常配置: " + entry.getKey() + " " + entry.getValue());
                ERRMSG_PROPERTY.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
