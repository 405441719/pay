package com.payinxl.authentication;

import com.payinxl.common.util.RequestUtils;
import com.payinxl.model.MemUser;
import com.payinxl.service.MemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by barry
 * Date:2017/2/16
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private MemUserService memUserService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username=userDetails.getUsername();
        httpServletRequest.getSession().removeAttribute("validateCode");
        httpServletResponse.getWriter().print("{\"success\": true}");
        httpServletResponse.getWriter().flush();
    }
}
