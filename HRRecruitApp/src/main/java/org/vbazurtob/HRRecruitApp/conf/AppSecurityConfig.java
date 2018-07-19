package org.vbazurtob.HRRecruitApp.conf;

import javax.sql.DataSource;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints.*;

/**
 * @author voltaire
 *
 */
@Configuration
@EnableWebSecurity


public class AppSecurityConfig  {

	@Autowired
	protected DataSource datasource;
	
	@Autowired
	protected BCryptPasswordEncoder bcryptEncoder;
	
	public AppSecurityConfig() {		
	}
	
	@Configuration
	@Order(1)
	public static class ApplicantSecurityConfig extends WebSecurityConfigurerAdapter {

		
		
//		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http
			.authorizeRequests()
			.antMatchers( ROOT_PAGE, INDEX_PAGE, PUBLIC_CNTROLLER + "/**", "/css/**", "/js/**", "/img/**" )
			.permitAll()
//			.antMatchers( ROOT_PAGE )
//			.denyAll()
			
			// Authentication needed
//			.authorizeRequests()
			.antMatchers( APPLICANT_CV_CNTROLLER +  "/**" , JOBS_CNTROLLER + "/**")
		
			.authenticated().anyRequest()
			//.hasRole("APPLICANT")
			.denyAll()
			
//			.anyRequest()
//			.hasRole("APPLICANT")
			
//			.antMatcher( APPLICANT_CV_CNTROLLER +  "/**")
//			.authorizeRequests().anyRequest().hasRole("APPLICANT")
//			.antMatcher(JOBS_CNTROLLER + "/**").
//			.authorizeRequests().anyRequest().hasRole("APPLICANT")
			
			// Authorization
//			.and()
//			.authorizeRequests()
//			.anyRequest()PUBLIC_CNTROLLER
////			.( APPLICANT_CV_CNTROLLER +  "/**" , JOBS_CNTROLLER + "/**")
//			.hasRole("APPLICANT")
			
			
			.and()
			.formLogin()
				.loginPage(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE)
				
				.loginProcessingUrl(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE )
				.failureUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?error")
				.defaultSuccessUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + APPLICANT_SUMMARY_PAGE )
				.permitAll()
				
			.and()
			.logout()
				.logoutUrl( "/logout")
				.logoutSuccessUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?logout")
				.deleteCookies("JSESSIONID")
				.permitAll()
			
			.and()
		          .exceptionHandling()
		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
				
			.and()
				.csrf()
				.disable()

			;
			
			System.out.println("app login " + PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE);;
		}

		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder, DataSource datasource) throws Exception {
			
			
			
			
			auth.jdbcAuthentication().dataSource(datasource)
			.passwordEncoder(bcryptEncoder)
			//True means the user is enabled. Usually this would be implement as a field in the DB.
			.usersByUsernameQuery("SELECT username,password,true FROM applicant WHERE username=?")
			//Since we don't have an authorities(roles) table we assume every user is a regular one: an applicant
			.authoritiesByUsernameQuery("SELECT username,'APPLICANT' FROM applicant WHERE username=?");

		}


		public ApplicantSecurityConfig() {
			super();
		}

		
		
	}
	
	@Configuration
	@Order(2)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter /*implements ControllerEndpoints*/{
		
		
		
		public AdminSecurityConfig() {
			super();
		}



//		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http
			.authorizeRequests()
			.antMatchers( ROOT_PAGE, INDEX_PAGE, PUBLIC_CNTROLLER + "/**", "/css/**", "/js/**", "/img/**" )
			.permitAll()
			
			
			
			.antMatchers( JOBS_ADS_MANAGEMENT_CNTROLLER + "/**")
			.authenticated()
			.anyRequest()
			.hasRole("HR_ADMIN")
			
			.and()
			.formLogin()
				.loginPage(PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE).permitAll()
				.loginProcessingUrl( PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE ).permitAll()
				.failureUrl( PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE + "?error")
				.defaultSuccessUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE )
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout-hr")
				.logoutSuccessUrl(PUBLIC_CNTROLLER +"/login-hr?logout")
				.deleteCookies("JSESSIONID")
				.permitAll()
			
			.and()
		          .exceptionHandling()
		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
			.and()
				.csrf().disable()
				;
			
			System.out.println("hr login " + PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE);;
			
		}

		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder, DataSource datasource ) throws Exception {
			
//			System.out.println("=============  admin " +  bcryptEncoder.encode("admin") );
			
			auth.jdbcAuthentication().dataSource(datasource)
			.passwordEncoder(bcryptEncoder)
			
			//True means the user is enabled. Usually this would be implement as a field in the DB.
			.usersByUsernameQuery("SELECT username, password, true FROM hr_user WHERE username = ?")
			//Since we don't have an authorities(roles) table we assume every user is admin
			.authoritiesByUsernameQuery("SELECT username, role FROM hr_user WHERE username = ?");

		}
		
		
	}
	

}
