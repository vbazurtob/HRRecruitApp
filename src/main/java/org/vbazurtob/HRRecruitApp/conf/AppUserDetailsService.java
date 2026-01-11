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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.ApplicantWithPassword;
import org.vbazurtob.HRRecruitApp.model.repository.ApplicantRepository;

import javax.sql.DataSource;

@Service
public class AppUserDetailsService implements UserDetailsService {

  public static final String ROLE_ADMIN = "HR_ADMIN";

  public static final String ROLE_APPLICANT = "APPLICANT";

  @Autowired private ApplicantRepository applicantRepository;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null) throw new UsernameNotFoundException(null);

    if (username.equals("admin")) {
      try {
        return getAdminInfo();
      } catch (Exception e) {
        throw new UsernameNotFoundException(username);
      }
    }

    ApplicantWithPassword applicantWithPassword = applicantRepository.findOneByUsername(username);
    if (applicantWithPassword != null) {
      return User.builder()
          .username(username)
          .password(applicantWithPassword.getPassword())
          .authorities(ROLE_APPLICANT)
          .build();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  @Autowired
  private UserDetails getAdminInfo() {
    String sql = "SELECT username, password, role FROM hr_user WHERE username = ?";
    // Fetch admin details using JdbcTemplate
    return jdbcTemplate.queryForObject(sql, new UserRowMapper(), "admin");
  }
}
