package com.tucs.business.services.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.tucs.business.services.interfaces.AuthenticationService;
import com.tucs.business.services.interfaces.UserServiceAuthentication;
import com.tucs.core.model.entity.EnUser;
import com.tucs.core.model.entity.EnUser.TypeUser;

public class AuthenticationServiceImpl implements AuthenticationProvider, AuthenticationService {

	private UserServiceAuthentication userService;
	private Md5PasswordEncoder passwordEncoder;
	private String salt;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());
  
		ArrayList<GrantedAuthority> privileges = null;
		EnUser user = userService.getUserByLogin(email);

		if (user == null) {
			throw new BadCredentialsException("This email is not register in the system");
		} else if (!passwordEncoder.isPasswordValid(user.getPassword(), password, salt)) {
			throw new BadCredentialsException("The password is wrong");
		}	
		
		return new UsernamePasswordAuthenticationToken(user,"", privileges) ;
	}
	
	@Override
	public EnUser createUser(EnUser user) {
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), salt));
		//FIXME: CHANGE THIS
		user.setTypeUser(TypeUser.ADMIN);
		return userService.createUserLogin(user);
	}
	
	@Override
	public Boolean verifyEmail(String email) {
		return userService.verifyEmail(email);
	}

	@Override
	public Boolean forgotPassword(String email) {
		EnUser user = userService.getUserByLogin(email);
		if (user != null) {
			user.setPassword(passwordEncoder.encodePassword("admin123", salt));
			user = userService.updateUser(user);
		}
		return user != null;
	}	
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	@Autowired
	public void setUserService(UserServiceAuthentication userService) {
		this.userService = userService;
	}

	@Autowired
	public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}	
	
	@Autowired
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
