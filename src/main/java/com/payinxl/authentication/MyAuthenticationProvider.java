package com.payinxl.authentication;

import com.payinxl.common.security.MD5;
import com.payinxl.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserLoginService userService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String captchaparameter=details.getCaptchaparameter();
        String captchasession=details.getCaptchasession();

        if(captchaparameter!=null&&captchasession!=null&&!captchaparameter.equals(captchasession)){
            throw new BadCredentialsException("error1");
        }
        MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("error2");
        }

        //加密过程在这里体现
        password= MD5.digest(password);
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("error3");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}