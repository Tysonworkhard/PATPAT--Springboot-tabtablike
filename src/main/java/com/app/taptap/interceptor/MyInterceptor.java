package com.app.taptap.interceptor;

import com.app.taptap.mapper.LoginMapper;
import com.app.taptap.mapper.RegisterMapper;
import com.app.taptap.pojo.User;
import com.app.taptap.utils.TokenCheck;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

public class MyInterceptor implements HandlerInterceptor {
    @Resource
    private RegisterMapper registerMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前执行的逻辑
        // 获取请求头中的 token
        String token = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取令牌部分
            token = authorizationHeader.substring("Bearer ".length());
        }else{
            token = authorizationHeader;
        }
        // 进行 token 的验证
        TokenCheck tokenCheck = new TokenCheck();
        // 判断用户是否登录，根据实际需求进行验证
        Claims claims = tokenCheck.analyseToken(token);
        if (isValidToken(claims)) {
            // 用户已登录，允许继续处理请求
            return true;
        } else {
            // 用户未登录，返回错误信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("请先登录");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后、视图渲染之前执行的逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求处理完成后执行的逻辑，包括视图渲染完成后
    }

    //判断token真实性
    public boolean isValidToken(Claims claims){
        //时间
        Date expirationDate = claims.getExpiration();
        Date now = new Date();
        if (expirationDate != null && expirationDate.before(now)) {
            return false; // token 已过期
        }

        //UID
        String UID = claims.getSubject();
        User user = registerMapper.getNewUser(UID);
        return user != null;

    }
}
