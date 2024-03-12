package com.demo.tlias.filter;

import com.alibaba.fastjson.JSONObject;
import com.demo.tlias.pojo.Result;
import com.demo.tlias.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author wjh
 * @date 2024/3/3 20:21
 */
//@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI().toString();
        log.info("url:{}",url);
        if (url.contains("login")) {
            log.info("检测到登录请求，进行放行");
            filterChain.doFilter(request,response);
            return;
        }
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            Result error = Result.error("NOT_LOGIN");
            String string = JSONObject.toJSONString(error);
            response.getWriter().write(string);
            return;
        }
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("jwt令牌解析失败");
            Result error = Result.error("NOT_LOGIN");
            String string = JSONObject.toJSONString(error);
            response.getWriter().write(string);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
