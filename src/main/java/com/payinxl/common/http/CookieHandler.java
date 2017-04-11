package com.payinxl.common.http;

import org.apache.http.impl.client.DefaultHttpClient;

public abstract class CookieHandler
{
    /**
     * 执行请求之前操作
     * 
     * @param http
     */
    public void before(DefaultHttpClient http)
    {
    }

    /**
     * 执行请求之后操作
     * 
     * @param http
     */
    public void after(DefaultHttpClient http)
    {
    }
}
