package org.vbazurtob.HRRecruitApp.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

@EnableWebSecurity
public class AppSecurityConfig  {

	@Autowired
	protected DataSource datasource;
		
	public AppSecurityConfig() {		
	}
	
	@Configuration
	public static class PublicSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http
			.authorizeRequests()
			.antMatchers(ROOT_PAGE).  permitAll()
			.antMatchers(INDEX_PAGE, PUBLIC_CNTROLLER  +  "/home", PUBLIC_CNTROLLER  +  "/**").  permitAll()
			.antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**").permitAll()
			.anyRequest().authenticated()
			
			.and().csrf().disable()
			;
						
		}		
	}
	
	
	
	@Configuration
	@Order(2)
	public static class ApplicantSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http			
			.requestMatchers()
				.antMatchers( APPLICANT_CV_CNTROLLER +  "/**", JOBS_CNTROLLER + "/**") // <- automatic redirection not working.. maybe implement another class???
			.and()
				.authorizeRequests().anyRequest().authenticated()
			.and()
				.authorizeRequests().anyRequest()
					.hasAuthority("APPLICANT")
								
			.and()
			.formLogin()
				.loginPage(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE)
				.loginProcessingUrl( APPLICANT_CV_CNTROLLER + APPLICANT_LOGIN_PAGE ) // This POST URL has to match with root AntMatcher
				
				.failureUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?error=Failure when trying to log in")
				.defaultSuccessUrl( APPLICANT_CV_CNTROLLER + APPLICANT_SUMMARY_PAGE )
				.permitAll()
				
			.and()
			.logout()
				.logoutUrl(APPLICANT_CV_CNTROLLER + APPLICANT_LOGOUT) // <-- logout
				.logoutSuccessUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?logout")
				.deleteCookies("JSESSIONID")
				.permitAll()
			
			.and()
		          .exceptionHandling()
		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
				
		    .and().csrf().disable()
			;
			
			System.out.println("app login " + PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE);;
		}

		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder,  DataSource datasource) throws Exception {
			
			auth.jdbcAuthentication().dataSource(datasource)
			.passwordEncoder(bcryptEncoder)
			//True means the user is enabled. Usually this would be implement as a field in the DB.
			.usersByUsernameQuery("SELECT username,password,true FROM applicant WHERE username=?")
			//Since we don't have an authorities(roles) table we assume every user is a regular one: an applicant
			.authoritiesByUsernameQuery("SELECT username, 'APPLICANT' FROM applicant WHERE username=?");

		}


		public ApplicantSecurityConfig() {
			super();
		}

		
		
	}
	
	@Configuration
	@Order(1)
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
		
		
		
		public AdminSecurityConfig() {
			super();
		}



		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http

			.antMatcher( JOBS_ADS_MANAGEMENT_CNTROLLER + "/**") // <-- Root ant matcher
			.authorizeRequests()
			.anyRequest()
			.hasAuthority(  "HR_ADMIN" )
			
			
			.and()
			.formLogin()
				.loginPage(PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE)
				.loginProcessingUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGIN_PAGE ) // <-- post login url must match root ant patter
				.failureUrl( PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE + "?error")
				.defaultSuccessUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE )
				.permitAll()
			.and()
			.logout()
				.logoutUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGOUT)
				.logoutSuccessUrl(PUBLIC_CNTROLLER +"/login-hr?logout") // <-- post login url must match root ant patter
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
		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder  , DataSource datasource ) throws Exception {
						
			auth.jdbcAuthentication().dataSource(datasource)
			.passwordEncoder(bcryptEncoder)
			
			//True means the user is enabled. Usually this would be implement as a field in the DB.
			.usersByUsernameQuery("SELECT username, password, true FROM hr_user WHERE username = ?")
			//Since we don't have an authorities(roles) table we assume every user is admin
			.authoritiesByUsernameQuery("SELECT username, role FROM hr_user WHERE username = ?");

		}
		
		
	}
	
	
	
	

}
