package com.truemeds.truemeds.security.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.truemeds.truemeds.security.jwt.AuthEntryPointJwt;
import com.truemeds.truemeds.security.jwt.AuthTokenFilter;
import com.truemeds.truemeds.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl 	userDetailsService;
	
	
	@Autowired
	AuthEntryPointJwt unauthorizedHandler;
	
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	 private static final Logger log= LogManager.getLogger(SecurityConfig.class);
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
		/*
		 * PasswordEncoder encoder =
		 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 * 
		 * log.info("inside securityConfig configure"); auth.inMemoryAuthentication()
		 * .withUser("user").password(encoder.encode("1234")).roles("USER") .and()
		 * .withUser("admin").password(encoder.encode("1234")).roles("USER", "ADMIN");
		 */
		
	}
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http
	                //HTTP cors authentication
	                .cors().and().csrf().disable()
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	    			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                .authorizeRequests()
	                .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
	                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
	                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
	                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
	                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
	                .antMatchers("/api/auth/**").permitAll()
	    			.antMatchers("/api/test/**").permitAll().antMatchers("api/getusercontent").
	    			permitAll().antMatchers("getbookRequestparam").permitAll()
	                .and().formLogin().disable();
	        
	        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	

}
