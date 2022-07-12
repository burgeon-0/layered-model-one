package org.bg181.sbd.adapter.security;

import org.bg181.sbd.adapter.security.admin.AdminAuthenticationProvider;
import org.bg181.sbd.adapter.security.user.UserAuthenticationProvider;
import org.bg181.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Sam Lu
 * @date 2021/6/4
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(1)
    class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AdminAuthenticationProvider adminAuthenticationProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(Constants.API_ADMIN + "/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(preAuthenticatedFilter())
                    .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(adminAuthenticationProvider);
        }

        @Bean("adminPreAuthenticatedFilter")
        public PreAuthenticatedFilter preAuthenticatedFilter() throws Exception {
            return new PreAuthenticatedFilter(authenticationManager());
        }

    }

    @Configuration
    @Order(2)
    class UserSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserAuthenticationProvider userAuthenticationProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(Constants.API_USER + "/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(preAuthenticatedFilter())
                    .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(userAuthenticationProvider);
        }

        @Bean("userPreAuthenticatedFilter")
        public PreAuthenticatedFilter preAuthenticatedFilter() throws Exception {
            return new PreAuthenticatedFilter(authenticationManager());
        }

    }

    @Configuration
    @Order(3)
    class PubSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/h2_console/**", Constants.API + "/**")
                    .permitAll();
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

    }

}
