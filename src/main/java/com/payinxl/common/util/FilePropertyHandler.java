package com.payinxl.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

//自定义配置加载处理器
public class FilePropertyHandler extends PropertyPlaceholderConfigurer
{
    private static Properties PROPERTY = new Properties();
    private static Logger logger = LoggerFactory.getLogger(FilePropertyHandler.class);

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException
    {
        logger.info("开始加载自定义配置信息");
        super.processProperties(beanFactory, props);
        loadErrorCodes(props);
        logger.info("自定义配置信息加载完成");

    }
    public static String getValue(String key)
    {
        String value = PROPERTY.getProperty(key);

        if (ValidUtil.isEmpty(value))
        {
            logger.warn("Not value for key:" + key);
        }
        return value;
    }


    private void loadErrorCodes(Properties props)
    {
        synchronized (PROPERTY)
        {
            Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<Object, Object> entry = it.next();
                logger.info("自定义配置: " + entry.getKey() + " " + entry.getValue());
                PROPERTY.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
