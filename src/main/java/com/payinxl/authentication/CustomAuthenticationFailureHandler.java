package com.payinxl.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by barry
 * Date:2017/2/16
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String errmsg=e.getMessage();
        Integer errornum= (Integer) httpServletRequest.getSession().getAttribute("errornum");
        if(null!=errornum){
            errornum++;
            httpServletRequest.getSession().setAttribute("errornum",errornum);
        }else{
            httpServletRequest.getSession().setAttribute("errornum",1);
        }
        httpServletResponse.getWriter().print("{\"success\": false,\"errornum\": "+errornum+",\"error\":\""+errmsg+"\"}");
        httpServletResponse.getWriter().flush();
    }
}
