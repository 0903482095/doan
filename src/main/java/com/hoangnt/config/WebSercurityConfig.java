package com.hoangnt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSercurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JWTAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	JWTAuthenticationFilter authenticationFilter;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users/**").permitAll()
				.antMatchers(HttpMethod.POST,"/social/login").permitAll()
//				.antMatchers(HttpMethod.POST,"/address/**").permitAll()
				.antMatchers(HttpMethod.GET,"/location/**").permitAll()
				.antMatchers("/resources/**", "/socket/**").permitAll()

				// .and()
				// .authorizeRequests().antMatchers(HttpMethod.GET,"/users/hoang2").access("hasRole('ADMIN')")
				// .antMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("ADMIN", "EMPLOYEE")
				// .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN", "EMPLOYEE").
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/resources/upload-dir/**")
				.addResourceLocations("file:"+Paths.get("").toAbsolutePath()+"/src/main/resources/upload-dir/" );
	}

}
