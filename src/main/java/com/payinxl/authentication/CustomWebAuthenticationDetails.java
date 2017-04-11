package com.payinxl.authentication;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by barry
 * Date:2017/2/16
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private final String captchasession;
    private final String captchaparameter;
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.captchaparameter = request.getParameter("vcode");
        this.captchasession =(String) request.getSession().getAttribute("validateCode");
    }

    public String getCaptchasession() {
        return captchasession;
    }

    public String getCaptchaparameter() {
        return captchaparameter;
    }
}
