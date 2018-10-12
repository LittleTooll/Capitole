package com.capitole.challenge.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	   
	@Autowired  
	private BasicAuthenticationPoint basicAuthenticationPoint;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
        
		http
            .authorizeRequests()
            	.antMatchers("/**").permitAll()
                .anyRequest().authenticated();
        
        http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);  
    }

    
}