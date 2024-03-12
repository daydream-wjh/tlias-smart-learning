package com.demo.tlias.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.demo.tlias.pojo.Result;
import com.demo.tlias.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wjh
 * @date 2024/3/4 16:32
 */
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        log.info("url:{}", url);
        if (url.contains("login")) {
            log.info("检测到登录请求，进行放行");
            return true;
        }
        String jwt = request.getHeader("token");
        if (!StringUtils.hasLength(jwt)) {
            Result error = Result.error("NOT_LOGIN");
            String string = JSONObject.toJSONString(error);
            response.getWriter().write(string);
            return false;
        }
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("jwt令牌解析失败");
            Result error = Result.error("NOT_LOGIN");
            String string = JSONObject.toJSONString(error);
            response.getWriter().write(string);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after");

    }
}
