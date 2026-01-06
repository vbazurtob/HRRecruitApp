/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

package org.vbazurtob.HRRecruitApp.conf;

import static org.vbazurtob.HRRecruitApp.conf.ControllerEndpoints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * @author voltaire
 *
 */

//@EnableWebSecurity
//public class AppSecurityConfig  {
//
//	@Autowired
//	protected DataSource datasource;
//
//	public AppSecurityConfig() {}
//
//	@Configuration
//	public static class PublicSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception{
//
//			http
//			.authorizeRequests()
//			.antMatchers(ROOT_PAGE).permitAll()
//			.antMatchers(INDEX_PAGE, PUBLIC_CNTROLLER  +  "/home", PUBLIC_CNTROLLER  +  "/**").permitAll()
//			.anyRequest().authenticated()
//			.and().csrf().disable();
//		}
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            // Completely bypass security filter chain for static assets so they always load
//            web.ignoring().antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**");
//        }
//	}
//
//	@Configuration
//	@Order(3)
//	public static class ApplicantSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		public ApplicantSecurityConfig() {
//			super();
//		}
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception{
//			http
////					.authorizeRequests()
//                    .authorizeHttpRequests()
//				.antMatchers( APPLICANT_CV_CNTROLLER +  "/**")
//					.hasAuthority("APPLICANT")
//                    .antMatchers( JOBS_CNTROLLER + "/**").hasAuthority("APPLICANT")// <- automatic redirection not working.. maybe implement another class???
//			.anyRequest().authenticated()
//
//			.and()
//			.formLogin()
//				.loginPage(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE)
//				.loginProcessingUrl( APPLICANT_CV_CNTROLLER + APPLICANT_LOGIN_PAGE ) // This POST URL has to match with root AntMatcher
//
//				.failureUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?error=Failure when trying to log in")
//				.defaultSuccessUrl( APPLICANT_CV_CNTROLLER + APPLICANT_SUMMARY_PAGE )
//				.permitAll()
//
//			.and()
//			.logout()
//				.logoutUrl(APPLICANT_CV_CNTROLLER + APPLICANT_LOGOUT) // <-- logout
//				.logoutSuccessUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?logout")
//				.deleteCookies("JSESSIONID")
//				.permitAll()
//
//			.and()
//		          .exceptionHandling()
//		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
//
//		    .and().csrf().disable()
//			;
//
//			System.out.println("app login " + PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE);
//        }
//
//		@Autowired
//		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder,  DataSource datasource) throws Exception {
//
//			auth.jdbcAuthentication().dataSource(datasource)
//			.passwordEncoder(bcryptEncoder)
//			//True means the user is enabled. Usually this would be implement as a field in the DB.
//			.usersByUsernameQuery("SELECT username,password,true FROM applicant WHERE username=?")
//			//Since we don't have an authorities(roles) table we assume every user is a regular one: an applicant
//			.authoritiesByUsernameQuery("SELECT username, 'APPLICANT' FROM applicant WHERE username=?");
//		}
//	}
//
//	@Configuration
//	@Order(2)
//	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
//		public AdminSecurityConfig() {
//			super();
//		}
//
//
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception{
//
//			http
//			.antMatcher( JOBS_ADS_MANAGEMENT_CNTROLLER + "/**") // <-- Root ant matcher
//			.authorizeRequests()
//			.anyRequest()
//			.hasAuthority(  "HR_ADMIN" )
//
//
//			.and()
//			.formLogin()
//				.loginPage(PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE)
//				.loginProcessingUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGIN_PAGE ) // <-- post login url must match root ant patter
//				.failureUrl( PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE + "?error")
//				.defaultSuccessUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE )
//				.permitAll()
//			.and()
//			.logout()
//				.logoutUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGOUT)
//				.logoutSuccessUrl(PUBLIC_CNTROLLER +"/login-hr?logout") // <-- post login url must match root ant patter
//				.deleteCookies("JSESSIONID")
//				.permitAll()
//
//			.and()
//		          .exceptionHandling()
//		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
//			.and()
//				.csrf().disable();
//
//			System.out.println("hr login " + PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE);
//
//        }
//
//
//		@Autowired
//		public void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bcryptEncoder  , DataSource datasource ) throws Exception {
//
//			auth.jdbcAuthentication().dataSource(datasource)
//			.passwordEncoder(bcryptEncoder)
//
//			//True means the user is enabled. Usually this would be implement as a field in the DB.
//			.usersByUsernameQuery("SELECT username, password, true FROM hr_user WHERE username = ?")
//			//Since we don't have an authorities(roles) table we assume every user is admin
//			.authoritiesByUsernameQuery("SELECT username, role FROM hr_user WHERE username = ?");
//		}
//	}
//}


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig  {
    @Autowired
    private AppUserDetailsService appUserDetailsService;

//    @Bean
//    @Order(1)
//    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Example: Disable CSRF
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers(new AntPathRequestMatcher(ROOT_PAGE)).permitAll()
//                                .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**").permitAll()
//                                .antMatchers(INDEX_PAGE, PUBLIC_CNTROLLER  +  HOME_PAGE, PUBLIC_CNTROLLER  +  "/**", "/error/**").permitAll()// Example: Public access
//                                .anyRequest().authenticated()
//                        // All other requests require authentication
//                );
//        return http.build();
//    }
//
//    @Bean
////    @Order(2)
//    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Example: Disable CSRF
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers(new AntPathRequestMatcher(ROOT_PAGE)).permitAll()
//                                .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**").permitAll()
//                                .antMatchers(INDEX_PAGE, PUBLIC_CNTROLLER  +  HOME_PAGE, PUBLIC_CNTROLLER  +  "/**", "/error/**").permitAll()// Example: Public access
//                                .anyRequest().authenticated()
//                        // All other requests require authentication
//                )
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers(new AntPathRequestMatcher(ROOT_PAGE)).permitAll()
////                        .anyRequest().authenticated()
////                        // All other requests require authentication
////                )
//
//			.formLogin()
//				.loginPage(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE)
//				.loginProcessingUrl( APPLICANT_CV_CNTROLLER + APPLICANT_LOGIN_PAGE ) // This POST URL has to match with root AntMatcher
//
//				.failureUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?error=Failure when trying to log in")
//				.defaultSuccessUrl( APPLICANT_CV_CNTROLLER + APPLICANT_SUMMARY_PAGE )
//				.permitAll()
//
//			.and()
//			.logout()
//				.logoutUrl(APPLICANT_CV_CNTROLLER + APPLICANT_LOGOUT) // <-- logout
//				.logoutSuccessUrl( PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE + "?logout")
//				.deleteCookies("JSESSIONID")
//				.permitAll()
//
//			.and()
//		          .exceptionHandling()
//		          .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
//        ; // Example: Enable HTTP Basic authentication
//        return http.build();
//    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Example: Disable CSRF
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(new AntPathRequestMatcher(ROOT_PAGE)).permitAll()
                                .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**").permitAll()
                                .antMatchers(INDEX_PAGE, PUBLIC_CNTROLLER  +  HOME_PAGE, PUBLIC_CNTROLLER  +  "/**", "/error/**").permitAll()
//                                .antMatchers(JOBS_ADS_MANAGEMENT_CNTROLLER  +  "/**").hasAuthority("HR_ADMIN")// Example: Public access
                            .anyRequest().authenticated()
                        // All other requests require authentication
                )

                 // <-- Root ant matcher
                .formLogin()
                    .loginPage(PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE)
                    .loginProcessingUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGIN_PAGE ) // <-- post login url must match root ant patter
                    .failureUrl( PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE + "?error")
                    .defaultSuccessUrl( JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE )
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGOUT)
                    .logoutSuccessUrl(PUBLIC_CNTROLLER + HR_MEMBER_LOGIN_PAGE + "?logout") // <-- post login url must match root ant patter
                    .deleteCookies("JSESSIONID")
                    .permitAll()

                .and()
                      .exceptionHandling()
                      .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
                .and()
				.csrf().disable();
        ; // Example: Enable HTTP Basic authentication
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserDetailsService;
    }
}

