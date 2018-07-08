package org.isegodin.expenses.adviser.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author isegodin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AccessTokenHandleInterceptor accessTokenHandleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessTokenHandleInterceptor)
                .addPathPatterns("/**");
    }
}
