package com.payinxl.common.http;

import com.payinxl.common.exception.ErrorCodePropertyHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @类功能说明：HttpConnectionManager类，请求发送工具类 默认编码UTF-8
 * @公司名称：Noname
 * @作者：Miner
 * @创建时间：2012-12-19 下午02:18:27
 * @版本：V1.0
 */
public class HttpRequester
{
    private static final int CONNECT_TIMEOUT = 100000;
    private static final int READ_TIMEOUT = 100000;
    private static Logger logger = LoggerFactory.getLogger(HttpRequester.class);

    /**
     * @函数功能：使用连接池方式HttpClient
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:18:37
     * @param url
     * @return
     * @throws Exception
     */
    public static String post(String url) throws Exception
    {
        return post(url, null, null, CONNECT_TIMEOUT, READ_TIMEOUT, null);
    }

    public static String post(String url, Mode mode) throws Exception
    {
        return post(url, null, null, CONNECT_TIMEOUT, READ_TIMEOUT, mode);
    }

    /**
     * @函数功能： 使用连接池方式HttpClient，通过HashMap指定参数
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:18:55
     * @param url
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static String post(String url, Object paramObject) throws Exception
    {
        return post(url, paramObject, null, CONNECT_TIMEOUT, READ_TIMEOUT, null);
    }

    public static String post(String url, Object paramObject, Mode mode) throws Exception
    {
        return post(url, paramObject, null, CONNECT_TIMEOUT, READ_TIMEOUT, mode);
    }

    /**
     * 函数功能：
     * 
     * @author Panda 2013-1-10 下午8:15:30
     * @param url
     * @param paramObject
     * @param charset
     * @return
     * @throws Exception
     */
    public static String post(String url, Object paramObject, String charset) throws Exception
    {
        return post(url, paramObject, charset, CONNECT_TIMEOUT, READ_TIMEOUT, null);
    }

    public static String post(String url, Object paramObject, String charset, Mode mode) throws Exception
    {
        return post(url, paramObject, charset, CONNECT_TIMEOUT, READ_TIMEOUT, mode);
    }

    /**
     * @函数功能：自定义的HttpClient，可设定超时时间
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:22:05
     * @param url
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    public static String post(String url, int connectTimeout, int readTimeout) throws Exception
    {
        return post(url, null, null, connectTimeout, readTimeout, null);
    }

    public static String post(String url, int connectTimeout, int readTimeout, Mode mode) throws Exception
    {
        return post(url, null, null, connectTimeout, readTimeout, mode);
    }

    /**
     * @函数功能：自定义的HttpClient，可设定超时时间及请求参数
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:45:41
     * @param url
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    public static String post(String url, Object paramObject, int connectTimeout, int readTimeout) throws Exception
    {
        return post(url, paramObject, null, connectTimeout, readTimeout, null);
    }

    public static String post(String url, Object paramObject, int connectTimeout, int readTimeout, Mode mode)
            throws Exception
    {
        return post(url, paramObject, null, connectTimeout, readTimeout, mode);
    }

    /**
     * @函数功能：可设定超时时间、请求参数、字符集
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:46:07
     * @param url
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    public static String post(String url, Object paramObject, String charset, int connectTimeout, int readTimeout,
                              Mode mode) throws Exception
    {
        if (StringUtils.isEmpty(charset))
        {
            charset = HTTP.UTF_8;
        }
        if (connectTimeout <= 0)
        {
            connectTimeout = CONNECT_TIMEOUT;
        }
        if (readTimeout <= 0)
        {
            readTimeout = READ_TIMEOUT;
        }
        HttpClient httpClient = null;
        if (mode == null || mode == Mode.HTTP)
        {
            httpClient = HttpConnectionManager.getHttpClient(connectTimeout, readTimeout);
        }
        else if (mode == Mode.NOCERTHTTPS)
        {
            httpClient = HttpConnectionManager.getNoCertHttpsClient(connectTimeout, readTimeout);
        }
        else
        {
            throw new IllegalArgumentException("mode 非法");
        }
        return createHttpPostAndPostToTarget(httpClient, url, paramObject, charset);
    }
    
    public static String postWithCookie(String url, Object paramObject, CookieHandler handler) throws Exception
    {
        DefaultHttpClient httpClient = (DefaultHttpClient) HttpConnectionManager.getHttpClient(CONNECT_TIMEOUT,
            READ_TIMEOUT);
        handler.before(httpClient);
        String result= createHttpPostAndPostToTarget(httpClient, url, paramObject, HTTP.UTF_8);
        handler.after(httpClient);
        return result;
    }

    // /**
    // * @函数功能：使用无认证的连接池方式HttpClient
    // * @作者：Miner
    // * @创建时间：2012-12-19 下午02:19:18
    // * @param url
    // * @param paramMap
    // * @param charset
    // * @return
    // * @throws Exception
    // */
    // public static String noCertHttpsPost(String url, Object paramObject,
    // String charset) throws Exception
    // {
    // HttpClient httpClient = HttpConnectionManager.getNoCertHttpsClient();
    // return createHttpPostAndPostToTarget(httpClient, url, paramObject,
    // charset);
    // }

