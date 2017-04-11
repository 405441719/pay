package com.payinxl.interceptor;

import com.payinxl.authentication.MyAccessDecisionManager;
import com.payinxl.common.exception.BusinessException;
import com.payinxl.common.exception.ErrorCodePropertyHandler;
import com.payinxl.service.MemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(MyFilterSecurityInterceptor.class);
    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        try {
            invoke(fi);
        }catch(Exception e){
//            request.getRequestDispatcher("/login") .forward(request, response);
            logger.error(ErrorCodePropertyHandler.errorException(e));
            ((HttpServletResponse)response).sendRedirect("/login");
        }
    }
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
    //fi里面有一个被拦截的url
    //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
    //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
    Authentication ath = SecurityContextHolder.getContext().getAuthentication();
       this.getAccessDecisionManager().decide(ath,null,securityMetadataSource.getAttributes(fi));
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}