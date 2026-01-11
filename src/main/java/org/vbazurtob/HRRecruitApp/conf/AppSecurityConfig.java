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
  public static final String JSESSIONID_COOKIE = "JSESSIONID";
  @Autowired private AppUserDetailsService appUserDetailsService;

  private void publicFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        auth ->
            auth.antMatchers(ROOT_PAGE)
                .permitAll()
                .antMatchers(CSS_FOLDER + "**", JS_FOLDER + "**", IMG_FOLDER + "**")
                .permitAll()
                .antMatchers(
                    INDEX_PAGE,
                    PUBLIC_CNTROLLER + HOME_PAGE,
                    PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE,
                    PUBLIC_CNTROLLER + "/**",
                    "/error/**")
                .permitAll());
  }

  // Admin config
  @Bean
  @Order(1)
  public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
    publicFilterChain(http);
    http
        // Specify the paths where admin filter chain config must be matched
        .requestMatchers(
            matchers ->
                matchers.antMatchers(
                    ADMIN_CNTROLLER + "/**", JOBS_ADS_MANAGEMENT_CNTROLLER + "/**"))
        .authorizeHttpRequests(
            auth ->
                auth.antMatchers(HR_LOGIN_URL, HR_POST_LOGIN_URL)
                    .permitAll()
                    .antMatchers(HR_LOGOUT_URL)
                    .permitAll()
                    .antMatchers(JOBS_ADS_MANAGEMENT_CNTROLLER + "/**")
                    .hasAuthority(AppUserDetailsService.ROLE_ADMIN)
                    .anyRequest()
                    .authenticated() // All other requests require authentication
            )
        .formLogin()
        .loginPage(HR_LOGIN_URL)
        .loginProcessingUrl(
            HR_POST_LOGIN_URL) // <-- post login url must match value in template forms
        .failureUrl(HR_LOGIN_URL + "?error")
        .defaultSuccessUrl(JOBS_ADS_MANAGEMENT_CNTROLLER + HR_MEMBER_SUMMARY_PAGE)
        .permitAll()
        .and()
        .logout()
        .logoutUrl(HR_LOGOUT_URL)
        .logoutSuccessUrl(HR_LOGIN_URL + "?logout") // <-- post login url must match root ant patter
        .deleteCookies(JSESSIONID_COOKIE)
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage(PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE);
    return http.build();
  }

  // Applicant config (Default for all other paths)
  @Bean
  @Order(2)
  public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
    publicFilterChain(http);
    http.authorizeHttpRequests(
            auth ->
                auth.antMatchers(
                        APPLICANT_LOGIN_URL, APPLICANT_POST_LOGIN_URL, APPLICANT_LOGOUT_URL)
                    .permitAll()
                    .antMatchers(APPLICANT_CV_CNTROLLER + "/**", APPLICANT_PROFILE_PAGE + "**")
                    .hasAuthority(AppUserDetailsService.ROLE_APPLICANT)
                    .anyRequest()
                    .authenticated() // All other requests require authentication
            )
        .formLogin()
        .loginPage(APPLICANT_LOGIN_URL)
        .loginProcessingUrl(
            APPLICANT_POST_LOGIN_URL) // Must match the POST URL that is being printed in the
        // template form
        .failureUrl(APPLICANT_LOGIN_URL + "?error=Failure when trying to log in")
        .defaultSuccessUrl(APPLICANT_CV_CNTROLLER + APPLICANT_SUMMARY_PAGE)
        .permitAll()
        .and()
        .logout()
        .logoutUrl(APPLICANT_LOGOUT_URL) // <-- logout
        .logoutSuccessUrl(APPLICANT_LOGIN_URL + "?logout")
        .deleteCookies(JSESSIONID_COOKIE)
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage(
            PUBLIC_CNTROLLER + NOT_AUTHORIZED_PAGE); // Example: Enable HTTP Basic authentication
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
