package org.pzz.config.jwt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  PZJ
 * @create  2021/4/29 17:08
 * @email   wuchzh0@gmail.com
 * @desc    自定义的jwt认证过滤器，用来拦截Header中携带 JWT token的请求
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 过滤器拦截请求的入口方法
     * 返回 true 则允许访问
     * 返回false 则禁止访问，会进入 onAccessDenied()
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try{
            System.out.println("进去jwt过滤器");
            //检测header里面的token内容是否正确
            executeLogin(request,response);
            return true;
        }catch (Exception e){
            throw new AuthenticationException("Token失效，请重新登录!",e);
        }
    }

    /**
     * 身份验证,检查 JWT token 是否合法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        //从请求头拿到token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        JwtToken jwtToken = new JwtToken(token);
        //将jwtToken提交给realm登录，如果报错会抛出异常并被捕获
        Subject subject = getSubject(request,response);
        //IncorrectCredentialsException 密码错误
        subject.login(jwtToken);
        //如果没有抛出异常则代表登录成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持 前置处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
