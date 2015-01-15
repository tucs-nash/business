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

import com.tucs.business.services.interfaces.security.AuthenticationFilter;
import com.tucs.business.services.interfaces.security.UserServiceAuthentication;
import com.tucs.core.commons.dto.UserLookupsDto;
import com.tucs.core.model.entity.EnUser;
import com.tucs.core.model.entity.EnUser.TypeUser;

public class AuthenticationFilterImpl implements AuthenticationProvider, AuthenticationFilter {

	private UserServiceAuthentication userService;
	private Md5PasswordEncoder passwordEncoder;
	private String salt;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());
  
		ArrayList<GrantedAuthority> privileges = null;
		EnUser user = userService.getUserActiveByLogin(email);

		if (user == null || !passwordEncoder.isPasswordValid(user.getPassword(), password, salt)) {
			throw new BadCredentialsException("");
		}
		
		return new UsernamePasswordAuthenticationToken(user,"", privileges) ;
	}
	
	@Override
	public EnUser createUser(EnUser user) {
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(), salt));
		//FIXME: CHANGE THIS
		user.setTypeUser(TypeUser.TYPE_USER_ADMIN);
		return userService.createUserLoginInitial(user);
	}
	
	@Override
	public Boolean verifyEmail(String email) {
		return userService.verifyEmail(email);
	}

	@Override
	public Boolean forgotPassword(String email) {
		EnUser user = userService.getUserActiveByLogin(email);
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

	@Override
	public EnUser getUser(String userId) {
		return userService.getUser(userId);
	}

	@Override
	public EnUser updateUser(EnUser user) {
		return userService.updateUser(user);
	}

	@Override
	public UserLookupsDto getUserLookups() {
		return userService.getUserLookups();
	}
}
