package com.milewczyk.orderservice.configuration;

import com.milewczyk.orderservice.model.models_from_other_services.user_service.USER_ROLE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/orders/**").authorizeRequests()
                .mvcMatchers("/management/orders/**").hasAnyRole(
                        USER_ROLE.ROLE_ADMIN.name(), USER_ROLE.ROLE_MODERATOR.name())
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}
