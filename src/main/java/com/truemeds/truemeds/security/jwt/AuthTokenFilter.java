package com.truemeds.truemeds.security.jwt;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.truemeds.truemeds.security.services.UserDetailsServiceImpl;



public class AuthTokenFilter extends OncePerRequestFilter {
	
	  @Autowired
	  private JwtUtils jwtUtils;

	  @Autowired
	  private UserDetailsServiceImpl userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		   logger.info("---------------------------inside doFilterInternal----------------- ");
		 try {
		      String jwt = parseJwt(request);
		      logger.info("validate token output"+jwtUtils.validateJwtToken(jwt));
		      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
		    	 
		        String username = jwtUtils.getUserNameFromJwtToken(jwt);
		        logger.info("validate token output"+username);  
		        
		        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		        
		        logger.info("UserDetail"+userDetails.getUsername()+"password"+userDetails.getPassword());
				
				  UsernamePasswordAuthenticationToken authentication = new
				  UsernamePasswordAuthenticationToken(userDetails, null,
				  userDetails.getAuthorities());
				 
		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		        SecurityContextHolder.getContext().setAuthentication(authentication);
		      }
		    } catch (Exception e) {
		      logger.error("Cannot set user authentication: {}", e.getMessage());
		     e.printStackTrace();
		    }

		    filterChain.doFilter(request, response);
		  }

		  private String parseJwt(HttpServletRequest request) throws IOException {
		    String headerAuth = request.getHeader("Authorization");
		    System.out.println("---------------inside parseJwt--------------------");
		    System.out.println(((String)request.getReader().lines().collect(Collectors.joining())));

		    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
		      return headerAuth.substring(7, headerAuth.length());
		    }

		    return null;
		
		
	}

}
