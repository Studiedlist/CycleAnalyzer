package org.einnfeigr.iot.config;

import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
			.csrf().disable()
            .authorizeRequests()
	            .antMatchers("/api/upload").permitAll()
	            .antMatchers("/api/token/check").hasRole("user")
	            .antMatchers("/api/**").hasRole("admin")
	            .anyRequest().hasAnyRole("user", "admin")
            .and()
	            .exceptionHandling()
	            .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            .and()
            .formLogin()
	            .loginPage("/login")
				.loginProcessingUrl("/login")
	            .defaultSuccessUrl("/", true)
	            .permitAll()
            .and()
	            .logout()
	            .logoutUrl("/logout")
	            .permitAll();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	String password = System.getenv("adminPassword");
    	if(password == null) {
    		password = ""+new Random().nextLong();
    	}
    	auth.inMemoryAuthentication()
    		.withUser("admin")
    		.password(password)
    		.roles("USER", "ADMIN");
    }

}
