package com.payinxl.config;


import com.payinxl.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by barry
 * Date:2017/1/10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider provider;//自定义验证
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private SimpleLogoutSuccessHandler simpleLogoutSuccessHandler;
    @Autowired
    private CustomAuthenticationDetailsSource customAuthenticationDetailsSource;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/*","/druid/**","/font/**","/js/**","/images/**","/css/**","/notify/**","/**/news_detail.html").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/login").authenticationDetailsSource(customAuthenticationDetailsSource).successHandler(loginSuccessHandler).failureHandler(customAuthenticationFailureHandler).permitAll()
                .and().logout().deleteCookies("JSESSIONID").logoutSuccessHandler(simpleLogoutSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedPage("/error")
                .and().csrf().ignoringAntMatchers("/validPhone","/druid/**","/notify/**")
                .and().headers().frameOptions().sameOrigin()
                .and().sessionManagement().invalidSessionUrl("/").maximumSessions(1).expiredUrl("/");
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}
