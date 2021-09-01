package com.milewczyk.productcatalogservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguation extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("actuator/**").hasRole(USER_ROLES.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,  "/admin/products**").hasRole(USER_ROLES.ADMIN.name())
                .antMatchers("/admin/products**").hasAnyRole(USER_ROLES.ADMIN.name(), USER_ROLES.MODERATOR.name())
                .antMatchers(HttpMethod.GET, "/web/products**").hasAnyRole(USER_ROLES.USER.name(), USER_ROLES.ADMIN.name(), USER_ROLES.MODERATOR.name())
                .anyRequest().authenticated();
    }

    private enum USER_ROLES {
        USER, ADMIN, MODERATOR
    }
}
