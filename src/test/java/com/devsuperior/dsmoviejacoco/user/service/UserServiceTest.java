package com.devsuperior.dsmoviejacoco.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devsuperior.dsmoviejacoco.security.AuthenticatedUser;
import com.devsuperior.dsmoviejacoco.user.UserFixture;
import com.devsuperior.dsmoviejacoco.user.entity.User;
import com.devsuperior.dsmoviejacoco.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final String NONEXISTENT_USERNAME = "nobody@gmail.com";

	@Mock
	private UserRepository userRepository;

	@Mock
	private AuthenticatedUser authenticatedUser;

	@InjectMocks
	private UserService userService;

	@Test
	void authenticatedShouldReturnUserEntityWhenUserExists() {
		given(authenticatedUser.getUsername()).willReturn(UserFixture.CLIENT_USERNAME);
		given(userRepository.findByUsername(UserFixture.CLIENT_USERNAME))
				.willReturn(Optional.of(UserFixture.aClientUser()));

		User user = userService.findCurrentUser();

		assertThat(user.getUsername()).isEqualTo(UserFixture.CLIENT_USERNAME);
	}

	@Test
	void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		given(authenticatedUser.getUsername()).willReturn(NONEXISTENT_USERNAME);
		given(userRepository.findByUsername(NONEXISTENT_USERNAME)).willReturn(Optional.empty());

		assertThatThrownBy(() -> userService.findCurrentUser()).isInstanceOf(UsernameNotFoundException.class);
	}

	@Test
	void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		given(userRepository.findWithRolesByUsername(UserFixture.CLIENT_USERNAME))
				.willReturn(Optional.of(UserFixture.aClientUser()));

		UserDetails userDetails = userService.loadUserByUsername(UserFixture.CLIENT_USERNAME);

		assertThat(userDetails.getUsername()).isEqualTo(UserFixture.CLIENT_USERNAME);
		assertThat(userDetails.getAuthorities())
				.extracting(grantedAuthority -> grantedAuthority.getAuthority())
				.containsExactly(UserFixture.CLIENT_AUTHORITY);
	}

	@Test
	void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		given(userRepository.findWithRolesByUsername(NONEXISTENT_USERNAME)).willReturn(Optional.empty());

		assertThatThrownBy(() -> userService.loadUserByUsername(NONEXISTENT_USERNAME))
				.isInstanceOf(UsernameNotFoundException.class);
	}

}
