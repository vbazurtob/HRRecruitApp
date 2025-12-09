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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.vbazurtob.HRRecruitApp.lib.validators.FormFieldsMatchConstraint;

@FormFieldsMatchConstraint.List({
	@FormFieldsMatchConstraint(field="password", fieldMatch="passwordConfirmation", message="Passwords don''t match! No changes were made.")
})
public class ApplicantChangePasswordForm {


	@Size(message = "Current password cannot exceed 128 characters", min= 8, max= 128)
	@NotNull(message = "Password cannot be null")
	@Pattern(message = "Password is too weak. It must include at least 1 number, 1 lowercase character, 1 uppercase character and 1 special character(@#$%^&+=)", regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String currentPassword;
	
	@Size(message = "Password cannot exceed 128 characters", min= 8, max= 128)
	@NotNull(message = "Password cannot be null")
	@Pattern(message = "Password is too weak. It must include at least 1 number, 1 lowercase character, 1 uppercase character and 1 special character(@#$%^&+=)", regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String password;
	
	
	@Size(max = 128, message = "Password confirmation cannot exceed 128 characters", min=8)
	@Pattern(message = "Password is too weak. It must include at least 1 number, 1 lowercase character, 1 uppercase character and 1 special character(@#$%^&+=)", regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	private String passwordConfirmation;
	
	
	@Valid
	private String usernameChangePwdForm;
	
	
	
	public ApplicantChangePasswordForm() {

	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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

	
	


	public String getUsernameChangePwdForm() {
		return usernameChangePwdForm;
	}

	public void setUsernameChangePwdForm(String usernameChangePwdForm) {
		this.usernameChangePwdForm = usernameChangePwdForm;
	}

	@Override
	public String toString() {
		return "ApplicantChangePasswordForm [currentPassword=" + currentPassword + ", password=" + password
				+ ", passwordConfirmation=" + passwordConfirmation + ", username=" + usernameChangePwdForm + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentPassword == null) ? 0 : currentPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmation == null) ? 0 : passwordConfirmation.hashCode());
		result = prime * result + ((usernameChangePwdForm == null) ? 0 : usernameChangePwdForm.hashCode());
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
		ApplicantChangePasswordForm other = (ApplicantChangePasswordForm) obj;
		if (currentPassword == null) {
			if (other.currentPassword != null)
				return false;
		} else if (!currentPassword.equals(other.currentPassword))
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
		if (usernameChangePwdForm == null) {
            return other.usernameChangePwdForm == null;
		} else return usernameChangePwdForm.equals(other.usernameChangePwdForm);
    }

	

}
