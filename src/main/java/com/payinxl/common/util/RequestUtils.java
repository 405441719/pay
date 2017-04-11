package com.payinxl.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;

public class RequestUtils
{
    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 获取客户端ip
     */
    public static String getIp(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        logger.debug("X-Real-IP " + ip);
        if (!checkIP(ip))
        {
            ip = request.getHeader("REMOTE-HOST");
            logger.debug("REMOTE-HOST " + ip);
        }
        String xip = request.getHeader("x-forwarded-for");
        logger.debug("x-forwarded-for " + xip);
        String firstXip = getFirstIP(xip);
        logger.debug("x-forwarded-for firstip " + firstXip);
        if (!StringUtils.equals(ip, firstXip))
        {
            logger.warn("X-Real-IP is " + ip + " But x-forwarded-for is " + xip);
            ip = firstXip;
        }
        if (checkIP(ip))
        {
            return ip;
        }
        if (checkIP(firstXip))
        {
            return firstXip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        logger.debug("Proxy-Client-IP " + ip);
        if (checkIP(ip))
        {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client");
        logger.debug("WL-Proxy-Client " + ip);
        if (checkIP(ip))
        {
            return ip;
        }
        ip = request.getHeader("HTTP_CLIENT_IP");
        logger.debug("HTTP_CLIENT_IP " + ip);
        if (checkIP(ip))
        {
            return ip;
        }
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        logger.debug("HTTP_X_FORWARDED_FOR " + ip);
        if (checkIP(ip))
        {
            return ip;
        }
        ip = request.getRemoteAddr();
        logger.debug("getRemoteAddr " + ip);
        return ip;
    }

    private static boolean checkIP(String ip)
    {
        if (StringUtils.isNotBlank(ip) && ip.split("\\.").length == 4)
        {
            return true;
        }
        return false;
    }

    /**
     * 获取请求路径
     * 
     * @param request
     * @return /url/1
     */
    public static String getRestURL(HttpServletRequest request)
    {
        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    }

    /**
     * 获取请求路径表达式
     * 
     * @param request
     * @return /url/{id}
     */
    public static String getRestPatternURL(HttpServletRequest request)
    {
        return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    }

    /**
     * Convenience method to set a cookie
     * 
     * @param response the current response
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path to set it on
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int expiry)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        cookie.setMaxAge(expiry);
        // cookie.setMaxAge(AppConstants.HOUR * 24 * 30); // 30 days

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     * 
     * @param request the current request
     * @param name the name of the cookie to find
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name)
    {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null)
        {
            return returnCookie;
        }

        for (final Cookie thisCookie : cookies)
        {
            if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue()))
            {
                returnCookie = thisCookie;
                break;
            }
        }

        return returnCookie;
    }

    /**
     * Convenience method for deleting a cookie by name
     * 
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set (i.e. /appfuse)
     */
    public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path)
    {
        if (cookie != null)
        {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

    private static String getFirstIP(String ip)
    {
        if (StringUtils.isEmpty(ip))
        {
            return null;
        }
        String iparr[] = ip.split("\\,");
        for (int i = 0; i < iparr.length; i++)
        {
            String iptemp = iparr[i];
            if (checkIP(iptemp))
            {
                return iptemp;
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        String ip = "180.96.19.199,180.96.19.191";
        String ip2 = "180.96.19.192";
        String ip3 = "180.96.19.199.180,96.19.191.09";
        System.out.println(getFirstIP(ip));
        System.out.println(getFirstIP(ip2));
        System.out.println(getFirstIP(ip3));
        System.out.println(checkIP(ip));
        if (null != ip && !"".equals(ip.trim()))
        {
            StringTokenizer st = new StringTokenizer(ip, ",");
            if (st.countTokens() > 1)
            {
                System.out.println(st.nextToken());
            }
        }
    }
}