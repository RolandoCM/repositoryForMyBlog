/**
 * Every request will be intercepted at this class. preHandle() intercepts all requests before landing to the endpoint
 * And postHandle() handles POST Requests
 */
package com.org.coop.authentication;

import java.util.Base64;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coop.org.exception.HttpUnauthorizedException;
import com.org.coop.admin.service.CustomerAdminLoginService;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.constants.WebConstants;
import com.org.coop.society.data.customer.transaction.config.CustomerContextHolder;

@Component
@PropertySource("classpath:CustomerApplicationWS.properties")
public class RestEndpointCustomerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger.getLogger(RestEndpointCustomerInterceptor.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CustomerAdminLoginService adminLoginService;
	
	public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        if(request.getRequestURL().toString().contains("/rest/")) {

        		String isHttpBasicAuthenticationEnabled = env.getProperty("enable.http.basic.authentication");
        		if("true".equalsIgnoreCase(isHttpBasicAuthenticationEnabled)) {
	        		String authCredentials = request.getHeader(WebConstants.AUTHENTICATION_HEADER);
	        		try {
		        		final String encodedUserPassword = authCredentials.replaceFirst("Basic ", "");
		        		String usernameAndPassword = null;
	        		
	        			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
	        			usernameAndPassword = new String(decodedBytes, "UTF-8");
	        		
		        		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		        		final String username = tokenizer.nextToken();
		        		final String password = tokenizer.nextToken();
		        		if(!adminLoginService.isUserAuthenticated(username, password)) {
		        			log.error("User is not authenticated: " + username);
		        			throw new HttpUnauthorizedException("User is not authenticated:" + username);
		        		}
		        		UIModel uiModel = adminLoginService.getBranchConfig(username);
		        		if(uiModel.getErrorMsg() == null) {
		        			CustomerContextHolder.setCustomerType(uiModel.getBranchBean().getDbName());
		        		}
		        		
	        		} catch (Exception e) {
	        			log.error("Exception occured while authenticating user: ", e);
	        			throw new HttpUnauthorizedException("Exception occured while authenticating user");
	        		}
        		}
        } 
        //if returned false, we need to make sure 'response' is sent
        return true;
    }
	
}
