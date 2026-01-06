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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDetails> {
    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role"); // Adjust based on your schema

        return User.builder()
                .username(username)
                .password(password)  // Password should ideally be encoded
                .authorities(role)  // Convert role to GrantedAuthority if needed
                .build();
    }
}