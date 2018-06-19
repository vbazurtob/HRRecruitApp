package org.vbazurtob.HRRecruitApp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User extends UserBaseClass implements Serializable {
	private static final long serialVersionUID = 1L;

	

	private String password;

	

	public User() {
	}



	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}