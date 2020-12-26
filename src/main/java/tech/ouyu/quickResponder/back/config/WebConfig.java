package tech.ouyu.quickResponder.back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.ouyu.quickResponder.back.interceptor.FontRequestInterceptor;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-09 11:13
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    FontRequestInterceptor fontRequestInterceptor;

    private String[] swaggerPath = {"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/csrf"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fontRequestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerPath)
                //登录、注册、验证码、微信小程序注册、获取角色
                .excludePathPatterns("/","/error","/user/doLogin",
                        "/user/doLoginUserId",
                        "/user/doRegister","/everyday/oneword",
                        "/document/captcha","/user/UserInfoByOpenIdOrRegister",
                        "/role/roles");
    }

}
