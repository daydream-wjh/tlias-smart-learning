package com.demo.tlias.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * @author wjh
 * @date 2024/3/3 19:46
 */
//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化了");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器拦截了请求,放行前");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("过滤器拦截了请求,放行后");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁了");

        Filter.super.destroy();
    }
}
