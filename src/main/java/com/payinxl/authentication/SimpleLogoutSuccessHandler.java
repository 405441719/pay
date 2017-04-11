package com.payinxl.authentication;

import com.payinxl.model.MemUser;
import com.payinxl.service.MemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
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
public class SimpleLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private MemUserService memUserService;
    @Autowired
    public SimpleLogoutSuccessHandler() {
        super();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username=userDetails.getUsername();
        super.onLogoutSuccess(httpServletRequest,httpServletResponse,authentication);
    }
}
