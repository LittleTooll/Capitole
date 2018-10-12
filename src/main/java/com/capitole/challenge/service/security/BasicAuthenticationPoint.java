package com.capitole.challenge.service.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;  

@Component  
public class BasicAuthenticationPoint extends BasicAuthenticationEntryPoint {  

	private static final String BASIC_REALM = "Basic realm=";
	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	private static final String HTTP_STATUS_401 = "HTTP Status 401 - ";
	private static final String REALM_NAME = "Challenge";

	@Override  
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)  
			throws IOException, ServletException {  

		response.addHeader(WWW_AUTHENTICATE, BASIC_REALM + getRealmName());  
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
		PrintWriter writer = response.getWriter();  
		writer.println(HTTP_STATUS_401 + authEx.getMessage());  
	}  
	
	@Override  
	public void afterPropertiesSet() throws Exception {  
		setRealmName(REALM_NAME);  
		super.afterPropertiesSet();  
	}	
}  