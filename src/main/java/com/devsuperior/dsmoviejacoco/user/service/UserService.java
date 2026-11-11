package com.devsuperior.dsmoviejacoco.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmoviejacoco.security.AuthenticatedUser;
import com.devsuperior.dsmoviejacoco.user.entity.User;
import com.devsuperior.dsmoviejacoco.user.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static final String USERNAME_NOT_FOUND_MESSAGE = "User not found.";

	private final UserRepository userRepository;

	private final AuthenticatedUser authenticatedUser;

	public UserService(UserRepository userRepository, AuthenticatedUser authenticatedUser) {
		this.userRepository = userRepository;
		this.authenticatedUser = authenticatedUser;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository
				.findWithRolesByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));

		String[] authorities = user
				.getRoles()
				.stream()
				.map(role -> role.getAuthority())
				.toArray(size -> new String[size]);

		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
	}

	@Transactional(readOnly = true)
	public User findCurrentUser() {
		return userRepository
				.findByUsername(authenticatedUser.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
	}

}
