package tech.ouyu.quickResponder.back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ouyu.quickResponder.back.filter.UrlAccessFilter;

@Configuration
public class FilterConfig {
    @Autowired
    UrlAccessFilter urlAccessFilter;
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(urlAccessFilter);
        registration.addUrlPatterns("/*");
        registration.setName("UrlAccessFilter");
        registration.setOrder(1);
        return registration;
    }
 
}