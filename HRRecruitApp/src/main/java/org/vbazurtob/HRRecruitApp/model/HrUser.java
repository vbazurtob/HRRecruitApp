package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="HrUser.findAll", query="SELECT u FROM HrUser u")
public class HrUser extends UserBaseClass implements Serializable {
	private static final long serialVersionUID = 1L;

	

	private String password;

	

	public HrUser() {
	}



	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "User [password=" + password + ", getUsername()=" + getUsername() + ", getEmail()=" + getEmail()
				+ ", getLastname()=" + getLastname() + ", getNames()=" + getNames() + ", getRole()=" + getRole() + "]";
	}
	
	



}