package tech.ouyu.quickResponder.back.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-11 13:40
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Component
@Slf4j
public class UrlAccessFilter extends CrossFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       //跨域验证
        onCrossDomain((HttpServletRequest) request, (HttpServletResponse) response);
        log.info("进入过滤器，验证是否跨域");

       /* String method = request.getMethod();
        if (method.equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.OK.value());
            return false;
        }*/
        // 检查是否是OPTIONS请求 如果是直接跳过
        String method = ((HttpServletRequest) request).getMethod();
        if (method.equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
            return;
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
