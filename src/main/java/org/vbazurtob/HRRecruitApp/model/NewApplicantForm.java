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

package org.vbazurtob.HRRecruitApp.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsMatchConstraint;

@FormFieldsMatchConstraint.List({
	@FormFieldsMatchConstraint(field="password", fieldMatch="passwordConfirmation", message="Passwords don''t match! ")
})
public class NewApplicantForm {

	
	@Size(message = "Password must be at least 8 characters and a maximum of 20", min= 8, max= 20)
	@NotNull(message = "Password cannot be null")
	@Pattern(message = "Password is too weak. It must include at least 1 number, 1 lowercase character, 1 uppercase character and 1 special character(@#$%^&+=)", regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String password;
	
	@NotNull(message = "Confirmation password cannot be null")
	@Size(min= 8, max = 20, message = "Password must be at least 8 characters and a maximum of 20")
	@Pattern(message = "Password is too weak. It must include at least 1 number, 1 lowercase character, 1 uppercase character and 1 special character(@#$%^&+=)", regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String passwordConfirmation;
	
	
	@Size(min = 10, max = 35, message = "Username must be between 10 and 35 characters")
	@NotNull( message ="Username cannot be null")
	@Pattern(message = "Username must start with a letter. Only alphanumeric characters, the ''_'' and ''.'' characters are allowed. ''_'' or ''.'' cannot be ending characters", regexp ="^(?![_.])(?!.*[_.]{2})[a-zA-Z][a-zA-Z0-9._]+(?<![_.])$")
	private String username;
	
	
	@Size(message = "E-mail cannot exceed 45 characters", max= 45)
	@Email(message = "E-mail should be a valid address")
	@NotEmpty(message = "E-mail cannot be empty")
	private String email;
	
	
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public NewApplicantForm() {

	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}



	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmation == null) ? 0 : passwordConfirmation.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewApplicantForm other = (NewApplicantForm) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordConfirmation == null) {
			if (other.passwordConfirmation != null)
				return false;
		} else if (!passwordConfirmation.equals(other.passwordConfirmation))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "NewApplicantForm [password=" + password + ", passwordConfirmation=" + passwordConfirmation
				+ ", username=" + username + ", email=" + email + "]";
	}
	
	
	

}
