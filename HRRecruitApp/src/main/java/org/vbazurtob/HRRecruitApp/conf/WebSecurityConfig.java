package org.vbazurtob.HRRecruitApp.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	protected DataSource datasource;
	
	@Autowired
	protected BCryptPasswordEncoder bcryptEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http
		.authorizeRequests()
		/*.antMatchers("/", "/home", "/login","/css/**", "/js/**").permitAll()
		.and()
		.authorizeRequests().antMatchers("/**").authenticated()
		.and().formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/summary")
		.permitAll()
		.and()
		.logout()
		
		
		.permitAll()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")*/
		
		.antMatchers("/**") //remove just for test
		.permitAll()
		;
		
		http.csrf().disable();
	}

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		//System.out.println("=============   " +  bcryptEncoder.encode("123") );
		
		
		auth.jdbcAuthentication().dataSource(datasource)
		.passwordEncoder(bcryptEncoder)
		//True means the user is enabled. Usually this would be implement as a field in the DB.
		.usersByUsernameQuery("SELECT username,password,true FROM applicant WHERE username=?")
		//Since we don't have an authorities(roles) table we assume every user is a regular one: an applicant
		.authoritiesByUsernameQuery("SELECT username,'APPLICANT' FROM applicant WHERE username=?");

	}
	
	
}
