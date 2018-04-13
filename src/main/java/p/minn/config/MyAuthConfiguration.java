package p.minn.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import p.minn.oauth.filter.MyAuthFilter;
/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
@Configuration
public class MyAuthConfiguration {

	@Bean
    public FilterRegistrationBean myFilterRegistration () {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new MyAuthFilter());
        frb.setUrlPatterns(Arrays.asList("/*"));
        return frb;
    }

}
