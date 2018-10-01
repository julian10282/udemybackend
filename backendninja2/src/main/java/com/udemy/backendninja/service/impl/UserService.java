package com.udemy.backendninja.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.entity.UserEntity;
import com.udemy.backendninja.entity.UserRoleEntity;
import com.udemy.backendninja.repository.UserRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class UserService.
 */
@Service("userService")
public class UserService implements UserDetailsService {

	/** The user repository. */
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(userEntity.getUserRole());
		return buildUser(userEntity, authorities);
	}
	
	/**
	 * Builds the user.
	 *
	 * @param userEntity the user entity
	 * @param authorities the authorities
	 * @return the user
	 */
	private User buildUser(UserEntity userEntity, List<GrantedAuthority> authorities) {
		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(),
				true, true, true, authorities);
	}
	
	/**
	 * Builds the authorities.
	 *
	 * @param userRoles the user roles
	 * @return the list
	 */
	private List<GrantedAuthority> buildAuthorities (Set<UserRoleEntity> userRoles){
		Set<GrantedAuthority> auths = new HashSet<>();
		
		for (UserRoleEntity userRoleEntity : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRoleEntity.getRole()));
		}
		
		return new ArrayList<>(auths); 
	}

}
