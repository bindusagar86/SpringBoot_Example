package com.bbtech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("bindu").password("{noop}bindu").roles("ADMIN")
			.and()
				.withUser("john").password("{noop}john").roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
//		http.httpBasic()
//			.and()
//				.authorizeRequests()
//					.antMatchers("/bbtech.com/hello/").hasRole("USER")
//					.antMatchers("**/admin/**").hasRole("ADMIN");
		http.authorizeRequests()
				.antMatchers("/bbtech.com/admin/**").hasRole("ADMIN")
				.antMatchers("/bbtech.com/hello").hasRole("USER")
			.and()
				.formLogin()
 				.defaultSuccessUrl("/bbtech.com");
	}

}
