package com.devsuperior.dsmoviejacoco.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmoviejacoco.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("""
			SELECT user
			FROM User user
			LEFT JOIN FETCH user.roles
			WHERE user.username = :username
			""")
	Optional<User> findWithRolesByUsername(String username);

	Optional<User> findByUsername(String username);

}
