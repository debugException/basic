package com.xh.basic.filter;

import com.xh.basic.shiro.JWTToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author szq
 * @Package com.xh.basic.filter
 * @Description: to do ...
 * @date 2018/5/39:24
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthenticatedException {
        //判断请求的请求头上是否带有“token”
        if(isLoginAttempt(request, response)){
            //如果存在，则进入executeLogin方法执行登入，检查token是否正确
            try{
                executeLogin(request, response);
                return true;
            }catch (Exception e){
                //token错误
                responseError(response, e.getMessage());
            }
        }
        //如果请求头不存在token，则可能是执行登陆操作或者是游客状态访问，无需检查token，直接返回true
        return true;
    }

    /**
     * 判断用户是否想登陆
     * 检查header里面是否包含Token字段
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        return token != null;
    }

    /**
     * 执行登陆操作
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        JWTToken jwtToken = new JWTToken(token);
        //提交给realm进行登入，如果错误他会抛出异常并捕获
        getSubject(request, response).login(jwtToken);
        //如果没有抛出异常则代表登陆成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Request-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request,response);
    }

    /**
     * 将非法请求跳转到/unauthorized/**
     */
    private void responseError(ServletResponse response, String message){
        try{
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }
}
