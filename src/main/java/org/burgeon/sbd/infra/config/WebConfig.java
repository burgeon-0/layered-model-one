package org.burgeon.sbd.infra.config;

import org.burgeon.sbd.adapter.common.resolver.CamelCaseToSnakeCaseResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/6/2
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CamelCaseToSnakeCaseResolver());
    }

}
