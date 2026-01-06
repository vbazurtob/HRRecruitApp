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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author voltaire
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig {
  @Autowired private AppUserDetailsService appUserDetailsService;

//    @Bean
//    @Order(0)
//    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable()) // Example: Disable CSRF
//                .authorizeHttpRequests(
//                        auth ->
//                                auth
//                                        .antMatchers(ROOT_PAGE).permitAll()
//                                        .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**")
//                                        .permitAll()
//                                        .antMatchers(
//                                                INDEX_PAGE,
//                                                PUBLIC_CNTROLLER + HOME_PAGE,
////                                                PUBLIC_CNTROLLER + "/**",
//                                                "/error/**")
//                                        .permitAll()
////                     .antMatchers(JOBS_ADS_MANAGEMENT_CNTROLLER  +
////                     "/**").hasAuthority(AppUserDetailsService.ROLE_ADMIN)
////                                        .anyRequest()
////                                        .authenticated()
//                        // All other requests require authentication
//                );
//        return http.build();
//    }


private void publicFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // Example: Disable CSRF
            .authorizeHttpRequests(
                    auth ->
                            auth
                                    .antMatchers(ROOT_PAGE).permitAll()
                                    .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**")
                                    .permitAll()
                                    .antMatchers(
                                            INDEX_PAGE,

                                            PUBLIC_CNTROLLER + HOME_PAGE,
                                            PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE,

APPLICANT_LOGIN_URL,
APPLICANT_POST_LOGIN_URL,
APPLICANT_LOGOUT_URL,



HR_LOGIN_URL,
HR_POST_LOGIN_URL,
HR_LOGOUT_URL,

PUBLIC_CNTROLLER + "/**",

//                                            PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE,
//                                            ADMIN_CNTROLLER + HR_MEMBER_LOGIN_PAGE,
//
//
//                                            APPLICANT_CV_CNTROLLER + APPLICANT_LOGIN_PAGE,
//                                            APPLICANT_CV_CNTROLLER + APPLICANT_LOGOUT,
//
//                                            JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGIN_PAGE,
//                                            JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGOUT,

                                            "/error/**")
                                    .permitAll()
//                     .antMatchers(JOBS_ADS_MANAGEMENT_CNTROLLER  +
//                     "/**").hasAuthority(AppUserDetailsService.ROLE_ADMIN)
//                                        .anyRequest()
//                                        .authenticated()
                    // All other requests require authentication
            );
//    return http;
}

  // Admin
  @Bean
  @Order(1)
  public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
    publicFilterChain(http);
    http.csrf(csrf -> csrf.disable()) // Example: Disable CSRF
        .authorizeHttpRequests(
            auth -> auth
//                requestMatchers(new AntPathRequestMatcher(ADMIN_CNTROLLER + HR_MEMBER_LOGIN_PAGE)).permitAll()
//                .requestMatchers(new AntPathRequestMatcher(PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE)).permitAll()

//                PUBLIC_CNTROLLER + APPLICANT_LOGIN_PAGE,

                    .antMatchers(
                            ADMIN_CNTROLLER + HR_MEMBER_LOGIN_PAGE,
                            JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_LOGOUT
                    ).permitAll()
//                     .antMatchers(JOBS_ADS_MANAGEMENT_CNTROLLER  +
//                     "/**").hasAuthority(AppUserDetailsService.ROLE_ADMIN)
                .anyRequest()
                .authenticated()
            // All other requests require authentication
            )

        // <-- Root ant matcher
        .formLogin()
        .loginPage(HR_LOGIN_URL)
//        .loginProcessingUrl(HR_POST_LOGIN_URL) // <-- post login url must match root ant patter
        .failureUrl(HR_LOGIN_URL + "?error")
        .defaultSuccessUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE)
        .permitAll()
        .and()
        .logout()
        .logoutUrl(HR_LOGOUT_URL)
        .logoutSuccessUrl(HR_LOGIN_URL + "?logout") // <-- post login url must match root ant patter
        .deleteCookies("JSESSIONID")
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE)
        .and()
        .csrf()
        .disable();
      // Example: Enable HTTP Basic authentication
    return http.build();
  }

    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
//        publicFilterChain(http);
        http.csrf(csrf -> csrf.disable()) // Example: Disable CSRF
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .antMatchers(

                                                PUBLIC_CNTROLLER + "**",
                                                "/error/**")
                                        .permitAll() // Example: Public access
                                        .anyRequest()
                                        .authenticated()
                        // All other requests require authentication
                )

                .formLogin()
                    .loginPage(APPLICANT_LOGIN_URL)
//                    .loginProcessingUrl(APPLICANT_POST_LOGIN_URL) // This POST URL has to match with root AntMatcher
                    .failureUrl(APPLICANT_LOGIN_URL + "?error=Failure when trying to log in")
                    .defaultSuccessUrl(APPLICANT_CV_CNTROLLER + APPLICANT_SUMMARY_PAGE)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl(APPLICANT_LOGOUT_URL) // <-- logout
                    .logoutSuccessUrl(APPLICANT_LOGIN_URL + "?logout")
                    .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE); // Example: Enable HTTP Basic authentication
        return http.build();
    }

  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
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
