package tech.ouyu.quickResponder.back.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.ouyu.quickResponder.back.util.JwtUtil;
import tech.ouyu.quickResponder.back.util.UserIdThreadLocal;
import tech.ouyu.quickResponder.back.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-09 11:10
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Slf4j
@Component
public class FontRequestInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String host = getIpAddr(request);
        String method = request.getMethod();
        String token = request.getHeader("X-CURRENT-TOKEN");
        try {
            //验证token
            if (jwtUtil.validExpiration(token)) {
                Claims claims = jwtUtil.getClaims(token);
                String issuer = claims.getIssuer();
                String userId = claims.getId();
                request.setAttribute("userId",userId);
                UserIdThreadLocal.serUserId(Long.valueOf(userId));
                log.info("{}请求URL:{},方法类型:{},来源ip:{}，进入前置拦截器", issuer, uri, method, host);
            }
        } catch (IllegalArgumentException e) {
            log.error("陌生人请求URL:{},方法类型:{},来源ip:{}，已被拦截token失效", uri, method, host);
//            response.sendError(401, "请求已失效请重新登录");
            returnError(response, 401, "请求已失效请重新登录");
            return false;
        }

        return true;
    }

    private void returnError(HttpServletResponse response, int code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String jsonString = JSONObject.toJSONString(new Result<>().error(code, message));
        writer.print(JSONObject.parseObject(jsonString));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri = request.getRequestURI();
        String host = getIpAddr(request);
        String method = request.getMethod();
        String token = request.getHeader("X-CURRENT-TOKEN");
        Claims claims = jwtUtil.getClaims(token);
        String issuer = claims.getIssuer();
        log.info("{}请求URL:{},方法类型:{},来源ip:{}，退出前置拦截器",issuer, uri, method, host);
        //清除线程数据
        UserIdThreadLocal.removeUserId();
    }

    /**
     * 获取访问者ip地址
     *
     * @author ouyu
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
