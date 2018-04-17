package effect.effect.config;

import effect.effect.common.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author feilongchen
 * @create 2018-01-28 11:19 PM
 */
@Configuration
public class FilterAutoConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(tokenFilter());
//        filterRegistrationBean.addUrlPatterns("/v1/*");
//        filterRegistrationBean.addUrlPatterns("/member/*");
        filterRegistrationBean.addUrlPatterns("/v1/member/*");
        filterRegistrationBean.setAsyncSupported(true);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return filterRegistrationBean;
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }
}
