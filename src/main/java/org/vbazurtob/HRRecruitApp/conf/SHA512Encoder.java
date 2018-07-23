package org.vbazurtob.HRRecruitApp.conf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA512Encoder implements PasswordEncoder{

	private String salt;
	
	
	
	//DELETE???

	public SHA512Encoder(String salt) {
		super();
		this.salt = salt;
	}

	@Override
	public String encode(CharSequence pwd) {
		
		return Sha512DigestUtils.sha(pwd.toString()).toString();
	}

	@Override
	public boolean matches(CharSequence arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
