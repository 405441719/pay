package com.payinxl.common.http;

import com.payinxl.common.exception.ErrorCodePropertyHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @类功能说明：HttpConnectionManager类
 * @公司名称：
 * @作者：fengjiao.Chen
 * @创建时间：2012-8-30 下午17:51:38
 * @版本：V1.0
 */
public class HttpConnectionManager
{
    private static Logger logger = LoggerFactory.getLogger(HttpRequester.class);
    // private static HttpParams httpParams;
    // 普通HTTP
    private static ClientConnectionManager connectionManager;
    // 无证书验证实现HTTPS请求
    // private static HttpParams noCertHttpsParams;
    // 无证书验证实现HTTPS请求
    private static ClientConnectionManager noCertHttpsConnectionManager;
    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 300;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_PER_ROUTE = 30;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 5000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 10000;
    static
    {
        try
        {
            // httpParams = new BasicHttpParams();
            // // 设置连接超时时间
            // HttpConnectionParams.setConnectionTimeout(httpParams,
            // CONNECT_TIMEOUT);
            // // 设置读取超时时间
            // HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
            ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);
            // 设置最大连接数
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            // 设置每个路由最大连接数
            cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
            connectionManager = cm;

            // noCertHttpsParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
            // false);
            // HttpProtocolParams.setVersion(noCertHttpsParams,
            // HttpVersion.HTTP_1_1);
            SSLContext ctx;
            ctx = SSLContext.getInstance("TLS");
            X509ExtendedTrustManager tm = new X509ExtendedTrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry noCertHttpsSchemeRegistry = new SchemeRegistry();
            noCertHttpsSchemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            noCertHttpsSchemeRegistry.register(new Scheme("https", 443, ssf));
            ThreadSafeClientConnManager cm2 = new ThreadSafeClientConnManager(noCertHttpsSchemeRegistry);
            // 设置最大连接数
            cm2.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            // 设置每个路由最大连接数
            cm2.setDefaultMaxPerRoute(MAX_PER_ROUTE);
            noCertHttpsConnectionManager = cm2;
        }
        catch (Exception e)
        {
            logger.error(ErrorCodePropertyHandler.errorException(e));
        }
    }

    /**
     * 函数功能: 获取HttpClient
     * 
     * @author fengjiao.Chen 2012-8-30
     * @return HttpClient
     */
    // public static HttpClient getHttpClient()
    // {
    // return new DefaultHttpClient(connectionManager, httpParams);
    // }

    /**
     * 函数功能: 获取没有认证HttpClient
     * 
     * @author fengjiao.Chen 2012-8-30
     * @return HttpClient
     */
    public static HttpClient getNoCertHttpsClient(int connectTimeout, int readTimeout)
    {
        HttpParams noCertHttpsParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(noCertHttpsParams, CONNECT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(noCertHttpsParams, READ_TIMEOUT);
        return new DefaultHttpClient(noCertHttpsConnectionManager, noCertHttpsParams);
    }

    /**
     * 函数功能: 获取HttpClient
     * 
     * @author fengjiao.Chen 2012-8-30
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @return HttpClient
     */
    public static HttpClient getHttpClient(int connectTimeout, int readTimeout)
    {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, readTimeout);
        return new DefaultHttpClient(connectionManager, httpParams);
    }
}