    /**
     * 函数功能：
     * 
     * @author Panda 2012-12-22 下午4:30:33
     * @param httpClient
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    private static String createHttpPostAndPostToTarget(HttpClient httpClient, String url, Object paramObject,
                                                        String charset) throws Exception
    {
        HttpPost httpPost = createHttpPost(url, paramObject, charset);
        return postToTarget(httpClient, httpPost);
    }

    /**
     * @函数功能：创建HTTPPOST，并塞入参数
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:46:29
     * @param url
     * @param charset
     * @return
     * @throws Exception
     */
    public static HttpPost createHttpPost(String url, Object paramObject, String charset) throws Exception
    {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = convertToNameValuePair(paramObject);
        if (nvps != null && nvps.size() > 0)
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
        }
        return addHeader(httpPost, charset);
    }

    private static HttpPost addHeader(HttpPost httpPost, String charset)
    {
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        httpPost.addHeader("Accept-Language", "zh-cn");
        httpPost.addHeader("User-Agent",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
        return httpPost;
    }

    public static String postToTarget(HttpClient httpClient, HttpPost httpPost) throws Exception
    {
        InputStream in = null;
        try
        {
            StringBuffer logbuf = new StringBuffer();
            logbuf.append("HttpClient Url:");
            logbuf.append(httpPost.getURI());
            logbuf.append((char) HTTP.LF);
            logbuf.append("HttpClient Post:");
            if (httpPost.getEntity() == null)
            {
                logbuf.append("null");
            }
            else
            {
                logbuf.append(EntityUtils.toString(httpPost.getEntity()));
            }
            String returnMsg = null;
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null)
            {
                in = httpEntity.getContent();
                returnMsg = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            logbuf.append((char) HTTP.LF);
            logbuf.append("HttpClient Recv:");
            logbuf.append(returnMsg);
            logger.info(logbuf.toString());
            httpPost.abort();
            return returnMsg;
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    logger.error("HttpClient:" + ErrorCodePropertyHandler.errorException(e));
                }
            }
        }
    }

    private static List<NameValuePair> convertToNameValuePair(Object paramObject) throws IllegalArgumentException,
            IllegalAccessException
    {
        if (paramObject == null)
        {
            return null;
        }
        List<NameValuePair> nvps = null;
        if (paramObject instanceof Map)
        {
            Map<String, String> paramMap = (Map<String, String>) paramObject;
            nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet())
            {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        else
        {
            nvps = new ArrayList<NameValuePair>();
            Class c = paramObject.getClass();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields)
            {
                field.setAccessible(true);
                Object value = field.get(paramObject);
                if (value != null)
                {
                    String key = field.getName();
                    nvps.add(new BasicNameValuePair(key, value.toString()));
                }
            }
        }
        return nvps;
    }

    /**
     * @函数功能：HttpClient直接连接接口，包含上传文件
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:46:43
     * @param url
     * @param requestMap
     * @param file
     * @param coding
     * @return
     * @throws Exception
     */
    public static String postFile(String url, HashMap<String, String> requestMap, File file, String coding)
            throws Exception
    {
        String returnMsg = "";
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            HttpPost httpPost = new HttpPost(URLEncoder.encode(url, HTTP.UTF_8));
            // 对请求的表单域进行填充
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", new FileBody(file));
            // 先迭代HashMap
            for (Map.Entry<String, String> entry : requestMap.entrySet())
            {
                reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName(coding)));
            }
            httpPost.setEntity(reqEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null)
            {
                returnMsg = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            httpPost.abort();
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return returnMsg;
    }

    /**
     * @函数功能：get方式
     * @作者：Miner
     * @创建时间：2012-12-19 下午02:46:54
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url) throws Exception
    {
        String returnMsg = "";
        InputStream in = null;
        try
        {
            HttpClient httpClient = HttpConnectionManager.getHttpClient(CONNECT_TIMEOUT, READ_TIMEOUT);
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            // 如果返回状态部位200,抛出异常
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode)
            {
                throw new Exception("HTTP状态:" + statusCode);
            }
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            if (httpEntity != null)
            {
                in = httpEntity.getContent();
                returnMsg = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            StringBuffer logbuf = new StringBuffer();
            logbuf.append("HttpClient Url:");
            logbuf.append(url);
            logbuf.append((char) HTTP.LF);
            logbuf.append("HttpClient Recv:");
            logbuf.append(returnMsg);
            logger.info(logbuf.toString());
            httpGet.abort();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    logger.error("HttpClient:" + ErrorCodePropertyHandler.errorException(e));
                }
            }
        }
        return returnMsg;
    }
    public static String sendPostStreamHandler(String iAddress, String iCommString) {
    	return sendPostStreamHandler(iAddress, iCommString, null);
    }
    
    public static Map<String,String> getJsonType(){
    	Map<String,String> maps=new HashMap<String, String>();
    	maps.put("Content-Type", "application/json; charset=UTF-8");
    	return maps;
    }
    public static String sendPostStreamHandler(String iAddress, String iCommString, Map<String,String> reqpropertys) {
		String _oRecString = null;
		URL _url = null;
		HttpURLConnection _urlConn = null;
		OutputStream _out = null;
		InputStreamReader _isr = null;
		InputStream _is = null;
		BufferedReader _br = null;
		try {
			_url = new URL(iAddress); // 根据数据的发送地址构建URL
			_urlConn = (HttpURLConnection) _url.openConnection(); // 打开链接
			_urlConn.setConnectTimeout(100000); // 链接超时设置为100秒
			_urlConn.setReadTimeout(100000); // 读取超时设置100秒
			_urlConn.setRequestMethod("POST"); // 链接相应方式为post
			if(reqpropertys!=null){
				Set<String> keys= reqpropertys.keySet();
				for(String key:keys){
					_urlConn.setRequestProperty(key,reqpropertys.get(key));
				}
			}else{
				_urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
			_urlConn.setDoOutput(true);
			_urlConn.setDoInput(true);
			_out = _urlConn.getOutputStream();
			_out.write(iCommString.getBytes("UTF-8"));
			_out.flush();
			_is = _urlConn.getInputStream();
			_isr = new InputStreamReader(_is, "UTF-8");
			_br = new BufferedReader(_isr);
			StringBuffer _tempSB = new StringBuffer();
			String _tempStr = null;
			while ((_tempStr = _br.readLine()) != null) {
				_tempSB.append(_tempStr +"\r\n");
			}
			_oRecString = _tempSB.toString().trim();
			logger.debug("路由到" + iAddress + "?" + iCommString + "成功，返回：" + _oRecString);
		} catch (Exception e) {
			logger.error("server主动推送请求地址错误：" + e.getMessage(), e);
		} finally {
			try {
				if (_out != null) {
					_out.close();
				}
				if (_br != null) {
					_br.close();
				}
				if (_isr != null) {
					_isr.close();
				}
				if (_is != null) {
					_is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			if (_urlConn != null) {
				_urlConn.disconnect();
			}
		}
		return _oRecString;
	}
    public static String httpsget(String url){
        try {

            HttpClient httpclient = new DefaultHttpClient();
            //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("TLSv1.1");
            //Implementation of a trust manager for X509 certificates
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
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(new AllowAllHostnameVerifier());
            ClientConnectionManager ccm = httpclient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            sr.register(new Scheme("https", 443, ssf));
            HttpGet httpget = new HttpGet(url);
            System.out.println("REQUEST:" + httpget.getURI());
            ResponseHandler responseHandler = new BasicResponseHandler();
            String responseBody= (String) httpclient.execute(httpget, responseHandler);
            return responseBody;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }
    public static void main(String args[]) throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mode", "r");
        TestPostPojo testPostPojo = new TestPostPojo();
        testPostPojo.setUsercode("A11111");
        testPostPojo.setCardno(11111111);
        testPostPojo.setOrderno("orderno");
        testPostPojo.setMode("r");
        System.err.println(post("http://card.pay.ofpay.com/rcvcard.do", testPostPojo));
//        System.err.println(post("https://card.pay.ofpay.com:8443/rcvcard.do", testPostPojo, HTTP.UTF_8,
//            Mode.NOCERTHTTPS));
//        System.err.println(post("https://card.pay.ofpay.com:8443/rcvcard.do", map, HTTP.UTF_8, Mode.NOCERTHTTPS));
//        System.err.println(get("http://card.pay.ofpay.com/rcvcard.do"));
    }
}
